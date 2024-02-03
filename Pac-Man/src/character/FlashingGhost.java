package character;

import java.awt.Graphics;

import main.GamePanel;

public class FlashingGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private int frameIndex = 0;
	private double elapsedFrameTimeInSeconds = 0;
	private double targetTimePerFrameInSeconds = 0.05;
	private int totalNumberOfFrames = gp.animation.flashingGhostSprites.length;
	
	public FlashingGhost(GamePanel gp, int i) 
	{
		super(gp, i);
	}
	
	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			moveRandomly(this);
		}
		
		manageFlashingAnimationTiming();
	}
	
	public void manageFlashingAnimationTiming()
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
		g.drawImage(gp.animation.flashingGhostSprites[frameIndex], x, y, width, height, null);
	}
}
