
public class MazeSolver {
	public static void main (String[]arg) {
		
		int [][] Board=Matrix();
		int []Start = findCharacter(Board);
		int StartX=getX(Start);
		int StartY=getY(Start);
		int []End = findExit(Board);
		int EndX=getX(End);
		int EndY=getY(End);
		int x=StartX;
		int y=StartY;
		int travel=0;
		int direction1=0;
		int count=0;
		
		System.out.println("" + Board[x][y] + x + y);
		
		if (direction1==0) {
			if (Board[x][y-1]==0) {
				Board[x][y]=1;				
				y=y-1;
				travel=0;

			}
			else {
				direction1++;
			}
		}
		if(direction1==1) {
			if (Board[x][y+1]==0) {
				Board[x][y]=1;				
				y=y+1;
				travel=1;

			}
			else {
				direction1++;
			}
		}
		if(direction1==2) {
			if (Board[x-1][y]==0) {
				Board[x][y]=1;				
				x=x-1;
				travel=2;

			}
			else {
				direction1++;
			}
		}	
		if(direction1==3) {
			if (Board[x+1][y]==0) {
				Board[x][y]=1;				
				x=x+1;
				travel=3;

			}

		}
		System.out.println("" + Board[x][y] + x + y);
		
		while (!(EndX==x&&EndY==y)&&count<1000000) {
			int direction=(int)(Math.random()*2);
			if(travel==0) {
				int countT0=0;
				if(direction==0) {
					if (Board[x][y-1]==0) {
						y=y-1;
						travel=0;
					}
					else {
						direction++;
						countT0++;
					}	
				}
				if(direction==1) {
					if (Board[x-1][y]==0) {
						x=x-1;
						travel=2;
					}
					else {
						direction++;
						countT0++;
					}
				}
				if(direction==2) {
					if (Board[x+1][y]==0) {
						x=x+1;
						travel=3;
					}
					else {
						direction=direction-2;
						countT0++;
					}
				}
				if(countT0>=3) {
					if (Board[x][y+1]==0) {
						Board[x][y]=1;						
						y=y+1;
						travel=1;

					}
				}
				
			}
			if(travel==1) {
				int countT1=0;
				if(direction==0) {
					if (Board[x][y+1]==0) {
						y=y+1;
						travel=1;
					}
					else {
						direction++;
						countT1++;
					}	
				}
				if(direction==1) {
					if (Board[x-1][y]==0) {
						x=x-1;
						travel=2;
					}
					else {
						direction++;
						countT1++;
					}
				}
				if(direction==2) {
					if (Board[x+1][y]==0) {
						x=x+1;
						travel=3;
					}
					else {
						direction=direction-2;
						countT1++;
					}
				}
				if(countT1>=3) {
					if (Board[x][y-1]==0) {
						Board[x][y]=1;						
						y=y-1;
						travel=0;

					}
				}
				
			}
			if(travel==2) {
				int countT2=0;
				if(direction==0) {
					if (Board[x][y-1]==0) {
						y=y-1;
						travel=0;
					}
					else {
						direction++;
						countT2++;
					}	
				}
				if(direction==1) {
					if (Board[x][y+1]==0) {
						y=y-1;
						travel=1;
					}
					else {
						direction++;
						countT2++;
					}
				}
				if(direction==2) {
					if (Board[x-1][y]==0) {
						x=x-1;
						travel=2;
					}
					else {
						direction=direction-2;
						countT2++;
					}
				}
				if(countT2>=3) {
					if (Board[x+1][y]==0) {
						Board[x][y]=1;						
						x=x+1;
						travel=3;

					}
				}
				
			}
			if(travel==3) {
				int countT3=0;
				if(direction==0) {
					if (Board[x][y-1]==0) {
						y=y-1;
						travel=0;
					}
					else {
						direction++;
						countT3++;
					}	
				}
				if(direction==1) {
					if (Board[x+1][y]==0) {
						x=x+1;
						travel=3;
					}
					else {
						direction++;
						countT3++;
					}
				}
				if(direction==2) {
					if (Board[x][y+1]==0) {
						y=y+1;
						travel=1;
					}
					else {
						direction=direction-2;
						countT3++;
					}
				}
				if(countT3>=3) {
					if (Board[x-1][y]==0) {
						Board[x][y]=1;						
						x=x+1;
						travel=2;

					}
				}
				
			}
		System.out.println("" + Board[x][y] + " " + x + " " + y);
		System.out.println();
		count++;

		}
		PrintMatrix(Board);
	System.out.println("Code Done");	
	}
	public static int [][] Matrix () {//initial maze matrix made for testing purpose while we developed the generateMaze method
		int Maze[][]= {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
			{1,0,1,0,1,1,0,1,0,1,0,1,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,0,0,1,0,1},
			{1,0,0,0,0,0,4,1,0,1,0,1,2,1,1,1,0,1,0,1},
			{1,0,1,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1},
			{1,0,1,0,1,1,1,1,1,0,0,1,0,1,0,1,0,1,0,1},
			{1,0,1,0,0,0,0,0,1,0,1,1,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,0,0,0,0,0,1,0,0,1,1,0,1},
			{1,0,1,0,0,0,1,0,1,0,1,1,1,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,0,1,1},
			{1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,1,0,1,0,1},
			{1,0,1,0,1,0,1,1,0,0,0,0,0,0,1,1,0,1,0,1},
			{1,0,1,0,1,0,1,1,0,1,1,1,1,0,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		return Maze;
	}
	public static int[] findCharacter(int[][]Board) {
		int x=0;
		int y=0;
		for (int count=0; count<20; count++) {
			for (int count2=0; count2<20; count2++) {
				if (Board[count][count2]==2) {
					x=count;
					y=count2;
				}	
			}
		}
		int[]position = {x,y};
		return position;
	}

	public static int getX (int[]position) {
		int x=position[0];
		return x;
	}
	public static int getY (int[]position) {
		int y=position[1];
		return y;
	}
	public static int[] findExit(int[][]Board) {
		int x=0;
		int y=0;
		for (int count=0; count<20; count++) {
			for (int count2=0; count2<20; count2++) {
				if (Board[count][count2]==4) {
					x=count;
					y=count2;
				}
			}
		}
		int []position= {x,y};
		return position;
	}
	public static void PrintMatrix (int[][]Board) {
		for(int count=0;count<20;count++) {
			for(int count1=0;count1<20;count1++) {
				System.out.print(" " + Board[count][count1]);
			}
			System.out.println();
		
		}
		System.out.println();
		System.out.println();
	}
}