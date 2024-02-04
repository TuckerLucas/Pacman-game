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
			if(movementType == "random")
			{
				moveRandomly(this);
			}
			else if(movementType == "methodical")
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
		switch(currentDir)
		{
			case "right": 
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][0][frameIndex], x, y, width, height, null);
				break;
			case "left":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][1][frameIndex], x, y, width, height, null);
				break;
			case "up":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][2][frameIndex], x, y, width, height, null);
				break;
			case "down":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][3][frameIndex], x, y, width, height, null);
				break;
		}
	}
}
