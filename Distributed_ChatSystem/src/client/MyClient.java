package client;
import communication.*;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MyClient extends JFrame {

    // Chat client components
    private Client client;
    private String clientName, hostname;
    private int portNumber;
    private SettingsOfClient settings;

    // GUI Components
    private final int WIDTH = 700;
    private final int HEIGHT = 400;
    private JTabbedPane roomsPane;
    private Map<Integer,Window> rooms;
    private JTextField messageToSend;
    private JButton send, cancel, sendFile;

    private boolean recordingAudio = false;

    /**
     * Creates a new chat client which will connect to the specified server.
     *
     * settings ClientSettings object from which to draw connection settings
     */
    public MyClient( SettingsOfClient settings ) {
        this.settings = settings;
        this.clientName = settings.getClientName();
        this.hostname = settings.getHostname();
        this.portNumber = settings.getPortNumber();

        initFrame();
        initComponents();

        this.client = new Client(clientName, hostname, portNumber);

        // Login handlers
        this.client.registerHandler(CommunicationType.LOGIN_SUCCESS, this::displayWelcome);
        this.client.registerHandler(CommunicationType.LOGIN_FAILURE, this::displayRetryDialog);

        // Communication message handlers
        this.client.registerHandler(CommunicationType.CHAT, this::displayMessage);
        this.client.registerHandler(CommunicationType.FILE, this::receiveFile);
        //this.client.registerHandler(CommunicationType.AUDIO, this::receiveAudio);

        // Command reply messages
        this.client.registerHandler(CommunicationType.JOIN_ROOM_SUCCESS, this::joinRoom);
        this.client.registerHandler(CommunicationType.JOIN_ROOM_FAILURE, this::joinRoomFailure);
        this.client.registerHandler(CommunicationType.LEAVE_ROOM_SUCCESS, this::leaveRoom);
        this.client.registerHandler(CommunicationType.LEAVE_ROOM_FAILURE, this::leaveRoomFailure);
        //this.client.registerHandler(MessageType.CREATE_ROOM_SUCCESS, this::joinRoom);

  
        this.client.makeConnection();
    }

    /**
     * Initializes all of the components present in the GUI. This includes buttons, text boxes,
     * action listeners, etc.
     */
    private void initComponents() {
        rooms = new HashMap<>();
        Window globalRoom = new Window(0, (int)(WIDTH * .95), (int)(HEIGHT * .7));
        rooms.put(0, globalRoom);

        roomsPane = new JTabbedPane(SwingConstants.TOP);
        roomsPane.addTab("Global room", globalRoom);
        add(roomsPane);

        messageToSend = new JTextField(15);
        messageToSend.addActionListener(ae -> sendStringMessage());
        add(messageToSend);

        send = new JButton("Send");
        send.setBackground(Color.CYAN);
        send.addActionListener(ae -> sendStringMessage());
        add(send);

        cancel = new JButton("Cancel");
        cancel.setBackground(Color.CYAN);
        cancel.addActionListener(ae -> messageToSend.setText(""));
        add(cancel);

        sendFile = new JButton("Send File");
        sendFile.setBackground(Color.CYAN);
        sendFile.addActionListener(ae -> sendFile());
        add(sendFile);
    }

    /**
     * Initializes frame settings. This includes things like the size of the frame, layout managers,
     * and the like.
     */
    private void initFrame() {
        // Frame settings
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setTitle("Chat Client - " + clientName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

       /* KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());*/

    }

    private int getCurrentRoom() {
        Window room = (Window) roomsPane.getSelectedComponent();
        for (Map.Entry<Integer, Window> roomEntry : rooms.entrySet() ) {
            if ( roomEntry.getValue().equals(room) ) {
                return roomEntry.getKey();
            }
        }
        return 0;
    }

    private void appendToRoom(String message, int roomId) {
        Window room = this.rooms.get(roomId);
        SwingUtilities.invokeLater(() -> room.append(message));
    }

    private void sendStringMessage() {
        String message = this.messageToSend.getText();
        int currentRoom = getCurrentRoom();

        if( ! message.trim().isEmpty() ) {
            if ( message.startsWith("/") ) {
                sendServerCommand(message.substring(1));
            } else {
                CommunicationMessage<String> m = new CommunicationMessage<>(clientName, currentRoom, message, CommunicationType.CHAT);
                this.client.writeMessage(m);
            }
        }

        this.messageToSend.setText("");
    }

    private void sendServerCommand(String message) {
        String command, contents;
        int endOfCommandName = message.indexOf(" ");

        if( endOfCommandName != -1 ) {
            command = message.substring(0, endOfCommandName);
            contents = message.substring(endOfCommandName + 1);
        } else {
            command = message;
            contents = String.valueOf(getCurrentRoom());
        }

        CommunicationType type = CommunicationType.getTypeFromCommand(command);
        if ( type != null ) {
            CommunicationMessage<String> m = new CommunicationMessage<>(clientName, CommunicationMessage.SERVER_ID, contents, type);
            m.setSenderId(this.client.getClientId());
            this.client.writeMessage(m);
        } else {
            displayMessage(new CommunicationMessage<>("Server", getCurrentRoom(), "Invalid command " + command, CommunicationType.ERROR));
        }
    }

    private <E extends Serializable> void receiveFile(CommunicationMessage<E> message) {
        System.out.printf("Received file message from id %d\n", message.getSenderId());
        // If I sent the file, don't make me download it
        if( message.getSenderId() == this.client.getClientId() ) {
            return;
        }

        // Ensure that the contents of the message is the byte array
        E messageContents = message.getContents();
        String sender = message.getSender();

        if( messageContents instanceof byte[] ) {
            // The user must confirm that they want to download the file
            int dialogAnswer = JOptionPane.showConfirmDialog(this,
                    String.format("%s has sent a file. Would you like to download it?", sender),
                    "File Download", JOptionPane.YES_NO_OPTION);

            if( dialogAnswer == JOptionPane.YES_OPTION ) {
                SwingUtilities.invokeLater(() -> {
                    JFileChooser jfc = new JFileChooser();
                    int returnVal = jfc.showSaveDialog(this);

                    // They must choose where to save the file
                    if( returnVal == JFileChooser.APPROVE_OPTION ) {
                        File file = jfc.getSelectedFile();

                        // Write the bytes to the file
                        try(FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write((byte[]) messageContents);
                            fos.flush();
                        } catch( IOException ioe ) {
                            JOptionPane.showMessageDialog(this, "Error saving file!", "Error!",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Message contents must contain file data!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendFile() {
        // Let the user choose a file to send
        int currentRoom = getCurrentRoom();
        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showOpenDialog(this);

        // If the user chose an option, read the file into a byte array
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            byte[] fileBytes = new byte[(int) file.length()];

            // Read in the bytes for the file
            try( FileInputStream fis = new FileInputStream(file) ) {
                fis.read(fileBytes);
            } catch( IOException ioe ) {
                System.err.printf("Error reading from %s\n", file.getName());
                ioe.printStackTrace();
                return;
            }

            // Create and send the message
            CommunicationMessage<byte[]> fileMessage = new CommunicationMessage<>(clientName, currentRoom, fileBytes,
                    CommunicationType.FILE);

            client.writeMessage(fileMessage);
        }
    }

    /*private void sendAudio() {
        try {
            // Get the microphone
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            // Set up the output stream for the audio data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            // Start recording in a separte thread
            Thread recordingThread = new Thread(() -> {
                System.out.println("Starting recording...");
                while( recordingAudio ) {
                    int numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    out.write(data, 0, numBytesRead);
                }
                System.out.println("Finished recording...");

                // Create new message
                byte[] audio = out.toByteArray();
                CommunicationMessage<byte[]> message = new CommunicationMessage<>(clientName, getCurrentRoom(), audio,
                    CommunicationType.AUDIO);

                System.out.println("Sending message...");
                client.writeMessage(message);
            });

            recordingThread.start();
        } catch( LineUnavailableException lue ) {
            System.err.println("Error while reading audio!");
            lue.printStackTrace();
        }
    }

    private <E extends Serializable> void receiveAudio(CommunicationMessage<E> message) {
        E messageContents = message.getContents();

        // Don't play audio sent by me
        if( message.getSenderId() == this.client.getClientId() ) {
            return;
        }

        // Add to the message history that an audio message was recieved
        String toDisplay = String.format("%s: [Audio Message]\n", message.getSender());
        appendToRoom(toDisplay, message.getDestination());

        if( messageContents instanceof byte[] ) {
            byte[] audioData = (byte[]) messageContents;

            // Get the speakers
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers;

            try {
                speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                speakers.open(format);
            } catch( LineUnavailableException lue ) {
                System.err.println("There was an error getting the audio output line!");
                lue.printStackTrace();
                return;
            }

            // Create a thread to play the audio
            Thread playThread = new Thread(() -> {
                speakers.start();
                speakers.write(audioData, 0, audioData.length);
                speakers.drain();
                speakers.stop();
                speakers.close();
            });
            playThread.start();
        }
    }*/

    private <E extends Serializable> void displayMessage(CommunicationMessage<E> message) {
        String toDisplay = String.format("%s: %s\n", message.getSender(), message.getContents());
        System.out.printf("Printing msg to chatroom %s\n", message.getDestination());
        appendToRoom(toDisplay, message.getDestination());
    }

    private <E extends Serializable> void displayWelcome(CommunicationMessage<E> message) {
        String toDisplay = String.format("You have connected to %s:%d!\n", hostname, portNumber);
        appendToRoom(toDisplay, message.getDestination());
    }

    private <E extends Serializable> void joinRoomFailure(CommunicationMessage<E> message) {
        String str = message.getContents().toString();
        SwingUtilities.invokeLater(() -> rooms.get(getCurrentRoom()).append(str));
    }

    private <E extends Serializable> void displayRetryDialog(CommunicationMessage<E> message) {
        String newUserId = JOptionPane.showInputDialog(this, message.getContents(), "Enter Username", JOptionPane.PLAIN_MESSAGE);
        CommunicationMessage<String> newLogin = new CommunicationMessage<>(newUserId, CommunicationMessage.SERVER_ID, newUserId, CommunicationType.USER_LOGIN);
        message.setSenderId(this.client.getClientId());
        client.writeMessage(newLogin);
    }

    private <E extends Serializable> void joinRoom(CommunicationMessage<E> message) {
        Window newRoom = rooms.get(message.getDestination());
        if( newRoom == null ) {
            newRoom = new Window(message.getDestination(), (int)(WIDTH * .95), (int)(HEIGHT * .7));
            rooms.put(message.getDestination(), newRoom);
            roomsPane.addTab(message.getContents().toString(), newRoom);
        }

        roomsPane.setSelectedComponent(rooms.get(message.getDestination()));

        String toDisplay = String.format("Welcome to room %s\n", message.getContents());
        appendToRoom(toDisplay, message.getDestination());
    }

    public <E extends Serializable> void leaveRoom(CommunicationMessage<E> message) {
        if ( message.getContents() instanceof Integer ) {
            Window rp = rooms.remove(message.getContents());
            roomsPane.remove(rp);
        }
    }

    public <E extends Serializable> void leaveRoomFailure(CommunicationMessage<E> message) {
        appendToRoom(String.format("%s: %s", message.getSender(), message.getContents()), getCurrentRoom());
    }

    /**
     * This is dispatcher which will handle sending audio events for audio messages.
     */
   /* private class KeyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            // Ignore the event if it was fired by the message field
            if( e.getSource() == messageToSend ) {
                return false;
            }

            // Otherwise, check if it was a press or release
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                // Ensure that the conditions are right to send an audio event
                if( e.getKeyCode() == settings.getTouchToTalkKey() && (!recordingAudio) ) {
                    recordingAudio = true;
                    sendAudio();
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED ) {
                // Ensure that the right button has been released
                if( e.getKeyCode() == settings.getTouchToTalkKey() ) {
                    recordingAudio = false;
                }
            }

            return false;
        }
    }*/

    public static void main( String[] args ) {
        SettingsOfClient settings;

        if( args.length > 2 ) {
            settings = SettingsOfClient.DEFAULT;
            settings.setClientName(args[0]);
            settings.setHostname(args[1]);
            settings.setPortNumber(Integer.parseInt(args[2]));
        } 
        else {
            settings = SettingsOfClient.loadSettings();

            if( settings.equals(SettingsOfClient.DEFAULT) || settings == null) {

                System.out.println("Showing settings dialog");
                //Dialog dialog = new Dialog(null);
                //dialog.setVisible(true);
                settings = SettingsOfClient.loadSettings();
            }
        }

        settings.setLastLoginDate(System.currentTimeMillis());
        SettingsOfClient.saveSettings(settings);

        // Create the chat client
        MyClient cc = new MyClient(settings);
        SwingUtilities.invokeLater(() -> cc.setVisible(true));
    }
}
