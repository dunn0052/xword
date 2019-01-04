import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class three{
	public static int BOARDSIZE = 15;
	public static String DW = "../csv/dictionary.csv";
	public static boolean symmetric = true;
	public static int time = 5; 
	public static void main(String[] args) throws IOException {
		if(args.length == 4) {
			BOARDSIZE = Integer.parseInt(args[0]);
			symmetric = Boolean.parseBoolean(args[1]);
			time = Integer.parseInt(args[3]);
			DW = args[2];
		}
    	Xboar3d test = new Xboar3d(BOARDSIZE, symmetric, DW, time);
    	long startTime = System.nanoTime();
    	test.init();
		test.randomFill();
   	 	long endTime = (System.nanoTime() - startTime)/1000000000;
   	 	System.out.println(endTime + "seconds");
   	 System.out.println("Finished board");
   	 boolean front = true;
   	 int k = 0;
   	 int l;
   	 char c;
   	 test.showBoard(k);
   	 while(true) {	 
			l = System.in.read();
			c = (char)l; 		 
			if(c == 'w' && k < test.size -1 && front) {
				k++;
				test.showBoard(k);
				}
			else if(c == 's' && k > 0 && front) {
				k--;
				test.showBoard(k);	
			} 
			else if(c == 'w' && k < test.size -1 && !front) {
				k++;
				test.showSide(k);
			}
			else if(c == 's' && k > 0 && !front) {
				k--;
				test.showSide(k);
			}
			else if(c == 'd' && front) {
				test.showSide(k);
				front = !front;
			}
			else if(c == 'd' && !front) {
				test.showBoard(k);
				front = !front;
			}
			else if(c == 'a') {
				if(front)
				test.showBoard(k);
				if(!front)
				test.showSide(k);
			}
			else if(c == 'e') {
				test.showBoard(k);
				test.showSide(k);
			}
			
			
		try {
			TimeUnit.MILLISECONDS.sleep(1);
			}
		catch (InterruptedException e) {}
  }
  
}
}
