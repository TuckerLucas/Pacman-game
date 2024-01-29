package character;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public int intersectedGhost;
	
	public int currentDir;
	public int nextDir;
	
	public static double elapsedFrameTimeInSeconds = 0.0;	
	public static double targetTimePerFrameInSeconds = 0.1;
	
	public static int frameIndex = 0;
	
	public Pacman(GamePanel gp)
	{
		super(gp);
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void tick() 
	{
		
	}
	
	public void render(Graphics g) 
	{
		
	}
}