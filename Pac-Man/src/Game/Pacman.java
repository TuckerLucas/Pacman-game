package Game;

import java.awt.Graphics;

public abstract class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public static int numberOfLives = 3;
	
	protected Ghost intersectedGhost;
	
	public static int pacmanSpawnXCoordinate = 320;
	public static int pacmanSpawnYCoordinate = 512;
	
	public static int currentDir;
	public static int nextDir;
	
	public static double elapsedFrameTimeInSeconds = 0.0;	
	public static double targetTimePerFrameInSeconds = 0.1;
	
	public static int frameIndex = 0;
	
	public static Pacman pacman;
	
	public Pacman()
	{

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
		return -1;
	}
	
	int getMovementType() 
	{
		return -1;
	}

	boolean getVulnerabilityStatus() 
	{
		return false;
	}
	
	abstract void tick();
	abstract void render(Graphics g);
}