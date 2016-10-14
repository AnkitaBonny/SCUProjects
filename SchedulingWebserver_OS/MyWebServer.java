/*
Author: Yiqiao Li, Jonathan Jeng and Ankita Mukherjee
Class: COEN283 -- Operating Systems
Prof: Amr Elkady
Assignment: Final Project
Java Class Overview:
	* This class is to create a multithreaded webserver  which listens for connections on a socket ( bound to a specific port on a host machine).
	* Client connects and server accepts only GET request.
	* Each request to server is a task and assigned to requestManager which in turns gives the response back via multithreading approach.
	
*/

package multithreadedserver;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.tools.FileObject;
import java.text.SimpleDateFormat;
import java.nio.file.*;
import java.util.concurrent.locks.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MyWebServer {

	private final static String SERVERSTRING = "Scheduled Server";
	private static int PORT;
	private static String FILEPATH ;

	private static final Map<String, String> map = new HashMap<String, String>() {{
		put("html", "text/html");
		put("css", "text/css");
		put("png", "image/png");
		put("jpg", "image/jpg");
		put("jpeg", "image/jpeg");
		put("js", "application/js");
	}};

	private static void display(String code, String mime, int length, DataOutputStream out, String time) throws Exception {
		System.out.println(" (" + code + ") ");
		out.writeBytes("HTTP Code:" + code + " \r\n");
		out.writeBytes("Content-Type: " + map.get(mime) + "\r\n");
		out.writeBytes("Content-Length: " + length + "\r\n");
		out.writeBytes("Date: " + time + "\r\n");
		out.writeBytes(SERVERSTRING);
		out.writeBytes("\r\n\r\n");
	}

	private static void response(String inString, DataOutputStream out,String time) throws Exception {
            long startTime = System.nanoTime();
            String method = inString.substring(0, inString.indexOf("/")-1);
            String file = inString.substring(inString.indexOf("/")+1, inString.lastIndexOf("/")-5);
            String mime = file.substring(file.indexOf(".")+1);

            if(method.equals("GET")) {
                try {
                    // Open file
                    byte[] fileBytes = null;
                    System.out.println("file path is: "+FILEPATH+file);
                    File fileObject = new File (FILEPATH+file);

                    long checkDoneTime = System.nanoTime();
                    InputStream is = new FileInputStream(FILEPATH+file);
                    fileBytes = new byte[is.available()];
                    is.read(fileBytes);
                    long readDoneTime = System.nanoTime();
                    display("200", mime, fileBytes.length, out,time);
                    out.write(fileBytes);
                    long writeDoneTime = System.nanoTime();
                    System.out.println(file);
                    long totalTime = writeDoneTime - startTime;
                    long checkTime = checkDoneTime - startTime;
                    long readTime = readDoneTime - checkDoneTime;
                    long writeTime = writeDoneTime - readDoneTime;
                    long checkPercent = checkTime*100/totalTime;
                    long readPercent = readTime*100/totalTime;
                    long writePercent = writeTime*100/totalTime;

                    System.out.println("Check: "+checkPercent+"%");
                    System.out.println("Read: "+readPercent+"%");
                    System.out.println("Write: "+writePercent+"%");

                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("File not found");
                }

            }

	}

	public static void main(String args[]) throws Exception {

            int i=0;
            for (i=0; i<args.length; i++) {
                    System.out.println(" args :" + args[i]);

            }
            PORT = Integer.valueOf(args[3]);
            FILEPATH = args[1];
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Listening to connection..");

            requestManager manager = new requestManager();
            manager.start();
            int nameIndex=0;
            for(;;) {
                Socket connectionSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
                String inString = in.readLine();
                System.out.println("Request are :" + inString );
                Calendar cal = Calendar.getInstance();
                cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = "[" + sdf.format(cal.getTime()) + "] ";
                System.out.println(time + connectionSocket.getInetAddress().toString() + " " + inString);

                if(inString != null){
                    String method = inString.substring(0, inString.indexOf("/")-1);
                    String file = inString.substring(inString.indexOf("/")+1, inString.lastIndexOf("/")-5);
                    String mime = file.substring(file.indexOf(".")+1);
                    file = file.substring(0,file.indexOf("?")==-1?file.length():file.indexOf("?"));
                    if(method.equals("GET")) {
                        try {
                                String filename = FILEPATH+file;
                                InterruptibleRequestHandler task = new InterruptibleRequestHandler(out,filename,nameIndex,1);
                                nameIndex++;
                                task.arrival = System.currentTimeMillis();
                                manager.insertTask(task);

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
	}
}
