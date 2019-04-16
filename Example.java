// Justin Bang
// All work below is not sharable without consent

/*
   0  1  2  3  4
0 [0][0][0][0][0]
1 [0][0][0][0][0]
2 [0][0][0][0][0]
3 [0][0][0][0][0]

Select Array[0][1] and its neighbors

   0  1  2  3  4
0 [1][1][1][0][0]
1 [1][1][1][0][0]
2 [0][0][0][0][0]
3 [0][0][0][0][0]

// 8 possible neighbors: (going clockwise starting at upper left)
// Upper left [rowTarget - 1][colTarget - 1]
// Above [rowTarget - 1][colTarget]
// Upper right [rowTarget - 1][colTarget + 1]
// Right [rowTarget][colTarget + 1]
// Bottom right [rowTarget + 1][colTarget + 1]
// Bottom [rowTarget + 1][colTarget]
// Bottom left [rowTarget + 1][colTarget - 1]
// Left [rowTarget][colTarget - 1]

*/

import java.util.*;

// Tile Class
class Tile
{
	int row;
	int col;

	ArrayList<Tile> neighbors;

	Tile(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
}

public class Example
{
	// Method returns an ArrayList holding all neighboring tiles
	public static ArrayList<Tile> loopOne(int [][] array, int rowTarget, int colTarget, int maxRowSize, int maxColSize)
	{
		ArrayList<Tile> neighbors = new ArrayList<Tile>();

		// We'll start with a row above, then the middle row, then a row below
		for (int i = -1; i <= 1; i++)
		{
			// Only care about rows that are within bounds
			if (rowTarget + i < maxRowSize && rowTarget + i >= 0)
			{
				// At our row we are checking, scan each column from left, to middle, to right
				// NOTE: This implementation will land on our starting tile
					// Ex: If we want [0][1]'s neighbors, this loop will eventually get [0][1] as a neighbor of
					// [0][1], this may be undesirable depending on your implementation, and if this is a problem
					// then a simple if-statement to continue with the loop and not add a new tile can fix it.
					// I've added it in for your convenience.
				for (int j = -1; j <= 1; j++)
				{
					if (colTarget + j < maxColSize && colTarget + j >= 0)
					{
						if (i == 0 && j == 0)
						{
							continue;
						}

						// For visual purposes only
						array[rowTarget + i][colTarget + j] = 1;

						// Create a new Tile with the index that we're currently at
						// NOTE: Chances are, this isn't what you're looking for. If you want to set
						// references to an already existing tile corresponding to a 2D array, simply use
						// array[][].tile or whatever, and use that as a reference.
							// An example of that fix is inside method loopTwo().
						Tile newTile = new Tile(rowTarget + i, colTarget + j);
						neighbors.add(newTile);
					}
				}					
			}
		}

		return neighbors;
	}

	// Method returns an ArrayList holding all neighboring tiles
	// This time, using an array of Tiles instead of arrays of Integers

	// NOTE: Please view loopOne() first before reading this method's code
	public static ArrayList<Tile> loopTwo(Tile [][] arrayTiles, int rowTarget, int colTarget, int maxRowSize, int maxColSize)
	{
		ArrayList<Tile> neighbors = new ArrayList<Tile>();

		// We'll start with a row above, then the middle row, then a row below
		for (int i = -1; i <= 1; i++)
		{
			// Only care about rows that are within bounds
			if (rowTarget + i < maxRowSize && rowTarget + i >= 0)
			{
				for (int j = -1; j <= 1; j++)
				{
					if (colTarget + j < maxColSize && colTarget + j >= 0)
					{
						if (i == 0 && j == 0)
						{
							continue;
						}

						Tile neighborTile = arrayTiles[rowTarget + i][colTarget + j];
						neighbors.add(neighborTile);
					}
				}					
			}
		}

		return neighbors;
	}

	// Example of what a brute force solution would look like (this is bad)
	public static void hardCode(int [][] array, int rowTarget, int colTarget, int maxRowSize, int maxColSize)
	{
		// Upper Left
		if (rowTarget - 1 < maxRowSize && rowTarget - 1 >= 0)
			if (colTarget - 1 < maxColSize && colTarget - 1 >= 0)
				array[rowTarget - 1][colTarget - 1] = 1;

		// Above
		if (rowTarget - 1 < maxRowSize && rowTarget - 1 >= 0)
			if (colTarget < maxColSize && colTarget >= 0)
				array[rowTarget - 1][colTarget] = 1;

		// Upper Right
		if (rowTarget - 1 < maxRowSize && rowTarget - 1 >= 0)
			if (colTarget + 1 < maxColSize && colTarget + 1 >= 0)
				array[rowTarget - 1][colTarget + 1] = 1;

		// Right
		if (rowTarget < maxRowSize && rowTarget >= 0)
			if (colTarget + 1 < maxColSize && colTarget + 1 >= 0)
				array[rowTarget][colTarget + 1] = 1;

		// Bottom Right
		if (rowTarget + 1 < maxRowSize && rowTarget + 1 >= 0)
			if (colTarget + 1 < maxColSize && colTarget + 1 >= 0)
				array[rowTarget + 1][colTarget + 1] = 1;

		// Bottom
		if (rowTarget + 1 < maxRowSize && rowTarget + 1 >= 0)
			if (colTarget < maxColSize && colTarget >= 0)
				array[rowTarget + 1][colTarget] = 1;

		// Bottom Left
		if (rowTarget + 1 < maxRowSize && rowTarget + 1 >= 0)
			if (colTarget - 1 < maxColSize && colTarget - 1 >= 0)
				array[rowTarget + 1][colTarget - 1] = 1;

		// Left
		if (rowTarget < maxRowSize && rowTarget >= 0)
			if (colTarget - 1 < maxColSize && colTarget - 1 >= 0)
				array[rowTarget][colTarget - 1] = 1;
	}

