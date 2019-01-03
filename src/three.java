import java.util.concurrent.TimeUnit;

public class three{
	public static int BOARDSIZE = 15;
	public static String DW = "../csv/dictionary.csv";
	public static boolean symmetric = true;
	public static int time = 5; 
	public static void main(String[] args) {
		if(args.length == 4) {
			BOARDSIZE = Integer.parseInt(args[0]);
			symmetric = Boolean.parseBoolean(args[1]);
			time = Integer.parseInt(args[3]);
			DW = args[2];
		}
    	Xboar3d test = new Xboar3d(BOARDSIZE, symmetric, DW, time);
    	test.init();
    long startTime = System.nanoTime();
   	 test.randomFill();
   	 for(int k = 0; k < test.size; k++){
   	 test.showBoard(k);
   	 test.showSide(k);
   	 System.out.println("");
   	 
   	 try {
   	 TimeUnit.SECONDS.sleep(1);
	}
	catch(InterruptedException ex) {
		}
	}
	
	long endTime = (System.nanoTime() - startTime)/1000000000;
	System.out.println(endTime);
  }
}
