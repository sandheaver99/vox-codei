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
	private List<Node> grid = new ArrayList<Node>();
	private int width;
	private int height;
	private final int RANGE = 3; //bomb destruction range in node distance from bomb node
	private final int BOMB_DELAY = 2;
	private String message;
	 
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
        
        
        int index = 0; //index for the grid array
        for (int i = 0; i < height; i++)
        {
            String mapRow = in.next(); // one line of the firewall grid
            String[] rowCharacters = mapRow.split("");
            for (String s: rowCharacters)
            {
				grid.add(new Node(index, s));
				index++;
			}            
        }
        
        
	}
	
	private void play()
	{
        int bombDelay = 0;
        boolean readyToBomb = true;
        // game loop
        while (true)
        {
			if (bombDelay > 0)
			{
				readyToBomb = false;
			}
			
			else
			{
				readyToBomb = true;
			}
			
			printGrid(); //displays the updated grid
			buildHeatMap(); //builds and displays the heatMap
			
            int rounds = in.nextInt(); // number of rounds left before the end of the game
            int bombs = in.nextInt(); // number of bombs left
            
            if (readyToBomb)
            {
				//find the best placement from the heatMap
				message = setBestPlacement(); //this also sets the life of 'death nodes' to the bomb delay
				bombDelay = BOMB_DELAY;	
				
							
			}   
			
			else
			{
				message = "WAIT";
				if(bombDelay > 0)
				{
					bombDelay--;
				}
			}         

            System.out.println(message);
            
            //update the life of all nodes in the grid
            for (Node n: grid)
            {
				n.updateLife();
			}
			
			
        }
    }
    
    private void printGrid()
    {
        System.err.println("Current Grid: ");
        System.err.println();
        
        int index = 0;
        while (index < grid.size())
        {
			for (int i = 0; i < width; i++)
            {
			    System.err.print(grid.get(index));
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
        while (index < grid.size())
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
		heatMap.clear();
		for (int i = 0; i < grid.size(); i++)
		{
			int nodeCount = 0;
			
			if (grid.get(i).getType().equals("@") || grid.get(i).getType().equals("#"))
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
		
		while ((i >= 0 && i < (width * height)) && loop <= RANGE)
		{			
			//System.err.println("Checking location " + i);
			//if an indestructible node is encoutered, return the current count
			if (grid.get(i).getType().equals("#"))
			{
                System.err.println();
				return count;
			}
			
			else if (grid.get(i).getType().equals("@"))
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
	
	
	private String setBestPlacement()
	{
		//find the best placement from the heatMap
		int bestCount = 0;
		int bestIndex = 0;
		int index = 0;
		
		for (Integer i: heatMap)
		{
			if(i > bestCount)
			{
				bestCount = (int) i;
				bestIndex = index;
			}
			index++;
		}
		
		String s = convertIndexToString(bestIndex);				
		
		//need to set Life of nodes around bestIndex. 
		List<Node> deathNodes = getDeathNodes(bestIndex);  
		for (Node n: deathNodes)
		{
			n.setLife(BOMB_DELAY);
		}
		
		return s;
	}
	
	private String convertIndexToString(int index)
	{
		int x = index % width;
		int y = index / width;
		
		return x + " " + y;
	}
	
	private List<Node> getDeathNodes(int bestIndex)
	{
		System.err.println("Best index is " + bestIndex);
		
		List<Node> deathNodes = new ArrayList<Node>();
		int[] increments = {-1, +1, -width, width};		
		outerLoop:
		for (int inc: increments)
		{			
			int loop = 0;
			int i = inc;
			//lookup
			while (loop < RANGE)
			{
				//if out of grid range exit loop and move to the next increments (direction)
				if((bestIndex+i) < 0 || (bestIndex+i) >= (width*height))
				{
					System.err.println("Can't find index " + (bestIndex+i));
					continue outerLoop;
				}
				
				//if changing rows whilst moving left or right, exit loop and move to the next increments (direction)
				if((Math.abs(i) < width) && (((bestIndex+i) / width) != (bestIndex / width)))
				{
					System.err.println("Can't use index " + (bestIndex+i));
					continue outerLoop;
				}					
				
				Node lookedAt = grid.get(bestIndex+i);
				if (lookedAt.getType().equals("#"))
				{
					System.err.println("Ran into indestructible at " + (bestIndex+i));
					continue outerLoop;
				}
				else if (lookedAt.getType().equals("@"))
				{
					System.err.println("Found surveillance node at " + (bestIndex+i));
					deathNodes.add(lookedAt);
				}
				else
				{
					System.err.println("Looked at " + (bestIndex+i) + ", is empty");
				}
				i += inc;
				loop++;
			}
		}
				
		printDeathNodes(deathNodes);
		return deathNodes;
	}
	
	private void printDeathNodes(List<Node> deathNodes)
	{
		System.err.println();
		System.err.print("Death Nodes: ");
		
		for (Node n: deathNodes)
		{
			System.err.print(n.getId()+" , ");
		}
		System.err.println();
	}					 
			
}

class Node
{
	private int id;
	private String type;
	private int life;
	
	public Node (int id, String type)
	{
		this.id = id;
		this.type = type;
		this.life = Integer.MAX_VALUE;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public void updateLife()
	{
		this.life--;
		if (this.life == 0)
		{
			this.type = ".";
			this.life = Integer.MAX_VALUE;
		}
	}
	
	@Override
	public String toString()
	{
		return type;
	}
}