	// Print int array (you can ignore this)
	public static void printArray(int [][] array, int rowTarget, int colTarget)
	{
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[0].length; j++)
			{
				if (i == rowTarget && j == colTarget)
					System.out.print("X ");
				else
					System.out.print(array[i][j] + " ");
			}
			System.out.print("\n");
		}		
		System.out.print("\n");
	}

	// Print tile array (you can ignore this)
	public static void printArray(Tile [][] array, int rowTarget, int colTarget, Tile target)
	{
		HashSet<Integer> flagR = new HashSet<Integer>();
		HashSet<Integer> flagC = new HashSet<Integer>();

		for (Tile current : target.neighbors)
		{
			flagR.add(current.row);
			flagC.add(current.col);
		}

		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[0].length; j++)
			{
				if (i == rowTarget && j == colTarget)
					System.out.print("X ");

				else if (flagR.contains(i))
				{
					if (flagC.contains(j))
					{
						System.out.print("1 ");
					}
					else
						System.out.print("0 ");
				}

				else
					System.out.print("0 ");
			}
			System.out.print("\n");
		}		
		System.out.print("\n");
	}

	// Prints the arraylist of neighbors (you can ignore this)
	public static void printArrayList(Tile tile)
	{
		System.out.println("The ArrayList of Neighbors for Tile [" + tile.row + "][" + tile.col + "] holds:");
		for (Tile current : tile.neighbors)
		{
			System.out.print("[" + current.row + "][" + current.col + "] ");
		}
		System.out.print("\n");
	}

	public static void main(String [] args)
	{
		int [][] array = new int [4][5];

		// Uncomment the following lines to see how its like with a modified integer array
		/*
		int maxRowSize = array.length;
		int maxColSize = array[0].length;
		int rowTarget = 0;
		int colTarget = 1;

		System.out.println("Target: array[" + rowTarget + "][" + colTarget + "]");
		array[rowTarget][colTarget] = 1;

		// Print out original array
		System.out.println("Before: ");
		printArray(array, rowTarget, colTarget);

		// hardCode(array, rowTarget, colTarget, maxRowSize, maxColSize);

		Tile exampleTileOne = new Tile(rowTarget, colTarget);

		exampleTileOne.neighbors = loopOne(array, rowTarget, colTarget, maxRowSize, maxColSize);

		// Print out modified array showcasing the indexes reached:
		System.out.println("After:");
		printArray(array, rowTarget, colTarget);

		// Print out the Neighbor ArrayList
		printArrayList(exampleTileOne);
		*/

		// This is how it'd look like for a 2D array of tiles
		Tile [][] arrayTiles = new Tile [4][5];
		int maxRowSize = arrayTiles.length;
		int maxColSize = arrayTiles[0].length;

		// Populate our array of tiles with the correct coordinates
		for (int i = 0; i < maxRowSize; i++)
		{
			for (int j = 0; j < maxColSize; j++)
			{
				Tile newTile = new Tile(i, j);
				arrayTiles[i][j] = newTile;
			}
		}

		int rowTarget = 0;
		int colTarget = 1;

		Tile exampleTileTwo = arrayTiles[rowTarget][colTarget];

		System.out.println("Target: array[" + rowTarget + "][" + colTarget + "]");

		// Print out original array
		System.out.println("Before: ");
		printArray(array, rowTarget, colTarget);

		// hardCode(array, rowTarget, colTarget, maxRowSize, maxColSize);

		exampleTileTwo.neighbors = loopTwo(arrayTiles, rowTarget, colTarget, maxRowSize, maxColSize);

		// Print out modified array showcasing the indexes reached:
		System.out.println("After:");
		printArray(arrayTiles, rowTarget, colTarget, exampleTileTwo);

		// Print out the Neighbor ArrayList
		printArrayList(exampleTileTwo);	

		// Further Examples:
		// Set the neighbors of ALL TILES
		for (int i = 0; i < maxRowSize; i++)
		{
			for (int j = 0; j < maxColSize; j++)
			{
				Tile currentTile = arrayTiles[i][j];
				rowTarget = i;
				colTarget = j;
				currentTile.neighbors = loopTwo(arrayTiles, rowTarget, colTarget, maxRowSize, maxColSize);

				System.out.println("For Tile[" + currentTile.row + "][" + currentTile.col + "]");
				printArray(arrayTiles, rowTarget, colTarget, currentTile);
			}
		}

		
		for (int i = 0; i < maxRowSize; i++)
		{
			for (int j = 0; j < maxColSize; j++)
			{
				Tile currentTile = arrayTiles[i][j];
				printArrayList(currentTile);
				System.out.println();
			}
		}
	}
}
