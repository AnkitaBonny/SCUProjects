package server;
import communication.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    private static final String SERVER_NAME = "Server";
    private ServerSocket serverSocket;
    private int portNumber;
    public final int COMMON_ROOM_ID;
    public static final int SERVER_ID = -1;
    private static int userIdCounter = 0;
    private static int roomId = 0;
    private Map<Integer, ClientHandler> clientConnections;
    private Map<Integer, CommonWindow> commonRoom;
    private Map<CommunicationType, List<CommunicationHandler>> handlers;
    private List<String> clientUsernames;

    public Server(int portNumber) {
        this.portNumber = portNumber;
        this.clientConnections = new HashMap<>();
        this.commonRoom = new HashMap<>();
        this.handlers = new HashMap<>();
        this.clientUsernames = new ArrayList<>();

        // Bind the server socket
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch( IOException ioe ) {
            System.err.printf("Error while opening server on port %d\n", portNumber);
            ioe.printStackTrace();
            System.exit(1);
        }

       //comHandler(CommunicationType.USER_LOGIN, this::loginUser);
        comHandler(CommunicationType.USER_LOGIN, this::loginUser);
        comHandler(CommunicationType.CREATE_ROOM, this::createGroupRoom);
        comHandler(CommunicationType.JOIN_ROOM, this::joinChatRoom);
        comHandler(CommunicationType.LEAVE_ROOM, this::leaveChatRoom);
        comHandler(CommunicationType.LIST_USERS, this::listOfUsers);
        comHandler(CommunicationType.LIST_ROOMS, this::listOfRooms);

        // Create the common chat room that all users can join
       // CommonWindow commonRoom = new CommonWindow("Common Chat Room");
        CommonWindow commonRoom = new CommonWindow("Common Chat Room");
        COMMON_ROOM_ID = commonRoom.getId();
        this.commonRoom.put(COMMON_ROOM_ID, commonRoom);
    }



    public void acceptConn() {
        System.out.printf("Server -> Server is listening on host: %s , port: %d\n",
                this.serverSocket.getInetAddress().getHostName(), portNumber);
        //writeClientDetailsToFile(this.serverSocket.getInetAddress().getHostName(), portNumber);

        while ( true ) {
            try {
                // Wait until a new client has arrived
                Socket newClientConn = serverSocket.accept();

                // Create a handler for that client
                ClientHandler clientHandler = new ClientHandler(newClientConn);
                clientHandler.start();


            } catch( IOException ioe ) {
                System.err.printf("Error while accept client on port %d\n", portNumber);
                ioe.printStackTrace();
            }
        }
    }
    public void comHandler(CommunicationType type, CommunicationHandler listener) {
        List<CommunicationHandler> typeHandlers = this.handlers.get(type);

        if( typeHandlers == null ) {
            typeHandlers = new ArrayList<>();
        }

        typeHandlers.add(listener);

        this.handlers.put(type, typeHandlers);
    }
    
    private void writeClientDetailsToFile(String hostName, int portNumber2,int userId,String clientName) {
    	try {

			String content = "Client IP:"+hostName+",Port:"+portNumber2+",UserId:"+userId+",ClientName:"+clientName+"\n";

			File file = new File("/Users/pallavi/ClientDetails/LogFile.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void joinCommonWindow(int userId) {
        this.commonRoom.get(COMMON_ROOM_ID).addUser(userId);
    }

    public <E extends Serializable> void msgSendToWindow( CommunicationMessage<E> message, CommonWindow room ) {
        if( room != null & message != null ) {
            System.out.printf("%s -> %s(%d) [type = %s]: %s\n", message.getSender(),
                    room.getName(), room.getId(), message.getType(), message.getContents());

            for( int userId : room.getUsers() ) {
                ClientHandler ch = this.clientConnections.get(userId);

                if( ch != null ) {
                    ch.sendMessage(message);
                } else {
                    System.out.printf("Server -> No users in room %s", room.getName());
                }
            }
        }
    }

    public <E extends Serializable> void notifyHandlers(CommunicationMessage<E> message) {
        CommunicationType type = message.getType();

        if( this.handlers.containsKey(type) ) {
            List<CommunicationHandler> typeHandlers = this.handlers.get(type);
            typeHandlers.stream().forEach(h -> h.recieveMessage(message));
            /*for (MessageHandler h : typeHandlers)
            {
            	h.recieveMessage(message);
            }*/
        } else {
            System.err.printf("Unhandled message type Received : %s\n", type);
        }
    }

    private <E extends Serializable> void createGroupRoom(CommunicationMessage<E> message) {
        CommonWindow commonWindow = new CommonWindow((String)message.getContents());
        commonWindow.addUser(message.getSenderId());
        this.commonRoom.put(commonWindow.getId(), commonWindow);
        CommunicationMessage<String> response = new CommunicationMessage<>(SERVER_NAME, commonWindow.getId(), commonWindow.getName(), CommunicationType.JOIN_ROOM_SUCCESS);
        ClientHandler ch = clientConnections.get(message.getSenderId());
        System.out.printf("Server -> %s(%d) created room %s(%d)\n", message.getSender(), message.getSenderId(), message.getContents(), commonWindow.getId());
        ch.sendMessage(response);
    }

    private <E extends Serializable> void joinChatRoom(CommunicationMessage<E> message) {
        CommonWindow roomToJoin;
        CommunicationMessage<String> response;
        ClientHandler ch = clientConnections.get(message.getSenderId());
        E contents = message.getContents();
        
        try {
            // Get the room which is joined
            int roomId = Integer.parseInt( (String)contents );
            roomToJoin = this.commonRoom.get(roomId);

            if( roomToJoin != null ) {
                // Add  user to room
                roomToJoin.addUser(message.getSenderId());
                this.commonRoom.put(roomId, roomToJoin);

                // Confirmation message
                response = new CommunicationMessage<>(SERVER_NAME, roomId, roomToJoin.getName(), CommunicationType.JOIN_ROOM_SUCCESS);
                System.out.printf("Server -> %s(%d) joined room %s(%d)\n", message.getSender(), message.getSenderId(), roomToJoin.getName(), roomId);

                String joined = String.format("%s has joined room %s!", ch.clientName, roomToJoin.getName());
                CommunicationMessage<String> joinedMessage = new CommunicationMessage<>(SERVER_NAME, roomToJoin.getId(), joined,
                        CommunicationType.CHAT);
                joinedMessage.setSenderId(-1);

                msgSendToWindow(joinedMessage, roomToJoin);
            } else {
                String str = String.format("Room couldn't find having id %d!\n", roomId);
                response = new CommunicationMessage<>(SERVER_NAME, CommunicationMessage.SERVER_ID, str, CommunicationType.JOIN_ROOM_FAILURE);
            }
        } catch( Exception e ) {
          
            response = new CommunicationMessage<>(SERVER_NAME, roomId, "Send valid room id!\n",
                    CommunicationType.JOIN_ROOM_FAILURE);
        }
        ch.sendMessage(response);
    }
    
    private <E extends Serializable> void listOfRooms(CommunicationMessage<E> message) {
        CommunicationMessage<String> response;
        try {
            int roomId = Integer.parseInt((String) message.getContents());
            String rooms = this.commonRoom.values().stream()
                .map(r -> String.format("%s(%d)", r.getName(), r.getId()))
                .collect(Collectors.joining(", "));

            response = new CommunicationMessage<>(SERVER_NAME, roomId, rooms, CommunicationType.CHAT);
        } catch( Exception e ) {
            response = new CommunicationMessage<>(SERVER_NAME, COMMON_ROOM_ID,
                    "/listrooms does not take an argument!", CommunicationType.CHAT);
        }

        this.clientConnections.get(message.getSenderId()).sendMessage(response);
    }
    private <E extends Serializable> void leaveChatRoom(CommunicationMessage<E> message) {
        try {
            CommonWindow room = this.commonRoom.get(Integer.parseInt(message.getContents().toString()));
            int senderId = message.getSenderId();
            room.removeUser(senderId);
            CommunicationMessage<Integer> leaveRoomMessage = new CommunicationMessage<>(SERVER_NAME, SERVER_ID, room.getId(), CommunicationType.LEAVE_ROOM_SUCCESS);
            ClientHandler client = clientConnections.get(message.getSenderId());
            client.sendMessage(leaveRoomMessage);
            System.out.printf("Server -> %s(%d) has left room %s(%d)\n", message.getSender(), message.getSenderId(), room.getName(), room.getId());
            if (room.getUsers().size() == 0) {
                commonRoom.remove(room.getId());
                System.out.printf("Server -> Room %s is empty\n", room.getName());
            } else {
                CommunicationMessage<String> disconnected = new CommunicationMessage<>(SERVER_NAME, room.getId(),
                        String.format("%s has disconnected from %s", client.clientName,
                                room.getName()), CommunicationType.CHAT);
                disconnected.setSenderId(-1);
                msgSendToWindow(disconnected, room);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Incorrect command 'leaveroom %s'", message.getContents());
            CommunicationMessage<String> leaveRoomMessage = new CommunicationMessage<>(SERVER_NAME, SERVER_ID, errorMessage, CommunicationType.LEAVE_ROOM_FAILURE);
            clientConnections.get(message.getSenderId()).sendMessage(leaveRoomMessage);
            System.out.printf("Server -> Incorrect message of type %s from %s\n", message.getType(), clientConnections.get(message.getSenderId()).getName());
        }
    }
    private <E extends Serializable> void listOfUsers(CommunicationMessage<E> message) {
        CommunicationMessage<String> response;
        try {
            int roomId = Integer.parseInt((String) message.getContents());
            CommonWindow room = this.commonRoom.get(roomId);

            String users = room.getUsers().stream()
                .map( i -> clientConnections.get(i) )
                .map( ch -> ch.clientName )
                .collect(Collectors.joining(", "));

            response = new CommunicationMessage<>(SERVER_NAME, roomId, users, CommunicationType.CHAT);
        } catch( Exception e ) {
            response = new CommunicationMessage<>(SERVER_NAME, COMMON_ROOM_ID,
                    "/listOfUsers not available!", CommunicationType.CHAT);
        }

        this.clientConnections.get(message.getSenderId()).sendMessage(response);
    }

    private <E extends Serializable> void loginUser(CommunicationMessage<E> message) {
        ClientHandler ch = this.clientConnections.get(message.getSenderId());
        ch.validate(message);
    }

    private class ClientHandler extends Thread {
        public final int userId;
        public String clientName;
        private Socket clientSocket;
        private ObjectInputStream readFromClient;
        private ObjectOutputStream writeToClient;

        public ClientHandler(Socket clientSocket) {
            this.userId = userIdCounter++; // give a user Id to each connected client
            this.clientSocket = clientSocket;
            clientConnections.put(userId, this);
           // writeClientDetailsToFile(this.clientSocket.getInetAddress().getHostName(), this.clientSocket.getPort(), userId, clientName);

            try {
                this.readFromClient = new ObjectInputStream(clientSocket.getInputStream());
                this.writeToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch( IOException e ) {
                System.err.printf("Client Socket Error!\n");
                e.printStackTrace();
            }
            CommunicationMessage<Integer> connectionSuccess = new CommunicationMessage<>(SERVER_NAME, SERVER_ID, userId, CommunicationType.CONNECTION_SUCCESS);
            sendMessage(connectionSuccess);
        }

        public void disconnectRoom(boolean sendMessage) {
            for(Iterator<CommonWindow> iter = commonRoom.values().iterator(); iter.hasNext(); ) {
                CommonWindow room = iter.next();
                room.removeUser(this.userId);

                if( sendMessage ) {
                    CommunicationMessage<String> disconnected = new CommunicationMessage<>(SERVER_NAME, room.getId(),
                            String.format("%s has disconnected from %s", this.clientName,
                                room.getName()), CommunicationType.CHAT);
                    disconnected.setSenderId(-1);

                    msgSendToWindow(disconnected, room);
                    clientUsernames.remove(this.clientName);
                }

                if ( room.getUsers().size() == 0 && room.getId() != COMMON_ROOM_ID ) {
                    iter.remove();
                    System.out.printf("Server -> Room %s is empty, removing\n", room.getName());
                }
            }
        }

        public <E extends Serializable> void sendMessage(CommunicationMessage<E> messageToSend) {
            try {
                this.writeToClient.writeObject(messageToSend);
            } catch( IOException ioe ) {
                disconnectRoom(false);
            }
        }

        public void run() {
            while( true ) {
                try {
                    CommunicationMessage<?> messageRecieved = (CommunicationMessage<?>)this.readFromClient.readObject();
                    CommunicationType type = messageRecieved.getType();
                    int receiveID = messageRecieved.getDestination();
                  
                    if ( receiveID == SERVER_ID ) {
                        notifyHandlers(messageRecieved);
                    } else {
                        CommonWindow receiveRoom = commonRoom.get(receiveID);
                        if( receiveRoom != null ) {
                            msgSendToWindow(messageRecieved, receiveRoom);
                        } else {
                            CommunicationMessage<String> errorMessage = new CommunicationMessage<>(SERVER_NAME, -1,
                                    String.format("%d is not a valid room id!",
                                            messageRecieved.getDestination()), CommunicationType.ERROR);

                            writeToClient.writeObject(errorMessage);
                        }
                    }
                } catch( IOException ioe ) {
                    disconnectRoom(true);
                    break;
                } catch( ClassNotFoundException cnfe ) {
                    System.err.printf("Invalid message class recieved \n");
                    cnfe.printStackTrace();
                }
            }
        }

        private <E extends Serializable> void validate(CommunicationMessage<E> connectionInfo) {
            String clientName = (String)connectionInfo.getContents();
            
            CommunicationMessage<?> loginResponse;
            if ( clientUsernames.contains(clientName) ) {
                String errorString = "Username already exists\n";
                loginResponse = new CommunicationMessage<>(SERVER_NAME, SERVER_ID, errorString,
                        CommunicationType.LOGIN_FAILURE);
                loginResponse.setSenderId(SERVER_ID);
            } else {
                this.clientName = clientName;
                loginResponse = new CommunicationMessage<>(SERVER_NAME, COMMON_ROOM_ID, userId,
                        CommunicationType.LOGIN_SUCCESS);
                loginResponse.setSenderId(SERVER_ID);
                joinServer();
            }
            sendMessage(loginResponse);
        }

        private void joinServer() {
            clientUsernames.add(clientName);
            joinCommonWindow(userId);
            String joined = String.format("%s has joined the server!", clientName);
            CommunicationMessage<String> joinedMessage = new CommunicationMessage<>(SERVER_NAME, COMMON_ROOM_ID, joined,
                    CommunicationType.CHAT);
            joinedMessage.setSenderId(-1);

            CommonWindow globalRoom = commonRoom.get(COMMON_ROOM_ID);
            msgSendToWindow(joinedMessage, globalRoom);
        }
        
    }

}
