package server;
import java.util.Set;
import java.util.TreeSet;

public class CommonWindow {
    private static int commonRoomIdCounter = 0;
    private final int id;
    private final String roomName;
    private Set<Integer> userSet;

    public CommonWindow(String roomName) {
        this.id = commonRoomIdCounter;
        this.roomName = roomName;
        commonRoomIdCounter++;
        this.userSet = new TreeSet<>();
    }

    public void addUser(int userId) {
        this.userSet.add(userId);
    }

    public void removeUser(int userId) {
        this.userSet.remove(userId);
    }

    public String getName() {
        return this.roomName;
    }

    public Set<Integer> getUsers() {
        return this.userSet;
    }

    public int getId() {
        return this.id;
    }

    public boolean equals(Object other) {
        if( other instanceof CommonWindow ) {
            return id == ((CommonWindow)other).getId();
        }

        return false;
    }

    public int hashCode() {
        return this.id;
    }
}
