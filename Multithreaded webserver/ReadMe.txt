Name : Ankita Mukherjee

Assignment name : HW1

Date : 01/28/2016

High-level description of the assignment and what your program(s) does:
Its a functional webserver which listens for connections on a socket ( bound to a specific port on a host machine). 
Client connects and retrieve file from server and send response using multithreading.
It retrieves GET request only. Supports index.html files and image files.

It gives back response code to header { content type, content-length, date)
It supports status code 200( success) , 404 ( file not found ), 403 ( forbidden access) , 400 ( Bad Request)

A list of submitted files : MyWebServer.java, ReadMe.txt, Script.pdf ,part2 folder

Instructions for running your program: Run from command line :
 1) compile :javac MyWebServer.java
 2) run: java MyWebServer -document_root "give your path where scu .edu downloaded" -port 'Give your port number(e.g. 8002)'


Any other information you want us to know: I tried implementing part 2 of the question, which I have seperate code,named WebServer_Version2.java under fold part2
Running information is same as previous. Script file has all the screenshots of Part1 implementation in detail.