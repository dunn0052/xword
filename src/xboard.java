//imports
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.lang.StringBuilder;

class Xboard{
	
  //flags
  public boolean SYMMETRIC = true;
	
  //dictionaries
  public List<String> dict;
  private String WA = "./csv/words_alpha.csv";
  private String DW = "./csv/dictionary.csv";
  private String CC = "./csv/corncob_caps.csv";
  private String WD = "./csv/words.csv";
  //percent of board blocks
  private static final int BLOCKPERCENT = 9; //as guidline on wikipedia /2 as it's symmetric
  // Default values
  private static final char BLOCK = '\u25A0';
  private static final char SPACE = '_';
  private static final int MAXWORDS = 78;
  private static final int MINWORDLEN = 3;
  private static final int MAXWORDLEN = 15;   // should be size
  //board vars
  private Tile[][] board;
  private int size; //dimension
  private Random generator;
  public List<WordRun> downWords = new ArrayList<WordRun>();
  public List<WordRun> acrossWords = new ArrayList<WordRun>();


// board constructor
  public Xboard(int size){
    this.size = size;
    this.board = new Tile[size][size];
  }


public void addBlocks(){
  //random block generator
  this.generator = new Random();
  //container for block chance
  int chance = 0;

  //make blocks
  for(int i = 0; i < this.size; i++){
    for(int j = 0; j < this.size; j++){
      chance = Math.abs(this.generator.nextInt()%100);
      if(chance <= BLOCKPERCENT){
        // diagonalize block spaces
    	  addBlock(i, j);
      }
    }
  }
}

public void addBlock(int i, int j ) {
	// symmetric
	if(SYMMETRIC) {
		addLetter(i, j, BLOCK);
		addLetter(j, i, BLOCK);
	}
	else {
		addLetter(i,j, BLOCK);
	}
}

public void addSpaces() {
	for(int i = 0; i < this.size; i++) {
		for(int j = 0; j < this.size; j++) {
			addLetter(i, j, SPACE);
		}
	}
}

public void addLetter(int i, int j, char letter) {
	try {
		if(board[i][j] != null) {
		this.board[i][j].letter = letter;
		}
		else {
			board[i][j] = new Tile(letter, 0, 0);
		}
	}
	catch(ArrayIndexOutOfBoundsException l) {
		System.out.println("Error :" + l);
		System.out.println("Error adding " + letter);
	}
}

public void addNumbers(){
  //word numbering -- really ugly but it works
  int downTotal = 0;
  int acrossTotal = 0;
  int across = 0;
  int down = 0;
  //make word numberings if either to right/bottom of edge or BLOCK
  for(int i = 0; i < this.size; i++){
    for(int j = 0; j< this.size; j++){
    	//check if new down
    	if(board[i][j].letter != BLOCK) {
        if(i == 0 || board[i -1][j].letter == BLOCK) {
        	//top is end or black square
        	WordRun d = new WordRun(i,j);
        	downTotal++;
        	//save down coord
        	this.downWords.add(d);
        	down = downTotal;//begin new down
        }
        else {
        	down = board[i-1][j].down;
        }
        
        //check if new across
        if(j == 0 || board[i][j-1].letter == BLOCK){
        //left is end or black square
          WordRun a = new WordRun(i,j);
          acrossTotal++;
          //save across coord
          this.acrossWords.add(a);
          across = acrossTotal;//begin new across
        }
        else {
        	across = board[i][j-1].across;
        }
        
        board[i][j].down = down;
        board[i][j].across = across;
        //running length total
        this.acrossWords.get(across-1).len++;
        this.downWords.get(down-1).len++;
    	}
    }
  }
}


public void putAcross(String word, int run) {
	WordRun coord = this.acrossWords.get(run-1);
	int i = coord.i;
	int j = coord.j;
	if(coord.len != word.length()) {
		System.out.format("%s won't fit in %d space(s)\n", word, coord.len);
		return;
	}
	for (int index = 0; index < word.length(); index++){
	    char c = word.charAt(index);
	    addLetter(i, j, c);
	    j++;
	}
}

public void putDown(String word, int run) {
	WordRun coord = this.downWords.get(run-1);
	int i = coord.i;
	int j = coord.j;
	if(coord.len != word.length()) {
	System.out.format("%s won't fit in %d space(s)\n", word, coord.len);
		return;
	}
	for (int index = 0; index < word.length(); index++){
		char c = word.charAt(index);
		addLetter(i, j, c);
		i++;
		}
}

public String findWord(int num, char run) {
	WordRun r;
	StringBuilder s = new StringBuilder("");
	if(run == 'd') {
		r = this.downWords.get(num-1);
		for(int i = 0; i < r.len; i++) {
			Tile t = board[r.i+i][r.j];
			if(t.letter == SPACE) {
				s.append(".");
			}
			else {
				s.append(t.letter);
			}
		}
	}
	else {
		r = this.acrossWords.get(num-1);
		for(int i = 0; i < r.len; i++) {
			Tile t = board[r.i][r.j+i];
			if(t.letter == SPACE) {
				s.append(".");
			}
			else {
				s.append(t.letter);
			}
		}
	}

	//set up regex pattern
	System.out.println(s);
	Pattern p = Pattern.compile(s.toString());
	Matcher m;
	List<String> l = new ArrayList<String>();
	for(String word : this.dict) {
		m = p.matcher(word);
		if(m.matches())
			l.add(m.group());
	}
	if(l.size() > 0) {
    String e = l.get(generator.nextInt(l.size()));
    return e;
	}
	return "";
}

// test set up
  public void init(){
	addSpaces();
    addBlocks();
    addNumbers();
    //from https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt
    this.dict = CSVReader.CSVList(this.DW);
  }

// print board
  public void showBoard(){
    for(int i = 0; i < this.size; i++){
      System.out.print("|");
      for(int j = 0; j < this.size; j++){
    	  Tile t = board[i][j];
          //System.out.format("%-2d%-2d%c|",t.down,t.across, t.letter);
    	  System.out.format("%c|", board[i][j].letter);
      }
      System.out.print("\n");
    }
  }


// autofill rest of the board
public void randomFill() {
    String randoma;
    String randomb;
    int wordmax = Math.max(this.acrossWords.size(), this.downWords.size());
    for(int i = 1; i <= wordmax;i++) {
    	this.showBoard();
    	if(i <= this.downWords.size()) {
    		randoma = this.findWord(i, 'd');
    		this.putDown(randoma, i);
    	}
    	if(i <= this.acrossWords.size()) {
    		randomb = this.findWord(i, 'a');
    		this.putAcross(randomb, i);
    	}
    	
    }
}

}

class Test{
	private static final int BOARDSIZE = 15;
  public static void main(String[] args){

    System.out.println("Down Left Character");
    Xboard test = new Xboard(BOARDSIZE);
    test.init();
    test.randomFill();
    test.showBoard();
  }
}
