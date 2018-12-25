//imports
import java.awt.Graphics;
import java.io.File;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;


class Xboard{
  //percent of board blanks
  private static final int BLANKPERCENT = 15;
  // Default values
  private static final char BLANK = 'X';
  private static final char SPACE = '_';
  private static final int MAXWORDS = 78;
  private static final int MINWORDLEN = 3;
  private static final int MAXWORDLEN = 15;   // should be size
  //board vars
  private Tile[][] board;
  private int size;
  private int numberOfWords = 0;
  private Random generator;
  private List<IntPair> downWords = new ArrayList<IntPair>();
  private List<IntPair> acrossWords = new ArrayList<IntPair>();


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
      if(chance < BLANKPERCENT){
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
        IntPair d = new IntPair(i,j);
        downTotal++;
        //save down coord
        this.downWords.add(d);
        dflag = true;//
        }
        if(j == 0 || board[i][j-1].letter == BLANK){
        //left is end or black square
          IntPair a = new IntPair(i,j);
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
      }
      aflag = false;
      dflag = false;
    }
  }
}

public void putAcross(String word, int run) {
	IntPair coord = this.acrossWords.get(run-1);
	int i = coord.x;
	int j = coord.y;
	for (int index = 0; index < word.length(); index++){
	    char c = word.charAt(index);
	    board[i][j].put(c);
	    j++;
	}
}

// test set up
  public void init(){
    addBlanks();
    addNumbers();
  }

// print board
  public void showBoard(){
    for(int i = 0; i < this.size; i++){
      System.out.print("|");
      for(int j = 0; j < this.size; j++){
    	  Tile t = board[i][j];
          System.out.format("%-2d%-2d%c|",t.down,t.across, t.letter);
      }
      System.out.print("\n");
    }
  }



}


class Test{
	private static final int BOARDSIZE = 15;
  public static void main(String[] args){
	  //from https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt
    List<String> dict = CSVReader.CSVList("./csv/words_alpha.csv");
    Xboard test = new Xboard(BOARDSIZE);
    test.init();
    test.putAcross("Kevin", 1);
    test.showBoard();
  }
}
