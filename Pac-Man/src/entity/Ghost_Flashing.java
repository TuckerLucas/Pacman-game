package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Ghost_Flashing extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	public Ghost_Flashing(GamePanel gp, int i) 
	{
		super(gp, i);
		
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;
		targetTimePerFrameInSeconds = 0.05;
		totalNumberOfFrames = gp.animation.flashingGhostSprites.length;
	}
	
	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			moveRandomly(this);
		}
		
		manageAnimationTiming(this);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.flashingGhostSprites[frameIndex], x, y, width, height, null);
	}
}
