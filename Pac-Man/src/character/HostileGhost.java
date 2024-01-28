package character;

import java.awt.Graphics;

import Game.Animation;
import main.GamePanel;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public HostileGhost(int ID, int cD, int x, int y, int movementStatus, boolean vulnerabilityStatus, GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, vulnerabilityStatus, gp);
	}
	
	public void tick()
	{
		if(!isCrossingPortal(ghostID))
		{
			selectGhostMovementType();
		}
		
		manageAnimationTiming();
		manageVulnerableAnimationTiming();
		manageFlashingAnimationTiming();
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
	
	public void manageVulnerableAnimationTiming()
	{
		elapsedFrameTimeInSecondsVulnerable += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsVulnerable >= targetTimePerFrameInSecondsVulnerable)
		{
			elapsedFrameTimeInSecondsVulnerable = 0;
			frameIndexVulnerable++;
			
			if(frameIndexVulnerable == totalNumberOfFramesVulnerable)
			{
				frameIndexVulnerable = 0;
			}
		}
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
		if(isVulnerable == false)
		{
			g.drawImage(Animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
		}
		else if(isVulnerable == true)
		{
			if(isFlashing)
			{
				g.drawImage(Animation.flashingGhostSprites[frameIndexFlashing], x, y, width, height, null);
			}
			else if(!isFlashing)
			{
				g.drawImage(Animation.vulnerableGhostSprites[frameIndexVulnerable], x, y, width, height, null);
			}
		}
	}
}
