package communication;
public enum CommunicationType {
    CREATE_ROOM("creategrouproom"),
    JOIN_ROOM("joingrouproom"),
    LEAVE_ROOM("leavegrouproom"),
    LIST_USERS("listofusers"),
    LIST_ROOMS("listofrooms"),
    CONNECTION_SUCCESS,
    LOGIN_SUCCESS,
    LOGIN_FAILURE,
    JOIN_ROOM_SUCCESS,
    JOIN_ROOM_FAILURE,
    LEAVE_ROOM_SUCCESS,
    LEAVE_ROOM_FAILURE,
    ERROR,
    AUTHENTICATION,
    CHAT,
    FILE,
    USER_LOGIN;
    String commandString;
    CommunicationType() {
        commandString = "";
    }

    CommunicationType(String stringRepresentation) {
        commandString = stringRepresentation;
    }

    public String getCommandString() {
        return this.commandString;
    }

    public static CommunicationType getTypeFromCommand(String commandString) {
        for ( CommunicationType type : CommunicationType.values() ) {
            if ( type.getCommandString().equals(commandString) ) {
                return type;
            }
        }
        return null;
    }

}
