import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeGame {
	public static void main (String[]arg) {
		int done=0;			
		long startTime = System.currentTimeMillis();
		int [][]MazeMain=Matrix();
		while (done==0) {
			StdDraw.enableDoubleBuffering();
			int [][]MazeMain2=MovingMario(MazeMain);
			printBoard(MazeMain2);
			lightSource(MazeMain2,startTime);
				if (StdDraw.isKeyPressed(81)) {
					done++;

				}
			StdDraw.show();
			StdDraw.pause(60);
			StdDraw.clear();
		}
	}
	
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
		StdDraw.setPenRadius(0.055);
		double timeDif=(System.currentTimeMillis()-startTime)/50000;
		double radius=0.4-timeDif;
		System.out.println(radius);
		while(radius<2) {
			StdDraw.circle(x/10.0+0.05, y/10.0+0.05, radius);
			radius=radius+0.05;
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

		// Randomly generate whether exit tile is along row or column (0 = row, 1 = column)
		if((int)(Math.random() * 2) == 0){

			// Randomly generate exit tile along top or bottom row
			maze[(int)(Math.random() * 2) * 19][(int)(Math.random() * 20)] = 4;

		}
		else{

			// Randomly generate exit tile along leftmost or rightmost column
			maze[(int)(Math.random() * 20)][(int)(Math.random() * 2) * 19] = 4;

		}

		// Declare boolean variable to run loop that generates the maze path
		boolean validMaze = false;

		// Loop to generate maze path
		do{

			// Something

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
