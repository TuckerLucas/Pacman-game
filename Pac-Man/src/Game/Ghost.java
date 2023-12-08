package Game;

import java.awt.Graphics;
import java.util.Random;

public abstract class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	public static Random randomGen;
	
	public int ghostID;
	
	protected int nextDir = 0;
	public int currentDir = 0;
	
	public static int numberOfEatenGhosts = 0;
	
	public static Ghost ghostArray[] = new Ghost[4];
	
	public static Ghost blinky;
	public static Ghost inky;
	public static Ghost pinky;
	public static Ghost clyde;
	
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public static boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 256)) ? true : false;
	}
	
	private void generateNextDirection()
	{
		nextDir = randomGen.nextInt(4);
	}
	
	public static void turnAllVulnerable()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i] = new VulnerableGhost(ghostArray[i], ghostArray[i].x, ghostArray[i].y);
		}
		
		numberOfEatenGhosts = 0;
	}
	
	public static void turnAllHostile()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i] = new HostileGhost(ghostArray[i], ghostArray[i].x, ghostArray[i].y);
		}
	}
	
	public static void startFlashing()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			if(ghostArray[i] instanceof VulnerableGhost)
			{
				ghostArray[i] = new FlashingGhost(ghostArray[i], ghostArray[i].x, ghostArray[i].y);
			}
		}
	}
	
	protected void moveRandomly(Ghost ghost)
	{	
		if(ghost instanceof HostileGhost && HostileGhost.pacmanIsClose() && !isInSpawnBox(this))
		{
			HostileGhost.movementType = HostileGhost.movingMethodically;
		}
		
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
			
			move(this, nextDir);
		}
		else if(canMove(this, currentDir))
		{
			move(this, currentDir);
			
			return;
		}
		
		generateNextDirection();
	}

	boolean getVulnerabilityStatus() 
	{
		return false;
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public int getID()
	{
		return ghostID;
	}
	
	abstract void tick();
	abstract void render(Graphics g);
}