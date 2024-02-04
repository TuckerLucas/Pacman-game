package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public String currentDir;
	public String nextDir;
	
	public int frameIndex = 0;
	public double elapsedFrameTimeInSeconds = 0.0;	
	public double targetTimePerFrameInSeconds = 0.1;
	
	public Pacman(GamePanel gp)
	{
		super(gp);
	}
	
	public String getCurrentDirection()
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