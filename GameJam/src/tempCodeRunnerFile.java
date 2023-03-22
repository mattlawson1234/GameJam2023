		lightSource(MazeMain2,count);//Count double is to see how many times the loop has run for the shrinking circle
			StdDraw.show();
			StdDraw.pause(50);
			StdDraw.clear();

			if (PlayerX==ExitX&&PlayerY==ExitY) {
				count=1210;
				printBoard(MazeMain2);
				Font font = new Font("Arial", Font.BOLD, 40);
				StdDraw.setFont(font);
				StdDraw.text(0.5, 0.5, "You Beat This Level");
				StdDraw.show();
				StdDraw.pause(5000);
				alive=true;
			}
		}
		return alive;
	}
	//Prints the board for the player
	public static void printBoard (int [][]Board) {//Prints the current board
		for (int count=0; count<20; count++) {
			for (int count2=0; count2<20; count2++) {
		