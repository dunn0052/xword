
public class WordRun {
	int i;
	int j;
	int len; // length of run
	String clue;
	WordRun(int i, int j){
		this.i=i; 
		this.j=j;
	}
	
	public void addClue(String clue) {
		this.clue = clue;
	}
}
