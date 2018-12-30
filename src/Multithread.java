import java.lang.Thread;

class TimingThread extends Thread 
{ 
	public long begin = 0;
	private boolean end = true;
    public void run() 
    { 
        try
        { 
			long startTime = System.nanoTime();
			Thread.sleep(1);
			long endTime = System.nanoTime() - startTime;
			System.out.println(endTime);
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    }
} 

// Main Class 
public class Multithread 
{ 
    public static void main(String[] args) 
    { 
            TimingThread timerthread = new TimingThread(); 
            timerthread.start();
                synchronized(timerthread){
				try{
					timerthread.wait();
				} catch(InterruptedException e){}
				
			synchronized(timerthread){
					timerthread.notify();
			}		
    }
    } 
} 
