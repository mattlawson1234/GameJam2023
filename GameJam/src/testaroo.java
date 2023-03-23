import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class testaroo {
    
    public static void main(String[] args){

        // Declare array to store maze
        int[][] maze = new int[20][20];

        // Randomly generate a maze
        maze = generateMaze();

        // Print the board
        printBoard(maze);

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

            if(!searchSpaces(maze)){

                break;

            }

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

                // Print walls at the corners around the player start position
                maze[startIndexA + 1][startIndexB + 1] = 1;
                maze[startIndexA - 1][startIndexB + 1] = 1;
                maze[startIndexA + 1][startIndexB - 1] = 1;
                maze[startIndexA - 1][startIndexB - 1] = 1;
                remaining -= 4;

            }

            // Check if invalid walk coordinates were found
            if(walkCoordinates[0] == 0 && walkCoordinates[1] ==0){

                // Fill all remaining spaces with walls
                maze = fillSpaces(maze);
                break;

            }

            // Loop for the current randomized walk, will continue until 14 tiles have been placed
            while(pathPlaced < 14){

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

			// Declare array to store tile next to start coordinates
			int[] adjacentTile = new int[2];

			// Assign tile above
			adjacentTile[0] = walkCoordinates[0] - 1;
			adjacentTile[1] = walkCoordinates[1];

			// Check tile above
			if(maze[adjacentTile[0]][adjacentTile[1]] == 99){

				// Check for path cube
				if(findPathCube(maze, adjacentTile)){

					maze[adjacentTile[0]][adjacentTile[1]] = 1;

				}

			}

			// Assign tile below
			adjacentTile[0] = walkCoordinates[0] + 1;
			adjacentTile[1] = walkCoordinates[1];

			// Check tile below
			if(maze[adjacentTile[0]][adjacentTile[1]] == 99){

				// Check for path cube
				if(findPathCube(maze, adjacentTile)){

					maze[adjacentTile[0]][adjacentTile[1]] = 1;

				}

			}

			// Assign tile to the left
			adjacentTile[0] = walkCoordinates[0];
			adjacentTile[1] = walkCoordinates[1] - 1;

			// Check tile to the left
			if(maze[adjacentTile[0]][adjacentTile[1]] == 99){

				// Check for path cube
				if(findPathCube(maze, adjacentTile)){

					maze[adjacentTile[0]][adjacentTile[1]] = 1;

				}

			}

			// Assign tile to the right
			adjacentTile[0] = walkCoordinates[0];
			adjacentTile[1] = walkCoordinates[1] + 1;

			// Check tile to the right
			if(maze[adjacentTile[0]][adjacentTile[1]] == 99){

				// Check for path cube
				if(findPathCube(maze, adjacentTile)){

					maze[adjacentTile[0]][adjacentTile[1]] = 1;

				}

			}

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
        if((maze[currentTile[0] + 1][currentTile[1]] == 1 && maze[currentTile[0] - 1][currentTile[1]] == 1 &&
        maze[currentTile[0]][currentTile[1] + 1] == 1 && maze[currentTile[0]][currentTile[1] - 1] == 1) ||
        (maze[currentTile[0] + 1][currentTile[1]] == 0 && maze[currentTile[0] - 1][currentTile[1]] == 0 &&
        maze[currentTile[0]][currentTile[1] + 1] == 0 && maze[currentTile[0]][currentTile[1] - 1] == 0)){

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

    // Method to print the maze
    public static void printMaze(int[][] maze){

        // Print the maze
        for(int i = 0; i < 20; i++){

            for(int j = 0; j < 20; j++){

                System.out.print(maze[i][j] + "\t");

            }

            System.out.println();

        }

    }


    //Prints the board for the player
	public static void printBoard (int [][]Board) {//Prints the current board

		for (int count=0; count<20; count++) {

			for (int count2=0; count2<20; count2++) {

				if (Board[count][count2]==1) {

					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Brick.png",0.05,0.05);

				}
				if (Board[count][count2]==0) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Path.png",0.05,0.05);
				}
				if (Board[count][count2]==2) {
					StdDraw.picture(count/20.0+0.025, count2/20.0+0.025, "Mario.png",0.05,0.05);
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

}