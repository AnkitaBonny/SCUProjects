Author: Yiqiao Li, Jonathan Jeng and Ankita Mukherjee
Class: COEN283 -- Operating Systems
Prof: Amr Elkady
Assignment: Final Project



This is a functional webserver which listens for connections on a socket ( bound to a specific port on a host machine). Client connects and retrieve file from server using multithreading via particular scheduling algorithm, in order to give faster and fairer response.

Instructions for running your program: Run from command line :
 1) compile :javac MyWebServer.java
 2) compile :javac requestManager.java
 3) compile :javac InterruptibleRequestHandler.java
 4) run : java MyWebServer -document_root "give path of server root” -port 'Give port number: 4567’
 5) run the script file ( strictest.sh) for multiple request to the server.
