import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeGame {
	public static void main (String[]arg) {
		boolean alive=true;
		while (alive) {
			alive=oneLevel();
			
		}	
		
	}
	//This prints one level of the maze
	public static boolean oneLevel() {	
		boolean alive=false;
		int [][]MazeMain=Matrix();
		int[]exit=findExit(MazeMain);
		int ExitX=getX(exit);
		int ExitY=getY(exit);
		for (int count=0;count<226;count++) {
			StdDraw.enableDoubleBuffering();
			int [][]MazeMain2=MovingMario(MazeMain);
			printBoard(MazeMain2);
			lightSource(MazeMain2,count);//Count double is to see how many times the loop has run for the shrinking circle
			
			int []Character=findCharacter(MazeMain2);
			int PlayerX=getX(Character);
			int PlayerY=getY(Character);
			StdDraw.show();
			StdDraw.pause(50);
			StdDraw.clear();
			if (PlayerX==ExitX&&PlayerY==ExitY) {
				count=226;
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
		for (int count=0; count<10; count++) {
			for (int count2=0; count2<10; count2++) {
				if (Board[count][count2]==1) {
					StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Brick.png",0.1,0.1);
				}
				if (Board[count][count2]==0) {
					StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Path.png",0.1,0.1);
				}
				if (Board[count][count2]==2) {
					StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Mario.png",0.1,0.1);
				}
				if (Board[count][count2]==3) {
					StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Entrance.png",0.1,0.1);
				}
				if (Board[count][count2]==4) {
					StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Exit.png",0.1,0.1);
				}
			}
		}
	}

	public static void lightSource(int [][]Board, double startCount) {
		int[]position=findCharacter(Board);
		int x =getX(position);
		int y = getY(position);
		
		StdDraw.setPenRadius(0.055);//Sets radius of drawn circles
		double radiusDifference = startCount/600;
		double radius=0.4-radiusDifference;//Subtracts 0.4 from above to get radius value
		if (radius>0.0255)
		while(radius<2) {//Starts while loop, will go until the radius value is 2
			StdDraw.circle(x/10.0+0.05, y/10.0+0.05, radius);//Prints circle with radius value of 'radius'
			radius=radius+0.05;//Adds more to radius
			//Basically is printing a whole bunch of circles around player
		}
		else {
			Font font = new Font("Arial", Font.BOLD, 80);
			StdDraw.setFont(font);
			StdDraw.text(0.5, 0.5, "You Lose");
		}
		
	}

	// Method to randomly generate maze
	public static int[][] generateMaze(int[] playerPosition){

		// Declare array to store maze
		int[][] maze = new int[20][20];

		// Fill maze with null values
		for(int i = 0; i < 20; i++){

			for(int j = 0; j < 0; j++){

				maze[i][j] = 99;

			}
			
		}

		// Choose random starting position and store in coordinates array
		int[] currentCoordinates = new int[]{(int)Math.random() * 21, (int)Math.random() * 21};

		// Declare and initialze variable to track how many cells are filled
		int remaining = 400;
		
		// Loop to complete maze
		while(remaining > 0){

			// Choose a random tile adjacent to the current tile
			

		}

		// Return value of maze
		return maze;

	}

	public static int [][] Matrix () {//initial maze matrix
		int Maze[][]= {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,1,0,0,0,0,0,0,1},
			{3,2,0,0,1,1,1,1,0,1},
			{1,1,1,0,1,0,0,0,0,1},
			{1,0,1,0,1,1,1,1,0,1},
			{1,0,0,0,1,0,0,0,0,1},
			{1,1,1,1,1,0,1,1,1,1},
			{1,0,0,0,0,0,1,0,0,4},
			{1,0,1,1,1,1,1,0,1,1},
			{1,0,0,0,0,0,0,0,1,1},
		};
		return Maze;
	}

	//Method for controlling the player with the four arrow keys
	public static int[][] MovingMario(int [][]Board) {
		int[]position=findCharacter(Board);
		int x =getX(position);
		int y = getY(position);
			
				if(StdDraw.isKeyPressed(37)) {
				if(x>0) {
					if(Board[x-1][y]==0||Board[x-1][y]==4) {
						Board[x-1][y]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(38)) {
				if(y<9) {
					if(Board[x][y+1]==0||Board[x][y+1]==4) {
						Board[x][y+1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(40)) {
				if (y>0) {
					if(Board[x][y-1]==0||Board[x][y-1]==4) {
						Board[x][y-1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(39)) {
				if(x<9) {
					if(Board[x+1][y]==0||Board[x+1][y]==4) {
						Board[x+1][y]=2;
						Board[x][y]=0;
					}
				}
			}
			
		return Board;
	}
	//Finds the exit tile of the random maze and stores in a 1D array as [x,y]
	public static int[] findExit(int[][]Board) {
		int x=0;
		int y=0;
		for (int count=0; count<10; count++) {
			for (int count2=0; count2<10; count2++) {
				if (Board[count][count2]==4) {
					x=count;
					y=count2;
				}
			}
		}
		int []position= {x,y};
		return position;
	}
	//Finds the charcter within the 2D array, returns a 1D array with the x and y position of the chracter
	public static int[] findCharacter(int[][]Board) {
		int x=0;
		int y=0;
		for (int count=0; count<10; count++) {
			for (int count2=0; count2<10; count2++) {
				if (Board[count][count2]==2) {
					x=count;
					y=count2;
				}	
			}
		}
		int[]position = {x,y};
		return position;
	}
	//Returns the x value of the charcter from the 1D array of it's location
	public static int getX (int[]position) {
		int x=position[0];
		return x;
	}
	//Returns the y value of the charcter from the 1D array of it's location
	public static int getY (int[]position) {
		int y=position[1];
		return y;
	}
	
}