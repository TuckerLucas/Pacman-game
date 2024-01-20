package character;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public static int numberOfLives = 3;
	
	protected int intersectedGhost;
	
	public static int pacmanSpawnXCoordinate = 320;
	public static int pacmanSpawnYCoordinate = 512;
	
	public static int currentDir;
	public static int nextDir;
	
	public static int portalCrossingStatus = notCrossingPortal;
	
	public static double elapsedFrameTimeInSeconds = 0.0;	
	public static double targetTimePerFrameInSeconds = 0.1;
	
	public static int frameIndex = 0;
	
	public static Pacman pacman;
	
	public Pacman(GamePanel gp)
	{
		super(gp);
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	public void setPortalCrossingStatus(int portalStatus)
	{
		portalCrossingStatus = portalStatus;
	}
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public void tick() {};
	public void render(Graphics g) {};
}