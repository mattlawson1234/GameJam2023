public class testaroo {
    
    public static void main(String[] args){

        // Declare maze
        int[][] maze = new int[20][20];

        // Generate maze
        maze = generateMaze();

        System.out.println();

        // Print maze
        printMaze(maze);

    }

    // Method to randomly generate maze
	public static int[][] generateMaze(){

		// Declare array to store maze
		int[][] maze = new int[20][20];

		// Fill maze with null values and walls
		maze = fillMazeNull();

		// Declare and initialze variable to track how many cells are filled
		int remaining = 324;

		// Declare boolean variable to store if the randomly generated starting position is the first one
		boolean startPosition = false;
		
		// Loop to complete maze
		while(remaining > 0){

			// Declare counter to store the number of path tiles generated
			int pathTilesMade = 0;

			// Check if player starting position has been determined, and generate starting position accordingly
            int[] startingPosition = findStartingPosition(maze, startPosition);

            // Declare variables that store the current tile position
            int firstIndex = startingPosition[0], secondIndex = startingPosition[1];

            // Change startPosition if player starting position was found
            if(remaining == 324){

                startPosition = true;

            }

			// Check if the starting tile has been generated already
			if(!startPosition){

				maze[firstIndex][secondIndex] = 3;
				pathTilesMade++;
                remaining--;
				startPosition = true;

			}
			else{

				maze[firstIndex][secondIndex] = 0;
				pathTilesMade++;
                remaining--;

			}

			// Declare variables to store previous tile coordinates
			int lastFirstIndex = 99, lastSecondIndex = 99;

			// Loop to start randomized walk of 15 tiles, including the begininng tile
			while(pathTilesMade < 15){

				// Declare boolean variable to run loop that chooses a random tile
				boolean validTile = false;

				// Declare variables to temporarily store the randomly generated adjacent tile coordinates
				int tempFirstIndex = 0, tempSecondIndex = 0;

				// Choose a random tile adjacent to the current tile
				while(!validTile){

					// Create array to store adjacent tile coordinates
					int[] adjacentTile = findAdjacentTile(firstIndex, secondIndex);

					// Randomly generate coordinates of tile adjacent to current tile
					tempFirstIndex = adjacentTile[0];
					tempSecondIndex = adjacentTile[1];

					// Check if tile is valid
					validTile = checkAdjacentTile(tempFirstIndex, tempSecondIndex, lastFirstIndex, lastSecondIndex, maze);

				}

				// Once tile is determined to be valid, update the coordinate variables
				lastFirstIndex = firstIndex;
				lastSecondIndex = secondIndex;
				firstIndex = tempFirstIndex;
				secondIndex = tempSecondIndex;

				// Check if adjacent tile is a path tile
				if(maze[firstIndex][secondIndex] == 0){

					break;

				}
				else{

					// Place a path tile in the current coordinates
					maze[firstIndex][secondIndex] = 0;
					pathTilesMade++;
                    remaining--;

				}

			}

			System.out.println("Hello");
			// Print the maze
			for(int i = 0; i < 20; i++){

				for(int j = 0; j < 20; j++){
	
					System.out.print(maze[i][j] + "\t");
	
				}
	
				System.out.println();
	
			}

			// Generate walls around the final tile
			if(maze[firstIndex + 1][secondIndex] == 99){

				maze[firstIndex + 1][secondIndex] = 1;
                remaining--;

			}
			if(maze[firstIndex - 1][secondIndex] == 99){

				maze[firstIndex - 1][secondIndex] = 1;
                remaining--;

			}
			if(maze[firstIndex][secondIndex + 1] == 99){

				maze[firstIndex][secondIndex + 1] = 1;
                remaining--;

			}
			if(maze[firstIndex][secondIndex - 1] == 99){

				maze[firstIndex][secondIndex - 1] = 1;
                remaining--;

			}

		}

		// Return value of maze
		return maze;

	}

    // Method to fill maze with null values and walls
	public static int[][] fillMazeNull(){

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

	// Method to generate coordinates for adjacent tile
	public static int[] findAdjacentTile(int firstIndex, int secondIndex){

		// Declare variable to store coordinate of adjacent tile
		int[] coordinates = new int[2];

		// Generate random number to determine direction of coordinate
		int random = (int)(Math.random() * 4);

		// Check random number and assign direction accordingly (0: up, 1: right, 2: down, 3: left)
		if(random == 0){

			coordinates[0] = firstIndex + 1;
			coordinates[1] = secondIndex;

		}
		else if(random == 1){

			coordinates[0] = firstIndex;
			coordinates[1] = secondIndex + 1;

		}
		else if(random == 2){

			coordinates[0] = firstIndex - 1;
			coordinates[1] = secondIndex;

		}
		else {

			coordinates[0] = firstIndex;
			coordinates[1] = secondIndex - 1;

		}

		// Return value of coordinate
		return coordinates;

	}

    // Method to check the adjacent tile
	public static boolean checkAdjacentTile(int tempFirstIndex, int tempSecondIndex, int lastFirstIndex, int lastsecondIndex, int[][] maze){

		// Declare boolean variable to store if adjacent tile is valid
		boolean validTile = false;

		// Check if adjacent tile is within scope of array, and within boundaries
		if(tempFirstIndex >= 1 && tempFirstIndex <= 18 && tempSecondIndex >= 1 && tempSecondIndex <= 18){

			// Check if adjacent tile is not the previously travelled tile
			if(tempFirstIndex != lastFirstIndex && tempSecondIndex != lastsecondIndex){

				// Check if adjacent tile is not a wall
				if(maze[tempFirstIndex][tempSecondIndex] != 1){

					validTile = true;

				}

			}

		}

		// Return value of validTile
		return validTile;

	}

	// Method to choose starting position for walk
	public static int[] findStartingPosition(int[][] maze, boolean startPosition){

		// Declare variable to store starting coordinates
		int[] startingCoordinates = new int[2];

		// Declare boolean variable to store if starting coordinates have been found
		boolean foundStartingCoordinates = false;

        // Check if player starting position has already been found
        if(!startPosition){

            startingCoordinates[0] = 1 + (int)(Math.random() * 18);
            startingCoordinates[1] = 1 + (int)(Math.random() * 18);

        }
        else{

            // Search maze for path tile with free spaces next to it
            for(int i = 0; i < 20; i++){

                for(int j = 0; j < 20; j++){

                    // Check if current tile is a path tile
                    if(maze[i][j] == 0 && i != 0 && i != 19 && j != 0 && j != 19){
                        
                        // Check if there is at least one empty space around the path tile
                        if(maze[i + 1][j] == 99 || maze[i - 1][j] == 99 || maze[i][j + 1] == 99 || maze[i][j - 1] == 99){

                            startingCoordinates[0] = i;
                            startingCoordinates[1] = j;
                            foundStartingCoordinates = true;
                            break;

                        }

                    }

                }

                // Check if starting coordinates have been found
                if(foundStartingCoordinates){

                    break;

                }

            }

        }

        

		// Return the value of startingCoordinates
		return startingCoordinates;

	}

    // Method to print the maze
    public static void printMaze(int[][] maze){

        // Print the maze
        for(int i = 0; i < 20; i++){

            for(int j = 0; j < 20; j++){

                System.out.print(maze[i][j] + " ");

            }

            System.out.println();

        }

    }   

}
