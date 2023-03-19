import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeGame {
	public static void main (String[]arg) {
		oneLevel();
	}
	//This prints one level of the maze
	public static void oneLevel() {
		int done=0;			
		int count=0;
		int [][]MazeMain=Matrix();
		while (done==0&&count<226) {
			StdDraw.enableDoubleBuffering();
			int [][]MazeMain2=MovingMario(MazeMain);
			printBoard(MazeMain2);
			lightSource(MazeMain2,count);//Count double is to see how many times the loop has run for the shrinking circle
				if (StdDraw.isKeyPressed(81)) {
					done++;
				}
			count++;
			StdDraw.show();
			StdDraw.pause(50);
			StdDraw.clear();
		}
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
				//if (Board[count][count2]==3) {
				//	StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Entrance.png",0.1,0.1);
				//}
				//if (Board[count][count2==4]) {
				//	StdDraw.picture(count/10.0+0.05, count2/10.0+0.05, "Exit.png",0.1,0.1);
				//}
			}
		}
	}

	public static void lightSource(int [][]Board, long startTime) {
		//Finds location of player
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
		StdDraw.setPenRadius(0.055);//Sets radius of drawn circles
		double timeDif=(System.currentTimeMillis()-startTime)/50000;//Takes time difference between when started running and current time, divide by 50000 to make a small enough number
		double radius=0.4-timeDif;//Subtracts 0.4 from above to get radius value
		while(radius<2) {//Starts while loop, will go until the radius value is 2
			StdDraw.circle(x/10.0+0.05, y/10.0+0.05, radius);//Prints circle with radius value of 'radius'
			radius=radius+0.05;//Adds more to radius
			//Basically is printing a whole bunch of circles around player
		}
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

		// Randomly generate start tile in the middle of the maze
		maze[(9 + (int)(Math.random() * 2))][(9 + (int)(Math.random() * 2))] = 2;

		// Generate walls along borders of the maze
		for(int i = 0; i < 20; i++){

			// Generate walls along top of the maze
			maze[0][i] = 1;

			// Generate walls along bottom of the maze
			maze[19][i] = 1;

			// Generate walls along left side of the maze
			maze[i][0] = 1;

			// Generate walls along right side of the maze
			maze[i][19] = 1;

		}

		// Declare variables to store coordinates of randomly generated end tile
		int firstCoord = (int)(Math.random() * 2) * 19;			// Coordinate to choose top/bottom row or leftmost/rightmost column
		int secondCoord = 1 + (int)(Math.random() * 18);		// Coordinate to choose tile with row/column, excluding corner tiles

		// Randomly generate whether exit tile is along row or column (0 = row, 1 = column)
		if((int)(Math.random() * 2) == 0){

			// Randomly generate exit tile along top or bottom row
			maze[firstCoord][secondCoord] = 4;

			// Place path tile adjacent to the exit tile
			if(firstCoord == 0){

				maze[firstCoord + 1][secondCoord] = 0;

			}
			else{

				maze[firstCoord - 1][secondCoord] = 0;

			}

		}
		else{

			// Randomly generate exit tile along leftmost or rightmost column
			maze[secondCoord][firstCoord] = 4;

			if(firstCoord == 0){

				maze[secondCoord][firstCoord + 1] = 0;

			}
			else{

				maze[secondCoord][firstCoord - 1] = 0;

			}

		}

		// Declare boolean variable to run loop that generates the maze path
		boolean validMaze = false;

		// Loop to generate maze path
		do{

			

		}while(!validMaze);
		

		// Return the generated maze
		return maze;

	}

	public static int [][] Matrix () {//initial maze matrix
		int Maze[][]= {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,1,0,0,0,0,0,0,1},
			{2,0,0,0,1,1,1,1,0,1},
			{1,1,1,0,1,0,0,0,0,1},
			{1,0,1,0,1,1,1,1,0,1},
			{1,0,0,0,1,0,0,0,0,1},
			{1,1,1,1,1,0,1,1,1,1},
			{1,0,0,0,0,0,1,0,0,0},
			{1,0,1,1,1,1,1,0,1,1},
			{1,0,0,0,0,0,0,0,1,1},
		};
		return Maze;
	}

	//Method for controlling the player with the four arrow keys
	public static int[][] MovingMario(int [][]Board) {
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
			
			if(StdDraw.isKeyPressed(37)) {
				if(x>0) {
					if(Board[x-1][y]==0) {
						Board[x-1][y]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(38)) {
				if(y<9) {
					if(Board[x][y+1]==0) {
						Board[x][y+1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(40)) {
				if (y>0) {
					if(Board[x][y-1]==0) {
						Board[x][y-1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(39)) {
				if(x<9) {
					if(Board[x+1][y]==0) {
						Board[x+1][y]=2;
						Board[x][y]=0;
					}
				}
			}
			
		return Board;

	}


}