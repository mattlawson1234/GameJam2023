import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeGame {
	public static void main (String[]arg) {
		int done=0;
		StdDraw.line(0,0,1,1);
		int [][]MazeMain=Matrix();
		while (done==0) {
			StdDraw.enableDoubleBuffering();
			int [][]MazeMain2=MovingMario(MazeMain);
			printBoard(MazeMain2);
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
			}
		}
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
