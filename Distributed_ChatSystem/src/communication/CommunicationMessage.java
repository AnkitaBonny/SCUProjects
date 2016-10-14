package communication;

import java.io.Serializable;

public final class CommunicationMessage<E extends Serializable> implements Serializable {

    private static final long serialVersionUID = 20L;
    public static final int SERVER_ID = -1;
    private final String senderName;
    private final int receiverRoom;
    private final CommunicationType type;
    private final E msgContent;
    private int senderId;

    public CommunicationMessage(String from, int destination, E contents, CommunicationType type) {
        this.senderName = from;
        this.receiverRoom = destination;
        this.msgContent = contents;
        this.type = type;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getSenderId() {
        return this.senderId;
    }

    public String getSender() {
        return this.senderName;
    }

    public int getDestination() {
        return this.receiverRoom;
    }

    public E getContents() {
        return this.msgContent;
    }

    public CommunicationType getType() {
        return this.type;
    }
}
