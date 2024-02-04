package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Ghost_Vulnerable extends Ghost
{
	private static final long serialVersionUID = 1L;

	public int frameIndex = 0;
	public double elapsedFrameTimeInSeconds = 0;		
	public double targetTimePerFrameInSeconds = 0.05;
	public int totalNumberOfFrames = gp.animation.vulnerableGhostSprites.length;
	
	public Ghost_Vulnerable(GamePanel gp, int i) 
	{
		super(gp, i);
	}

	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			moveRandomly(this);
		}
		
		manageVulnerableAnimationTiming();
	}
	
	public void manageVulnerableAnimationTiming()
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
		g.drawImage(gp.animation.vulnerableGhostSprites[frameIndex], x, y, width, height, null);
	}
}
