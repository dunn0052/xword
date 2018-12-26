//imports
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.lang.StringBuilder;

class Xboard{
	
  //dictionaries
  public List<String> dict;
  private String WA = "./csv/words_alpha.csv";
  private String DW = "./csv/dictionary.csv";
  private String CC = "./csv/corncob_caps.csv";
  private String WD = "./csv/words.csv";
  //percent of board blanks
  private static final int BLANKPERCENT = 16; //as guidline on wikipedia
  // Default values
  private static final char BLANK = '\u25A0';
  private static final char SPACE = '_';
  private static final int MAXWORDS = 78;
  private static final int MINWORDLEN = 3;
  private static final int MAXWORDLEN = 15;   // should be size
  //board vars
  private Tile[][] board;
  private int size;
  private Random generator;
  public List<WordRun> downWords = new ArrayList<WordRun>();
  public List<WordRun> acrossWords = new ArrayList<WordRun>();


// board constructor
  public Xboard(int size){
    this.size = size;
    this.board = new Tile[size][size];
  }


public void addBlanks(){
  //random blank generator
  this.generator = new Random();
  //container for blank chance
  int chance = 0;

  //make blanks
  for(int i = 0; i < this.size; i++){
    for(int j = 0; j+i< this.size; j++){
      chance = Math.abs(this.generator.nextInt()%100);
      if(chance <= BLANKPERCENT){
        // diagonalize blank spaces
        board[i][j+i] = new Tile(BLANK, 0, 0);
        board[j+i][i] = new Tile(BLANK, 0, 0);
      }
    }
  }
}

public void addNumbers(){
  //word numbering -- really ugly but it works
  int downTotal = 0;
  int acrossTotal = 0;
  int across = 0;
  int down = 0;
  boolean aflag = false;
  boolean dflag = false;
  //make word numberings if either to right/bottom of edge or BLANK
  for(int i = 0; i < this.size; i++){
    for(int j = 0; j< this.size; j++){
      if(board[i][j] == null){
        if(i == 0 || board[i -1][j].letter == BLANK) {
        //top is end or black square
        WordRun d = new WordRun(i,j);
        downTotal++;
        //save down coord
        this.downWords.add(d);
        dflag = true;//
        }
        if(j == 0 || board[i][j-1].letter == BLANK){
        //left is end or black square
          WordRun a = new WordRun(i,j);
          acrossTotal++;
          //save across coord
          this.acrossWords.add(a);
          aflag = true;
        }
        if(aflag) {
        	across = acrossTotal;//begin new across
        }
        else {
        	across = board[i][j-1].across;
        }
        if(dflag) {
        	down = downTotal;//begin new down
        }
        else {
        	down = board[i-1][j].down;
        }
        board[i][j] = new Tile(SPACE, down, across);
        //running length total
        this.acrossWords.get(across-1).len++;
        this.downWords.get(down-1).len++;
      }
      aflag = false;
      dflag = false;
    }
  }
}


// down and across get run coord, split word and place char
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
	    board[i][j].put(c);
	    j++;
	}
}
public void putDown(String word, int run) {
	WordRun coord = this.downWords.get(run-1);
	int i = coord.i;
	int j = coord.j;
	for (int index = 0; index < word.length(); index++){
		char c = word.charAt(index);
		board[i][j].put(c);
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
			//System.out.println(m.group());
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
    addBlanks();
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
    	  System.out.format("%c|", t.letter);
      }
      System.out.print("\n");
    }
  }



}


class Test{
	private static final int BOARDSIZE = 15;
  public static void main(String[] args){

    System.out.println("Down Left Character");
    Xboard test = new Xboard(BOARDSIZE);
    test.init();
    String randoma;
    String randomb;
    int wordmax = Math.max(test.acrossWords.size(), test.downWords.size());
    for(int i = 1; i <= wordmax;i++) {
    	test.showBoard();
    	if(i <= test.downWords.size()) {
    		randoma = test.findWord(i, 'd');
    		test.putDown(randoma, i);
    	}
    	if(i <= test.acrossWords.size()) {
    		randomb = test.findWord(i, 'a');
    		test.putAcross(randomb, i);
    	}
    	
    }
  }
}
