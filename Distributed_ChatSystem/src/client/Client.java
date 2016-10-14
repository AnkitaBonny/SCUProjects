package client;
import communication.*;
import java.net.Socket;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Client {
    private Socket socket;
    private String hostname;
    private int portNumber;
    private int clientId;
    private ObjectOutputStream writeToServer;
    private ObjectInputStream readFromServer;
    private String cliName;
    private Map<CommunicationType, List<CommunicationHandler>> handlerMap;
    private CommunicationHandler comHandler;

    public Client(String clientName, String hostname, int portNumber) {
        this.cliName = clientName;
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.handlerMap = new HashMap<>();

        registerHandler(CommunicationType.CONNECTION_SUCCESS, this::validateUser);
    }


    private <E extends Serializable> void validateUser(CommunicationMessage<E> message) {
        setClientId(message);

        // Send client information to the server
        CommunicationMessage<String> loginInfo = new CommunicationMessage<>(cliName, CommunicationMessage.SERVER_ID, cliName,
                CommunicationType.USER_LOGIN);
        writeMessage(loginInfo);
    }

    public void makeConnection() {
        try {
            this.socket = new Socket(hostname, portNumber);
            this.writeToServer = new ObjectOutputStream(this.socket.getOutputStream());
            this.readFromServer = new ObjectInputStream(this.socket.getInputStream());

            // Start the reader thread
            ClientReader reader = new ClientReader(this.readFromServer);
            Thread readerThread = new Thread(reader);
            readerThread.start();

        } catch( UnknownHostException uhe ) {
            System.err.printf("Not able to connect to %s:%d\n", hostname, portNumber);
            uhe.printStackTrace();
        } catch( IOException ioe ) {
            System.err.printf("Unable openning streams to %s:%d\n", hostname, portNumber);
            ioe.printStackTrace();
        }
    }

    private <E extends Serializable> void setClientId(CommunicationMessage<E> message) {
        if( message.getContents() instanceof Integer ) {
            this.clientId = (Integer) message.getContents();
        }
    }

    public int getClientId() {
        return this.clientId;
    }

    public <E extends Serializable> void writeMessage( CommunicationMessage<E> message ) {
        try {
            message.setSenderId(this.clientId);
            writeToServer.writeObject(message);
        } catch( IOException ioe ) {
            System.err.printf("Error writing message to %s:%d!\n", this.hostname, this.portNumber);
        }
    }

    public void setDefaultMessageHandler(CommunicationHandler handler) {
        this.comHandler = handler;
    }

    public void registerHandler(CommunicationType type, CommunicationHandler listener) {
        List<CommunicationHandler> typeHandlers = this.handlerMap.get(type);

        if( typeHandlers == null ) {
            typeHandlers = new ArrayList<>();
        }

        typeHandlers.add(listener);

        this.handlerMap.put(type, typeHandlers);
    }

    public <E extends Serializable> void notifyHandlers(CommunicationMessage<E> message) {
        CommunicationType type = message.getType();

        if( this.handlerMap.containsKey(type) ) {
            List<CommunicationHandler> typeHandlers = this.handlerMap.get(type);
            typeHandlers.stream().forEach(h -> h.recieveMessage(message));
        } else if( this.comHandler != null ) {
            this.comHandler.recieveMessage(message);
        } else {
            System.err.printf("Incorrect unhandled type: %s\n", type);
        }
    }

    private class ClientReader implements Runnable {

        private ObjectInputStream serverRead;

        public ClientReader(ObjectInputStream serverRead) {
            this.serverRead = serverRead;
        }

        public void run() {
            while( true ) {
                try {
                    CommunicationMessage<?> message = (CommunicationMessage<?>)serverRead.readObject();

                    notifyHandlers(message);
                } catch( IOException ioe ) {
                    System.err.println("Error reading from the server!");
                    ioe.printStackTrace();
                    System.exit(1);
                } catch( ClassNotFoundException cnfe ) {
                    System.err.printf("Invalid message from %s:%d\n", hostname, portNumber);
                    cnfe.printStackTrace();
                }
            }
        }
    }
}
