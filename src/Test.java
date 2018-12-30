class Test{
	private static final int BOARDSIZE = 15;
	public static void main(String[] args) {
		
    	Xboard test = new Xboard(BOARDSIZE);
    	test.init();
   	 test.randomFill();
   	 test.showBoard();
  }
}
