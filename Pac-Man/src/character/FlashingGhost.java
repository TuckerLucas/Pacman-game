package character;

import java.awt.Graphics;

import main.GamePanel;

public class FlashingGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private int frameIndexFlashing = 0;
	private double elapsedFrameTimeInSecondsFlashing = 0;
	private double targetTimePerFrameInSecondsFlashing = 0.33;
	private int totalNumberOfFramesFlashing = gp.animation.flashingGhostSprites.length;
	
	public FlashingGhost(int ID, int cD, int x, int y, int movementStatus, GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, gp);
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
		elapsedFrameTimeInSecondsFlashing += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsFlashing >= targetTimePerFrameInSecondsFlashing)
		{
			elapsedFrameTimeInSecondsFlashing = 0;
			frameIndexFlashing++;
			
			if(frameIndexFlashing == totalNumberOfFramesFlashing)
			{
				frameIndexFlashing = 0;
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.flashingGhostSprites[frameIndexFlashing], x, y, width, height, null);
	}
}
