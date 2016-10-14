package server;
public class MyServer {
    public static void main( String[] args ) {
        if( args.length < 1 ) {
            System.err.println("Enter valid port number");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        Server server = new Server(portNumber);
        server.acceptConn();
    }
}
