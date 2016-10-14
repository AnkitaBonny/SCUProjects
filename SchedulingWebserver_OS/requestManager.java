/*
Author: Yiqiao Li, Jonathan Jeng and Ankita Mukherjee
Class: COEN283 -- Operating Systems
Prof: Amr Elkady
Assignment: Final Project

Java Class Overview:
    *   This is the Request Manager Class created to implement scheduling algorithms
    *   Every new request will be wrapped into a Request Handler class and inserted into a queue within this Manager
    *   The manager will decide which schedualing algorithm to use, and inturn execute each task in the queue accordingly
    *   The manager class contains a single managerThread that keeps running as soon as the server started
    *   The managerThread keeps checking if the task queue is empty. If not, it will process tasks contained in it accordingly

    *   The manager task queue takes two possible forms according to the algorithm it implements: LinkedList and PriorityQueue
        *   LinkedList task queue is implemented for FIFO and RR (Round Robin)
        *   PriorityQueue task queue is implemented for FRR (Fair Round Robin), SJF (Shortest Job First) and SRPT (Shortest Remaining Process Time)
        *   To implement each algorithm you must check the following three options:
            *   What data structure is the task queue implementing?
            *   Is the insertTask method correctly commented or uncommented?
            *   Is the correct Algorithm method used in the run method section


*/
package multithreadedserver;
import java.lang.*;
import java.lang.Thread.*;
import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class requestManager implements Runnable{

    
//*********************************************
//  Before implementing FRR, SJF and SRPT  
//  remember to use the PriorityQueue in the following line
//  and comment out the LinkedList line right after
    public PriorityQueue<InterruptibleRequestHandler> taskQueue;
//  To implement FIFO and RR
//  use LinkedList in the next line and comment out PriorityQueue
    //public LinkedList<InterruptibleRequestHandler> taskQueue;
//******************************************
  
    public PriorityQueue<InterruptibleRequestHandler> maxTracker;
    public Thread managerThread;
    public InterruptibleRequestHandler currentTask = null;
    private final long TIMEQUANTUM = 10;
    
    public long totalBurst;
    public long maxBurst;
    public long taskCount;
    public long avgBurst;
    
    public FileWriter fw;
    public BufferedWriter bw;

    public requestManager(){
        
        
//******************************************************  
//If PriorityQueue is chosen for the task queue, the following Comparator and PriorityQueue construction should be used
        //priority related queue construction
        Comparator<InterruptibleRequestHandler> cmp = new Comparator<InterruptibleRequestHandler>(){
            @Override
            public int compare(InterruptibleRequestHandler o1, InterruptibleRequestHandler o2) {
                return Long.compare(o1.burstTime, o2.burstTime);
            }    
        };
        this.taskQueue = new PriorityQueue<InterruptibleRequestHandler>(cmp);
        this.maxTracker = new PriorityQueue<InterruptibleRequestHandler>(cmp.reversed());
//If LinkedList is chosen for the task queue, the following constructors should be used accordingly        
        //this.taskQueue = new LinkedList<InterruptibleRequestHandler>();
//********************************************************     
        this.totalBurst = 0;
        this.maxBurst = 0;
        this.avgBurst = 0;
        this.taskCount = 0;
        
        try{
            File log = new File("/Users/Richard/NetBeansProjects/MultithreadedServer/src/multithreadedserver/log.txt");
            this.fw = new FileWriter(log);
            this.bw = new BufferedWriter(this.fw);
            System.out.println("log file created");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public synchronized void insertTask(InterruptibleRequestHandler task) throws Exception{
        try{
//***********************************   
//This entire section enclosed in the if{} block should be commented out unless
//you are implementing SRPT. If you are, you must include this section
//SRPT insertion 
//            if(this.currentTask != null){
//                if(task.remainingBytes<this.currentTask.remainingBytes){
//                    this.currentTask.t.interrupt();
//                    this.fw.write(task.name+" "+task.fileName+" size: "+task.filesize+" arrives replaces current task---   queue size: "+this.taskCount+"\n");
//                    this.fw.write("current task remaining bytes: "+this.currentTask.remainingBytes+"\nnew task remaining bytes: "+task.remainingBytes+"\n");
//                    this.insertTask(this.currentTask);
//                    this.maxTracker.add(task);
//                    this.fw.write("current max burst time: "+this.maxTracker.peek().burstTime);
//                    this.currentTask = task;
//                    this.currentTask.start();
//                    this.fw.write(this.currentTask.name+" "+this.currentTask.fileName+" "+this.currentTask.filesize+" start executing\n");
//                    return;
//                }
//            }          
//***********************************         
            this.taskQueue.add(task);
            this.maxTracker.add(task);
            this.fw.write("current max burst time: "+this.maxTracker.peek().burstTime+"\n");
            this.totalBurst += task.burstTime;
            if(this.taskCount==0){
                this.maxBurst = task.burstTime;
            }else{
                this.maxBurst = Math.max(this.maxBurst, task.burstTime);
            }
            this.taskCount++;
            this.avgBurst = this.totalBurst/this.taskCount;

            this.fw.write(task.name+" "+task.fileName+" size: "+task.filesize+" enters queue   ---   queue size: "+this.taskCount+"\n");
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public synchronized InterruptibleRequestHandler popTask() throws Exception{
        if(this.taskQueue.isEmpty()){
            return null;
        }
        InterruptibleRequestHandler task = this.taskQueue.poll();
        this.totalBurst -= task.burstTime;
        this.taskCount--;
        this.avgBurst = this.totalBurst/this.taskCount;
        return task;
    }
    
    
    public void start(){
        if(this.managerThread==null){
            this.managerThread = new Thread(this);
            this.managerThread.start();
        }else{
            this.managerThread.start();
        }
        System.out.println("Manager Thread Started");
    }
    public void run(){
        while(true){
            try{
//*******************************  
//Uncomment the scheduling algorithm you want to implement
//Remember to uncomment or comment out corresponding task queue structure and 
//the insertTask method
               //FIFO();
               FRR();
               //RR();
               //SJF();
               //SRPT();
//***************************          
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
    
    protected void SRPT() throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            this.currentTask = taskQueue.poll();
            this.currentTask.start();
            this.fw.write(this.currentTask.name+" "+this.currentTask.fileName+" "+this.currentTask.filesize+" start executing\n");
        }
    }

    protected void SJF() throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            this.currentTask = taskQueue.poll();
            this.currentTask.start();
            this.currentTask.t.join();
        }
    }
    
    protected void FIFO() throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            this.currentTask = taskQueue.poll();
            this.currentTask.start();
            this.currentTask.t.join();
        }
    }
    protected synchronized void RR() throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            try{
                this.currentTask = taskQueue.poll();        
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                this.currentTask.start();
                Future<String> future = executor.submit(new SleepTask());
                System.out.println(future.get(TIMEQUANTUM, TimeUnit.MILLISECONDS));  
            }catch(Exception e){
                System.out.println("Times out!");
                this.currentTask.t.interrupt();
                this.fw.write("task: "+this.currentTask.name +" exceeded TQ and interrupted\n");
                this.currentTask.t.join();
                synchronized (this.currentTask){
                    if(!this.currentTask.over){
                        insertTask(this.currentTask);
                    }
                }
            }
        
        }
    }
    
    protected void FRR() throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            try{
                Long TQ;
                synchronized (this.taskQueue){
                    System.out.println("avg Burst: "+this.avgBurst);
                    long maxBurst = this.maxTracker.peek().burstTime;
                    this.currentTask = this.taskQueue.poll();
                    this.maxTracker.remove(this.currentTask);
                    TQ = Math.floorDiv(this.avgBurst+this.maxBurst,this.currentTask.weight);
                    TQ = Math.max(10, TQ);
                    this.fw.write("avgBurst: "+this.avgBurst+" maxBurst: "+maxBurst+" weight: "+this.currentTask.weight+" TQ: "+TQ+"\n");
                    this.fw.write(this.currentTask.name+" "+this.currentTask.fileName+" "+this.currentTask.filesize+" start executing\n");
                    this.totalBurst -= this.currentTask.burstTime;
                    this.taskCount--;
                    this.avgBurst = this.taskCount==0?0:Math.floorDiv(this.totalBurst,this.taskCount);
                    
                    if(TQ>=this.currentTask.burstTime){
                        this.currentTask.start();
                        this.fw.write(this.currentTask.name+" "+this.currentTask.fileName+" "+this.currentTask.filesize+" allowed to fully execute\n");
                    }else{
                        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                        this.currentTask.start();
                        this.fw.write(this.currentTask.name+" "+this.currentTask.fileName+" "+this.currentTask.filesize+" given "+TQ+" ms to execute\n");
                        Future<String> future = executor.submit(new SleepTask());
                        System.out.println(future.get(TQ, TimeUnit.MILLISECONDS));
                    }
                    

                }
 
                
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("Times out!");
                this.currentTask.t.interrupt();
                this.fw.write(this.currentTask.name +" exceeded TQ and interrupted\n");
                this.currentTask.t.join();
                synchronized (this){
                    if(!this.currentTask.over){
                        insertTask(this.currentTask);
                    }else{
                        this.fw.write("task: "+this.currentTask.name+" completed\n");
                        
                    }
                }
            }
        
        }
    }
        
    protected void SRPT(LinkedList<InterruptibleRequestHandler> taskQueue) throws Exception{
        System.out.print("");
        if(this.taskQueue.size()!=0){
            try{
                System.out.println("Current Queue Size: "+this.taskQueue.size());
                this.currentTask = taskQueue.poll();
                
                this.totalBurst -= this.currentTask.burstTime;
                this.taskCount--;
                this.avgBurst = this.totalBurst/this.taskCount;          
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                this.currentTask.start();
                Future<String> future = executor.submit(new SleepTask());
                System.out.println(future.get(TIMEQUANTUM, TimeUnit.MILLISECONDS));
                
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("Times out!");
                this.currentTask.t.interrupt();
                this.currentTask.t.join();
                if(!this.currentTask.over){
                    insertTask(this.currentTask);
                }
            }
        
        }
    }

    class SleepTask implements Callable<String>{ 
        @Override
        public String call(){
            try{
                Thread.sleep(5000);
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return "done";
        }
    }
    
}
