package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Ghost_Vulnerable extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	public Ghost_Vulnerable(GamePanel gp, int i) 
	{
		super(gp, i);
		
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;		
		targetTimePerFrameInSeconds = 0.05;
		totalNumberOfFrames = gp.animation.vulnerableGhostSprites.length;
	}

	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			moveRandomly(this);
		}
		
		gp.animation.manageAnimationTiming(this);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.vulnerableGhostSprites[frameIndex], x, y, width, height, null);
	}
}
