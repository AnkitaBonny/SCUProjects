package communication;
import java.io.Serializable;

@FunctionalInterface
public interface CommunicationHandler {
    public <E extends Serializable> void recieveMessage(CommunicationMessage<E> message);
}
