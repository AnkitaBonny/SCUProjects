/*
Author: Yiqiao Li, Jonathan Jeng and Ankita Mukherjee
Class: COEN283 -- Operating Systems
Prof: Amr Elkady
Assignment: Final Project

Java Class Overview:
    *   This InterruptibleRequestHandler class is an implementation of each handler per http request received by the server
    *   Each instance of the class represents the life cycle of a single error checked http request
    *   The class reads the requested local file into a byte[] as soon as it is instantiated
    *   It keeps an index of the lowest byte not processed in the array
    *   Each time the start() method is invoked, a new thread is created to continue writing out to the socket from where it discontinued last time
    *   Each thread contains a loop that listens for incomming interrupts to the thread in case the request manager decides to do so
    *   Interrupts can include
        *   Time out interruption from Round Robin school of algorithms
        *   Preemption caused by SRPT algorithm
 */
 
package multithreadedserver;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class InterruptibleRequestHandler implements Runnable{

    public byte[] byteArray;
    public int remainingBytes;
    public int index;
    public String file;
    public String fileName;
    public Socket socket;
    public DataOutputStream out;
    public Thread t;
    public boolean over;
    public int priority;
    private boolean started;
    public String name;
    public int switchCount;
    public String requestType;

//time measurement
    public long arrival;
    public long start;
    public long finish;

    public long responseTime;
    public long turnaroundTime;
    public double slowdown;
    public long filesize;
    public long burstTime;
    public long weight;


    InterruptibleRequestHandler(DataOutputStream out,String file,int nameIndex,int priority) throws Exception{
        this.byteArray = null;
        this.out = out;
        this.over = false;
        this.priority = priority;
        this.file = file;
        this.started = false;
        this.name = "request "+nameIndex;
        this.switchCount = 0;
        File fileObject = new File (this.file);
        InputStream is = new FileInputStream(this.file);
        this.byteArray = new byte[is.available()];
        is.read(this.byteArray);
        this.remainingBytes = this.byteArray.length;

        this.fileName = file.substring(file.lastIndexOf("/")+1,file.lastIndexOf("."));
        System.out.println("file name: "+this.fileName);

        switch(this.fileName){
            case "file1": this.filesize = 238;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=7;break;
            case "file2": this.filesize = 96;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=6;break;
            case "file3": this.filesize = 45;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=5;break;
            case "file4": this.filesize = 36;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=4;break;
            case "file5": this.filesize = 28;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=3;break;
            case "file6": this.filesize = 18;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=2;break;
            case "file7": this.filesize = 14;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=1;break;
            case "music": this.filesize = 4672;this.burstTime=Math.floorDiv(this.filesize, 5);this.weight=150;break;
            default: this.filesize = 0;
        }

    }

    public void start(){
        this.t = new Thread(this);
        this.t.start();
        if(!this.started){
            this.start = System.currentTimeMillis();
        }
        this.started = true;
    }

    public synchronized void run(){
        try{
            while(this.index<byteArray.length){
                if(this.t.isInterrupted()){
                    this.switchCount++;
                    this.remainingBytes = this.byteArray.length - this.index;
                    return;
                }
                out.write(Arrays.copyOfRange(this.byteArray, this.index, Math.min(this.index+32, this.byteArray.length)));
                this.index += 32;
                this.remainingBytes -= 32;
                this.burstTime = this.remainingBytes/(1024*5);

            }

            if(this.index>=this.byteArray.length){
                System.out.println(this.name+" Response Complete: "+this.requestType);
                this.out.close();
                this.over = true;
                this.finish = System.currentTimeMillis();

                this.responseTime = this.start - this.arrival;
                this.turnaroundTime = this.finish - this.arrival;
                this.slowdown = (this.turnaroundTime*100)/this.filesize;
                System.out.println("Task Response Time: "+this.responseTime);
                System.out.println("Task Turnaround Time: "+this.turnaroundTime);
                System.out.println("Context Switch Count: "+this.switchCount);
                writeStats_overall("serverData_overall.txt");

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void writeStats_overall(String file) throws IOException{
        File result = new File("/Users/Richard/NetBeansProjects/MultithreadedServer/src/multithreadedserver/"+file);
        FileWriter fw = new FileWriter(result,true);
        BufferedWriter bw = new BufferedWriter(fw);
        fw.write(this.responseTime+","+this.turnaroundTime+","+this.slowdown+","+this.filesize+","+"\n");
        fw.flush();
        fw.close();
    }

    public void writeStats_response(String file) throws IOException{
        File result = new File("/Users/Richard/NetBeansProjects/MultithreadedServer/src/multithreadedserver/"+file);
        FileWriter fw = new FileWriter(result,true);
        BufferedWriter bw = new BufferedWriter(fw);
        fw.write(this.responseTime+",");
        fw.flush();
        fw.close();
    }
    public void writeStats_tnad(String file) throws IOException{
        File result = new File("/Users/Richard/NetBeansProjects/MultithreadedServer/src/multithreadedserver/"+file);
        FileWriter fw = new FileWriter(result,true);
        BufferedWriter bw = new BufferedWriter(fw);
        fw.write(this.turnaroundTime+",");
        fw.flush();
        fw.close();
    }
}
