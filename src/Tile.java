public class Tile{
    char letter;
    int down;
    int across;
    int deep;

    //constructor
    Tile(char letter, int down, int across, int deep){
      this.letter = letter;
      this.across =  across;
      this.down = down;
      this.deep = deep;
    }

    //manually put in character
    public void put(char letter) {
      this.letter =  letter;
    }
  }
