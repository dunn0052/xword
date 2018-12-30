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
			Thread.sleep(10000);
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

