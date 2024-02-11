package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Entity extends Rectangle
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
	
	public int frameIndex;
	public double elapsedFrameTimeInSeconds;
	public double targetTimePerFrameInSeconds;
	public int totalNumberOfFrames;
	
	public Entity(GamePanel gp)
	{
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
}