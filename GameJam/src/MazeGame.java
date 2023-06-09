import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeGame {
	public static void main (String[]arg) {
		boolean alive=true;
		int levelsCompleted=0;
		boolean playAgain=true;
		while(playAgain) {
			levelsCompleted=0;
			startScreen();
			StdDraw.show();
			StdDraw.pause(1000);
			while(alive) {
				if(StdDraw.isKeyPressed(38)||StdDraw.isKeyPressed(39)||StdDraw.isKeyPressed(40)||StdDraw.isKeyPressed(37)) {
					StdDraw.clear();
					while (alive) {
						alive=oneLevel(levelsCompleted);
						levelsCompleted++;
						
						
					}
				}
			}
			StdDraw.clear();
			endOfLevel(levelsCompleted-2);
			StdDraw.show();
			StdDraw.pause(100);
			boolean onEndGame=true;
			while(onEndGame) {
				if(StdDraw.isKeyPressed(38)||StdDraw.isKeyPressed(39)||StdDraw.isKeyPressed(40)||StdDraw.isKeyPressed(37)) {
					StdDraw.clear();
					StdDraw.pause(25);
					onEndGame=false;
					alive=true;
				}
				if(StdDraw.isKeyPressed(69)) {
					onEndGame=false;
					playAgain=false;
					StdDraw.clear();
				}
			}
		}
		StdDraw.filledSquare(0.5,0.5,1);
		StdDraw.show();
			
	}
	//This prints one level of the maze
	public static boolean oneLevel(int levelsDone) {	
		boolean alive=false;
		int [][]MazeMain=generateMaze();
		int[]exit=findExit(MazeMain);
		int ExitX=getX(exit);
		int ExitY=getY(exit);

		int []BoostXA= new int [5];
		int []BoostYA= new int [5];
		int []allItemsX = new int [5+levelsDone];
		int []allItemsY = new int [5+levelsDone];
		for (int i=0; i<(5-levelsDone);i++) {
			int []boost=item(MazeMain,allItemsX,allItemsY);
			int BoostX=getX(boost);
			int BoostY=getY(boost);
			BoostXA[i]=BoostX;
			BoostYA[i]=BoostY;
			allItemsX[i]=BoostX;
			allItemsY[i]=BoostY;
		}
		int []badXA= new int [levelsDone];
		int []badYA= new int [levelsDone];
		for (int i=0; i<levelsDone; i++){
			int []bad=item(MazeMain,allItemsX,allItemsY);
			int BadX=getX(bad);
			int BadY=getY(bad);
			badXA[i]=BadX;
			badYA[i]=BadY;
			if (5-levelsDone>0) {
				allItemsX[5-levelsDone+i]=BadX;
				allItemsY[5-levelsDone+i]=BadY;
			}
			else {
				allItemsX[i]=BadX;
				allItemsY[i]=BadY;
			}
		}
		boolean []boostAvailable= new boolean [5];
		boolean []badAvailable=new boolean [levelsDone];
		for (int count=0;count<674;count++) {
			StdDraw.enableDoubleBuffering();
			int [][]MazeMain2=MovingMario(MazeMain);
			printBoard(MazeMain2);
			
			int []Character=findCharacter(MazeMain2);
			int PlayerX=getX(Character);
			int PlayerY=getY(Character);
			for (int i=0; i<(5-levelsDone);i++){
				if (PlayerX==BoostXA[i]&&PlayerY==BoostYA[i]&&!boostAvailable[i]) {
					count=count-250;
					boostAvailable[i]=true;
				}
				if (!boostAvailable[i]) {
					StdDraw.picture(BoostXA[i]/20.0+0.025, BoostYA[i]/20.0+0.025,"battery.png",0.025,0.025);
				}
			}
			for (int i=0; i<levelsDone; i++) {
				if (PlayerX==badXA[i]&&PlayerY==badYA[i]&&!badAvailable[i]) {
					count=count+250;
					badAvailable[i]=true;
				}
				if (!badAvailable[i]) {
					StdDraw.picture(badXA[i]/20.0+0.025, badYA[i]/20.0+0.025,"dust.png",0.025,0.025);
				}
			}
			lightSource(MazeMain2,count);//Count double is to see how many times the loop has run for the shrinking circle
			StdDraw.show();
			StdDraw.pause(50);
			StdDraw.clear();

			if (PlayerX==ExitX&&PlayerY==ExitY) {
				count=1210;
				boolean onBreakScreen=true;
				betweenLevels(levelsDone);
				while(onBreakScreen) {			
					StdDraw.show();
					StdDraw.pause(100);
					if(StdDraw.isKeyPressed(38)||StdDraw.isKeyPressed(39)||StdDraw.isKeyPressed(40)||StdDraw.isKeyPressed(37)) {
						StdDraw.clear();
						StdDraw.pause(25);
						onBreakScreen=false;
					}
				}
				StdDraw.clear();
				alive=true;
			}
			
		}
		StdDraw.clear();
		return alive;
	}
	//Prints the board for the player
	public static void printBoard (int [][]Board) {//Prints the current board
		for (int count=0; count<20; count++) {
			for (int count2=0; count2<20; count2++) {
				if (Board[count][count2]==1) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "WallTexture.png",0.05,0.05);
				}
				if (Board[count][count2]==0) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Ground.png",0.05,0.05);
				}
				if (Board[count][count2]==2) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "playercharup.png",0.05,0.05);
				}
				if (Board[count][count2]==3) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Entrance.png",0.05,0.05);
				}
				if (Board[count][count2]==4) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Exit.png",0.05,0.05);
				}
			}
		}

	}

	public static void lightSource(int [][]Board, double startCount) {
		int[]position=findCharacter(Board);
		int x =getX(position);
		int y = getY(position);
	
		
		StdDraw.setPenRadius(0.055);//Sets radius of drawn circles
		double radiusDifference = startCount/3000;
		double radius=0.25-radiusDifference;//Subtracts 0.4 from above to get radius value
		if (radius>0.0255)
		while(radius<2) {//Starts while loop, will go until the radius value is 2
			StdDraw.circle(x/20.0+0.025, y/20.0+0.025, radius);//Prints circle with radius value of 'radius'
			radius=radius+0.05;//Adds more to radius
			//Basically is printing a whole bunch of circles around player
		}

	
		
	}

    // Method to generate a maze
    public static int[][] generateMaze(){

        // Declare array to store maze
        int[][] maze = new int[20][20];

        // Fill maze with empty spaces surrounded by walls
        maze = fillMaze();

        // Randomly determine the player's starting position and store in variables
        int startIndexA = findStartCoord(), startIndexB = findStartCoord();

        // Place the start tile at those coordinates
        maze[startIndexA][startIndexB] = 2;

        // Declare variables required for randomized walk loop
        int remaining = 323;        // Counter for the number of empty tiles (323 since the edge of the array takes up 76, and the starting tile takes up 1)
        boolean firstWalk = true;   // Variable to store if it is currently the first walk
        

        // Loop for the randomized walks, will continue until all tiles are filled
        while(remaining > 0){

            // Check if there are any remaining space tiles
            if(!searchSpaces(maze)){

                break;

            }

            // Check for any path cubes
            fillPathCubes(maze);

            // Declare counter for amount of path tiles placed during current walk
            int pathPlaced = 0;

            // Declare array to store the starting position of the walk and another to store the coordinates of a randomly selected adjacent tile
            int[] walkCoordinates = new int[2];
            int[] adjacentTile = new int[2];

            // Check if it is currently the first walk
            if(!firstWalk){

                // Determine the starting position of the next walk and store in an array
                walkCoordinates = findWalkCoord(maze);

            }
            else{

                // Store the starting player position as the start of the next walk
                walkCoordinates[0] = startIndexA;
                walkCoordinates[1] = startIndexB;
                firstWalk = false;

                // Place walls at the corners of the start tile
                maze[startIndexA + 1][startIndexB - 1] = 1;
                maze[startIndexA + 1][startIndexB + 1] = 1;
                maze[startIndexA - 1][startIndexB - 1] = 1;
                maze[startIndexA - 1][startIndexB + 1] = 1;

            }

            // Check if invalid walk coordinates were found
            if(walkCoordinates[0] == 0 && walkCoordinates[1] ==0){

                // Fill all remaining spaces with walls
                maze = fillSpaces(maze);
                break;

            }

            // Loop for the current randomized walk, will continue until 14 tiles have been placed
            while(pathPlaced < 14){

                // Check for any path cubes
                fillPathCubes(maze);

                // Declare array to store coordinates for checking surroundings
                int[] currentTile = new int[2];

                // Check if any tiles are surrounded by walls
                for(int i = 1; i < 19; i++){

                    for(int j = 1; j < 19; j++){

                        // Store coordinates in array
                        currentTile[0] = i;
                        currentTile[1] = j;

                        // Check if the tile is surrounded by walls, and fill the space if necessary with a wall
                        if(checkSurroundings(maze, currentTile)){

                            maze[currentTile[0]][currentTile[1]] = 1;

                        }

                    }

                }

                // Find a tile adjacent to the current tile on walk
                adjacentTile = findAdjacentTile(maze, walkCoordinates);

                // Check if the found adjacent tile was valid
                if(adjacentTile[0] == 0 && adjacentTile[1] == 0){

                    break;

                }
                else{

                    maze[adjacentTile[0]][adjacentTile[1]] = 0;
                    walkCoordinates[0] = adjacentTile[0];
                    walkCoordinates[1] = adjacentTile[1];
                    pathPlaced++;
                    remaining--;

                }

            }

            // Place walls around the final walk tile
            if(maze[walkCoordinates[0] + 1][walkCoordinates[1]] == 99){

                maze[walkCoordinates[0] + 1][walkCoordinates[1]] = 1;

            }
            if(maze[walkCoordinates[0] - 1][walkCoordinates[1]] == 99){

                maze[walkCoordinates[0] - 1][walkCoordinates[1]] = 1;

            }
            if(maze[walkCoordinates[0]][walkCoordinates[1] + 1] == 99){

                maze[walkCoordinates[0]][walkCoordinates[1] + 1] = 1;

            }
            if(maze[walkCoordinates[0]][walkCoordinates[1] - 1] == 99){

                maze[walkCoordinates[0]][walkCoordinates[1] - 1] = 1;

            }

        }

        // Generate the exit tile
        maze = generateExit(maze, startIndexA, startIndexB);

        // Return the value of maze
        return maze;

    }

    // Method to fill maze with spaces surrounded by walls
	public static int[][] fillMaze(){

		// Declare array to store null values
		int[][] nullMaze = new int[20][20];

		// Fill maze with 99
		for(int i = 0; i < 20; i++){

			for(int j = 0; j < 20; j++){

				nullMaze[i][j] = 99;

			}
			
		}

		// Fill maze edges with wall tiles
		for(int i = 0; i < 20; i++){

			// Fill top row of maze with wall tiles
			nullMaze[0][i] = 1;
			
			// Fill bottom row of maze with wall tiles
			nullMaze[19][i] = 1;

			// Fill leftmost column of maze with wall tiles
			nullMaze[i][0] = 1;

			// Fill rightmost column of maze with wall tiles
			nullMaze[i][19] = 1;
		
		}

		// Return value of nullMaze
		return nullMaze;

	}

    // Method to determine a coordinate for the starting position of the player
    public static int findStartCoord(){

        // Declare variable to store randomly generated starting position
        int coordinate;

        // Randomly generate starting coordinate that is not on the edge of the board
        coordinate = 1 + (int)(Math.random() * 18);

        // Return the value of coordinate
        return coordinate;

    }

    // Method to check maze for space tiles
    public static boolean searchSpaces(int[][] maze){

        // Declare boolean variable to store if spaces are found
        boolean foundSpaces = true;

        // Declare counter variable for number of spaces found
        int spaces = 0;

        // Check the maze for space tiles
        for(int i = 0; i < 20; i++){

            for(int j = 0; j < 20; j++){

                if(maze[i][j] == 99){

                    spaces++;

                }

            }

        }

        // Check if spaces were found
        if(spaces == 0){

            foundSpaces = false;

        }

        // Return the value of foundSpaces
        return foundSpaces;

    }

    // Method to fill path cubes
    public static void fillPathCubes(int[][] maze){

        // Find any path cubes in the maze and fill them
        for(int i = 1; i < 19; i++){

            for(int j = 1; j < 19; j++){

                // Store current tile in array
                int[] currentTile = {i, j};

                if(maze[i][j] == 99 && findPathCube(maze, currentTile)){

                    maze[i][j] = 1;

                }

            }

        }

    }

    // Method to determine the starting position of the next walk
    public static int[] findWalkCoord(int[][] maze){

        // Declare array to store the coordinates of the walk starting position
        int[] walkCoordinates = new int[2];

        // Declare boolean variable to store whether loop has found a valid starting tile
        boolean validTile = false;

        // Declare counter to check if loop is unable to find a valid walk tile
        int counter = 0;

        // Loop to find a starting tile for the walk
        while(!validTile){

            // Randomly select a path tile in the maze
            walkCoordinates = findPathTile(maze);

			// Check if spaces still exist around the selected tile
			if(findSpaces(maze, walkCoordinates)){

				validTile = true;

			}

            // Increment the counter if search is unsuccessful
            counter++;

            // Check if the counter is high
            if(counter > 500){

                walkCoordinates[0] = 0;
                walkCoordinates[1] = 0;
                break;

            }

        }

        // Return the value of walkCoordinates
        return walkCoordinates;

    }

    // Method to fill all remaining maze spaces with walls
    public static int[][] fillSpaces(int[][] maze){

        // Replace all space tiles with walls
        for(int i = 0; i < 20; i++){

            for(int j = 0; j < 20; j++){

                if(maze[i][j] == 99){

                    maze[i][j] = 1;

                }

            }

        }

        // Return the value of maze
        return maze;

    }

    // Method to randomly select a path tile
    public static int[] findPathTile(int[][] maze){

        // Declare array to store the randomly selected path tile
        int[] pathTile = new int[2];

        // Declare boolean variable to store whether loop has found a path tile
        boolean foundPath = false;

        // Loop to find path tile
        while(!foundPath){

            // Pick a random tile in the maze that isn't on the edge and store in the walkCoordinates array
            for(int i = 0; i < 2; i++){

                pathTile[i] = 1 + (int)(Math.random() * 18);

            }

            // Check if the randomly selected tile is a path tile
            if(maze[pathTile[0]][pathTile[1]] == 0){

                foundPath = true;

            }

        }

        // Return the value of pathTile
        return pathTile;

    }

    // Method to check if starting walk tile is surrounded by walls
    public static boolean checkSurroundings(int[][] maze, int[] currentTile){

        // Declare boolean variable to store if a surrounded tile is found
        boolean tileSurrounded = false;

        // Check around the selected tile to see if it is surrounded
        if(maze[currentTile[0] + 1][currentTile[1]] == 1 && maze[currentTile[0] - 1][currentTile[1]] == 1 &&
        maze[currentTile[0]][currentTile[1] + 1] == 1 && maze[currentTile[0]][currentTile[1] - 1] == 1){

            tileSurrounded = true;

        }

        // Return the value of tileSurrounded
        return tileSurrounded;

    }

    // Method to check if there are empty spaces around the randomly chosen path tile
    public static boolean findSpaces(int[][] maze, int[] walkCoordinates){

        // Declare variable to store if spaces are found around the path tile
        boolean validTile = false;

        // Check around tile for spaces
        if(maze[walkCoordinates[0] + 1][walkCoordinates[1]] == 99){

            validTile = true;

        }
        else if(maze[walkCoordinates[0] - 1][walkCoordinates[1]] == 99){

            validTile = true;

        }
        else if(maze[walkCoordinates[0]][walkCoordinates[1] + 1] == 99){

            validTile = true;

        }
        else if(maze[walkCoordinates[0]][walkCoordinates[1] - 1] == 99){

            validTile = true;

        }

        // Return the value of validTile
        return validTile;

    }

    // Method to find a valid adjacent tile
    public static int[] findAdjacentTile(int[][] maze, int[] walkCoordinates){

        // Declare array to store the coordinates of the adjacent tile
        int[] adjacentTile = new int[2];

        // Declare boolean variable to store if loop has found a valid adjacent tile
        boolean validTile = false;

        // Loop to find an empty tile adjacent to the current path tile
        while(!validTile){

            // Declare variable for direction and assign random value corresponding to one of the 4 directions
            int direction = (int)(Math.random() * 4);

            // Assign adjacent tile based on direction (0: up, 1: down, 2: left, 3: right)
            switch(direction){

                case(0):{

                    if(maze[walkCoordinates[0] - 1][walkCoordinates[1]] == 99 && walkCoordinates[0] > 1){

                        adjacentTile[0] = walkCoordinates[0] - 1;
                        adjacentTile[1] = walkCoordinates[1];
                        validTile = true;
                    }

                    break;

                }
                case(1):{

                    if(maze[walkCoordinates[0] + 1][walkCoordinates[1]] == 99 && walkCoordinates[0] < 18){

                        adjacentTile[0] = walkCoordinates[0] + 1;
                        adjacentTile[1] = walkCoordinates[1];
                        validTile = true;
                    }

                    break;

                }
                case(2):{

                    if(maze[walkCoordinates[0]][walkCoordinates[1] - 1] == 99 && walkCoordinates[1] > 1){

                        adjacentTile[0] = walkCoordinates[0];
                        adjacentTile[1] = walkCoordinates[1] - 1;
                        validTile = true;
                    }

                    break;

                }
                case(3):{

                    if(maze[walkCoordinates[0]][walkCoordinates[1] + 1] == 99 && walkCoordinates[1] < 18){

                        adjacentTile[0] = walkCoordinates[0];
                        adjacentTile[1] = walkCoordinates[1] + 1;
                        validTile = true;
                    }

                    break;

                }

            }

            // Check if path has hit a dead end
            if(findDeadEnd(maze, walkCoordinates)){

                break;

            }

            // Check if the tile is a path tile
            else if(maze[adjacentTile[0]][adjacentTile[1]] == 0){

                break;

            }

            // Check if the tile is empty
            else if(maze[adjacentTile[0]][adjacentTile[1]] == 99){

                // Check if there is a path cube
                if(findPathCube(maze, adjacentTile)){

                    maze[adjacentTile[0]][adjacentTile[1]] = 1;
                    validTile = false;

                }


            }

            // Check if the path is trying to wander into a wall
            else if(maze[adjacentTile[0]][adjacentTile[1]] == 1){

                validTile = false;

            }
            else{

                validTile = true;

            }

        }
        
        // Return the value of adjacentTile
        return adjacentTile;

    }

    // Method to generate the exit tile (must be at least 5 spaces away)
    public static int[][] generateExit(int[][] maze, int startIndexA, int startIndexB){

        // Declare an array to store the coordinates of the exit tile
        int[] exitTile = new int[2];

        // Declare boolean variable to store whether a valid exit tile has been found
        boolean validExit = true;

        do{

            // Generate a random path tile
		    exitTile = findPathTile(maze);

            // Assign the lateral and vertical distance from the start to variables
            int rowDistance = Math.abs(exitTile[0] - startIndexA);
            int colDistance = Math.abs(exitTile[1] - startIndexB);

            // Check the distance
            if(rowDistance < 8 || colDistance < 8){

                validExit = false;

            }
            else{

                validExit = true;

            }

        }while(!validExit);

        // Assign exit tile to maze
        maze[exitTile[0]][exitTile[1]] = 4;

        // Return the value of maze
        return maze;

    }

    // Method to check for dead ends
    public static boolean findDeadEnd(int[][] maze, int[] walkCoordinates){

        // Declare boolean variable to store if a dead end is found
        boolean deadEnd = true;

        // Search around current walk coordinate for free space
        if(findSpaces(maze, walkCoordinates)){

            deadEnd = false;

        }

        // Return the value of deadEnd
        return deadEnd;

    }

    // Method to determine if placing a path tile will create a path cube
    public static boolean findPathCube(int[][] maze, int[] adjacentTile){

        // Declare integer to store how many walls need to be placed if multiple path cubes are found
        boolean pathCube = false;

        // Declare array to store currently selected corner
        int[] corner = new int[2];

        // Assign top left corner coordinates to corner array
        corner[0] = adjacentTile[0] - 1;
        corner[1] = adjacentTile[1] - 1;

        // Check if the corner is a path tile
        if(maze[corner[0]][corner[1]] == 0){

            // Check if the corner has path tiles to the right and below
            if(maze[corner[0]][corner[1] + 1] == 0 && maze[corner[0] + 1][corner[1]] == 0){

                pathCube = true;
                
            }

        }

        // Assign top right corner coordinates to corner array
        corner[0] = adjacentTile[0] - 1;
        corner[1] = adjacentTile[1] + 1;

        // Check if the corner is a path tile
        if(maze[corner[0]][corner[1]] == 0){

            // Check if the corner has path tiles to the left and below
            if(maze[corner[0]][corner[1] - 1] == 0 && maze[corner[0] + 1][corner[1]] == 0){

                pathCube = true;
                
            }

        }

        // Assign bottom left corner coordinates to corner array
        corner[0] = adjacentTile[0] + 1;
        corner[1] = adjacentTile[1] - 1;

        // Check if the corner is a path tile
        if(maze[corner[0]][corner[1]] == 0){

            // Check if the corner has path tiles to the right and above
            if(maze[corner[0]][corner[1] + 1] == 0 && maze[corner[0] - 1][corner[1]] == 0){

                pathCube = true;
                
            }

        }

        // Assign bottom right corner coordinates to corner array
        corner[0] = adjacentTile[0] + 1;
        corner[1] = adjacentTile[1] + 1;

        // Check if the corner is a path tile
        if(maze[corner[0]][corner[1]] == 0){

            // Check if the corner has path tiles to the left and above
            if(maze[corner[0]][corner[1] - 1] == 0 && maze[corner[0] - 1][corner[1]] == 0){

                pathCube = true;
                
            }

        }

        // Return the value of pathCube
        return pathCube;

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

	//Method for controlling the player with the four arrow keys
	public static int[][] MovingMario(int [][]Board) {
		int[]position=findCharacter(Board);
		int x =getX(position);
		int y = getY(position);
			
			if(StdDraw.isKeyPressed(37)) {
				if(x>0) {
					if(Board[x-1][y]==0||Board[x-1][y]==4||Board[x-1][y]==2) {
						Board[x-1][y]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(38)) {
				if(y<19) {
					if(Board[x][y+1]==0||Board[x][y+1]==4||Board[x][y+1]==2) {
						Board[x][y+1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(40)) {
				if (y>0) {
					if(Board[x][y-1]==0||Board[x][y-1]==4||Board[x][y-1]==2) {
						Board[x][y-1]=2;
						Board[x][y]=0;
					}
				}
			}
			if (StdDraw.isKeyPressed(39)) {
				if(x<19) {
					if(Board[x+1][y]==0||Board[x+1][y]==4||Board[x+1][y]==2) {
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
	
    //Finds the charcter within the 2D array, returns a 1D array with the x and y position of the chracter
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
	
	public static void startScreen() {
		StdDraw.picture(0.5, 0.5, "Roomba.png",1,1);

	}
	
    public static int[] item(int[][]initialMaze, int []itemX, int []itemY) {
		boolean NoBoost=true;
		int x=0;
		int y=0;
		while (NoBoost) {
			x=(int)(Math.random()*19)+1;
			y=(int)(Math.random()*19)+1;
			boolean isPath=checkPath(initialMaze,x,y);
			if (isPath)
				for(int i=0; i<itemX.length;i++) {
					if (itemX[i]!=x||itemY[i]!=y) {
						NoBoost=false;
					}
				}
		}
		int[]boostSpot= {x,y};
		return boostSpot;
	}
	
    public static boolean checkPath(int [][]Board, int x, int y) {
		boolean isPath=false;
		if (Board[x][y]==0) {
			isPath=true;
		}
		return isPath;
	}
    public static void betweenLevels (int levelsDone) {
		String levels = String.valueOf(levelsDone+1);
		String dust = "The next level has " + levels + " dust piles";
		String complete = "You completed level " + levels;
    	StdDraw.picture(0.5, 0.5, "leveladv.png",1,1);
		Font font = new Font("Arial", Font.BOLD, 40);
		StdDraw.setFont(font);
		StdDraw.text(0.5, 0.9, complete);
		Font font1 = new Font("Arial", Font.BOLD, 20);
		StdDraw.setFont(font1);
		if (5-levelsDone>0) {
			String levelsMinusFive= String.valueOf(4-levelsDone);
			String light = "The next level has " + levelsMinusFive + " batteries";
			StdDraw.text(0.5, 0.8, light);
		}
		else {
			StdDraw.text(0.5, 0.8, "The next level has 0 batteries");
		}
		StdDraw.text(0.5, 0.75, dust);
		StdDraw.text(0.5, 0.7, "Press the arrow key to start the next level");
    }
    public static void endOfLevel (int levelsDone) {
    	StdDraw.clear();
    	StdDraw.picture(0.5, 0.5, "gameoverscr.png",1,1);
		String levels = String.valueOf(levelsDone+1);
		String complete = "You completed " + levels + " levels";
		Font font = new Font("Arial", Font.BOLD, 40);
		
		StdDraw.setFont(font);
		StdDraw.text(0.5, 0.9, complete);
		Font font1 = new Font("Arial", Font.BOLD, 20);
		StdDraw.setFont(font1);
		StdDraw.text(0.5, 0.8, "To return to main menu press any arrow key");
		StdDraw.text(0.5, 0.75, "To exit the game press 'e'");
    }
 
}