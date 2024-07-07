package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public String currentDir;
	public String nextDir;
	
	public Pacman(GamePanel gp)
	{
		super(gp);
		
		name = "PACMAN";
		action = "PLAYER";
		frameIndex = 2;
		elapsedFrameTimeInSeconds = 0.0;	
		targetTimePerFrameInSeconds = 0.1;
		
		solidArea.x = x + 12;
		solidArea.y = y + 12;
		solidArea.width = 10;
		solidArea.height = 10;
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