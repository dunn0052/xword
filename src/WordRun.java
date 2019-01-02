
public class WordRun {
	int i;
	int j;
	int k;
	int len; // length of run
	String clue;
	WordRun(int i, int j, int k){
		this.i=i; 
		this.j=j;
		this.k=k;
	}
	
	public void addClue(String clue) {
		this.clue = clue;
	}
}
