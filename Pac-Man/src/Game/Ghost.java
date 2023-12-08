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
		Ghost.blinky = new VulnerableGhost(blinky, blinky.x, blinky.y);
		Ghost.inky = new VulnerableGhost(inky, inky.x, inky.y);
		Ghost.pinky = new VulnerableGhost(pinky, pinky.x, pinky.y);
		Ghost.clyde = new VulnerableGhost(clyde, clyde.x, clyde.y);
		
		numberOfEatenGhosts = 0;
	}
	
	public static void turnAllHostile()
	{
		Ghost.blinky = new HostileGhost(blinky, blinky.x, blinky.y);
		Ghost.inky = new HostileGhost(inky, inky.x, inky.y);
		Ghost.pinky = new HostileGhost(pinky, pinky.x, pinky.y);
		Ghost.clyde = new HostileGhost(clyde, clyde.x, clyde.y);
	}
	
	public static void startFlashing()
	{
		Ghost.blinky = new FlashingGhost(blinky, blinky.x, blinky.y);
		Ghost.inky = new FlashingGhost(inky, inky.x, inky.y);
		Ghost.pinky = new FlashingGhost(pinky, pinky.x, pinky.y);
		Ghost.clyde = new FlashingGhost(clyde, clyde.x, clyde.y);	
	}
	
	protected void moveRandomly()
	{	
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