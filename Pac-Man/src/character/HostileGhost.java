package character;

import java.awt.Graphics;

import Game.Animation;
import main.GamePanel;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public int frameIndex = 0;
	public double elapsedFrameTimeInSeconds = 0;		
	public double targetTimePerFrameInSeconds = 0.05;
	public int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	public HostileGhost(int ID, int cD, int x, int y, int movementStatus, GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, gp);
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
		g.drawImage(Animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
	}
}
