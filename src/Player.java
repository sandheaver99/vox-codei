import java.util.*;
import java.io.*;
import java.math.*;

class Player
{
	/*
	 * heatMap holds the number of vulnerable surveillance nodes that lie in a 7 X 7 
	 * cross centred on the indexed node
	 */
	private List<Integer> heatMap = new ArrayList<Integer>();
	private String[] grid;
	private int width;
	private int height;
	private final int RANGE = 4;
	 
	Scanner in = new Scanner(System.in);

    public static void main(String args[])
    {
        new Player().go();
	}
	
	private void go()
	{
		initialise();
		play();
	}
	
	private void initialise()
	{        
        width = in.nextInt(); // width of the firewall grid
        height = in.nextInt(); // height of the firewall grid
        
        grid = new String[width * height]; // array to hold grid character (String) values
        int index = 0; //index for the grid array
        for (int i = 0; i < height; i++)
        {
            String mapRow = in.next(); // one line of the firewall grid
            String[] rowCharacters = mapRow.split("");
            for (String s: rowCharacters)
            {
				grid[index] = s;
				index++;
			}            
        }
        
        printGrid();
	}
	
	private void play()
	{
        // game loop
        while (true)
        {
			buildHeatMap();
			
            int rounds = in.nextInt(); // number of rounds left before the end of the game
            int bombs = in.nextInt(); // number of bombs left

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println("3 0");
        }
    }
    
    private void printGrid()
    {
        System.err.println("Current Grid: ");
        System.err.println();
        
        int index = 0;
        while (index < grid.length)
        {
			for (int i = 0; i < width; i++)
            {
			    System.err.print(grid[index]);
			    index++;
		    }
		    System.err.println();
		}
		System.err.println();
	}
	
	private void printHeatMap()
	{
		System.err.println("Current HeatMap: ");
        System.err.println();
		
		int index = 0;
        while (index < grid.length)
        {
			for (int i = 0; i < width; i++)
            {
			    System.err.print(heatMap.get(index));
			    index++;
		    }
		    System.err.println();
		}
		System.err.println();
	}
	
	private void buildHeatMap()
	{
		for (int i = 0; i < grid.length; i++)
		{
			int nodeCount = 0;
			
			if (grid[i].equals("@"))
			{
				nodeCount = 0;
			}
			
			else
			{			
			    nodeCount += crossSearchGrid(i, -width); //look up
			    nodeCount += crossSearchGrid(i, width); //look down
			    nodeCount += crossSearchGrid(i, -1); //look left
			    nodeCount += crossSearchGrid(i, 1); //look right
			}
			
			heatMap.add(nodeCount);		
		}
		printHeatMap();		
	}
	
	private int crossSearchGrid(int i, int increment)
	{
		//System.err.println("At location " + i);
		//System.err.println();
		
		int count = 0;
		//if looking left or right and changing rows, return the current count
		if((Math.abs(increment) == 1) && (i + increment) / width != i / width)
		{
			return count;
		}
		
		i += increment;	
			
		int loop = 1;
		
		while ((i >= 0 && i < (width * height)) && loop < RANGE)
		{			
			//System.err.println("Checking location " + i);
			//if an indestructible node is encoutered, return the current count
			if (grid[i].equals("#"))
			{
                System.err.println();
				return count;
			}
			
			else if (grid[i].equals("@"))
			{
				count++;
			}
			//if looking left or right and changing rows, return the current count
			if((Math.abs(increment) == 1) && (i + increment) / width != i / width)
			{
				return count;
			}
			
			i += increment;
			loop++;
		}
		//System.err.println();
		
		return count;
	}
	
	private int lookDown(int i)
	{
		return i;
	}
	
	private int lookLeft(int i)
	{
		return i;
	}
	
	private int lookRight(int i)
	{
		return i;
	}

	
			 
			
}
