public class Tile{
    char letter;
    int down;
    int across;
    int xcoord;
    int ycoord;

    //constructor
    Tile(char letter, int x, int y, int down, int across){
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