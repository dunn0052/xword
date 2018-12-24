//imports
import java.awt.Graphics;
import java.io.File;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
//import java.util.Regex;


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
  private int[] numbers;
  //hold words
  private String[] down;
  private String[] across;


// individual tiles
  private class Tile{
    char letter;
    int down;
    int across;
    int xcoord;
    int ycoord;

    //constructor
    private Tile(char letter, int x, int y, int down, int across){
      this.letter = letter;
      this.across =  across;
      this.down = down;
      this.xcoord = x;
      this.ycoord = y;
    }

    //manually put in character
    public void put(char letter) {
      this.letter =  letter;
    }
  }

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
        board[i][j+i] = new Tile(BLANK, i, j+i, 0, 0);
        board[j+i][i] = new Tile(BLANK, j+i, i, 0, 0);
      }
    }
  }
}

public void addNumbers(){
  //word numbering
  int wordCount = 0;
  //make word numberings if either to right/bottom of edge or BLANK
  for(int i = 0; i < this.size; i++){
    for(int j = 0; j< this.size; j++){
      if(board[i][j] == null){
        if(i == 0 || board[i -1][j].letter == BLANK) {
          //down case
          wordCount++;
          board[i][j] = new Tile(SPACE, i, j, wordCount, 0);
        }
        else if(j == 0 || board[i][j-1].letter == BLANK){
          //across case
          wordCount++;
          board[i][j] = new Tile(SPACE, i, j, 0, wordCount);
        }
        else{
          //open space
          board[i][j] = new Tile(SPACE, i, j, 0, 0);
        }
      }
    }
  }
  this.numberOfWords = wordCount;
}

public void addWord(String[] words, int run, String word){
  words[run-1] = word;
}

// test set up
  public void init(){
    addBlanks();
    addNumbers();
  }

// print board
  public void showBoard(){
    for(int i = 0; i < this.size; i++){
      for(int j = 0; j < this.size; j++){
        if(board[i][j].across > 0){
          System.out.format("%-2dA", board[i][j].across);
        }
        else if(board[i][j].down > 0){
          System.out.format("%-2dD", board[i][j].down);
        }
        else{
          System.out.format("%-3c", board[i][j].letter);
        }
      }
      System.out.println("");
    }
  }



}


class Test{

  public static void main(String[] args){
	  //from https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt
    //List<String> dict = CSVReader.CSVList("./csv/words_alpha.csv");
    Xboard test = new Xboard(15);
    test.init();
    test.showBoard();
  }
}
