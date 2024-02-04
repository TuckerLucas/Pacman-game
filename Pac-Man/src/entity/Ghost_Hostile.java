package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Ghost_Hostile extends Ghost
{
	private static final long serialVersionUID = 1L;

	public int frameIndex = 0;
	public double elapsedFrameTimeInSeconds = 0;		
	public double targetTimePerFrameInSeconds = 0.05;
	public int totalNumberOfFrames = gp.animation.hostileGhostSprites[0][0].length;
	
	public Ghost_Hostile(GamePanel gp, int i) 
	{
		super(gp, i);
	}
	
	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			if(movementType == randomMovement)
			{
				moveRandomly(this);
			}
			else if(movementType == methodicalMovement)
			{
				moveMethodically();
			}
		}
		
		manageAnimationTiming();
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			
			if(frameIndex == totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
	}
}
