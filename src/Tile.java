public class Tile{
    char letter;
    int down;
    int across;

    //constructor
    Tile(char letter, int down, int across){
      this.letter = letter;
      this.across =  across;
      this.down = down;
    }

    //manually put in character
    public void put(char letter) {
      this.letter =  letter;
    }
  }