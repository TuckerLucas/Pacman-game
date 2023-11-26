package Game;

import java.awt.Graphics;
import java.util.Random;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public int ghostID;
	private int portalCrossingStatus;
	private int movementType;
	public boolean isVulnerable;
	
	private int frameIndexVulnerable = 0;
	private double elapsedFrameTimeInSecondsVulnerable = 0;		
	private double targetTimePerFrameInSecondsVulnerable = 0.05;
	private int totalNumberOfFramesVulnerable = Texture.blueGhost.length;
	
	private int frameIndexFlashing = 0;
	private double elapsedFrameTimeInSecondsFlashing = 0;
	private double targetTimePerFrameInSecondsFlashing = 0.33;
	private int totalNumberOfFramesFlashing = Texture.flashGhost.length;
	
	public VulnerableGhost(int ID, int movementStatus, int portalStatus, boolean vulnerabilityStatus)
	{
		ghostID = ID;                  			
		movementType = movementStatus; 			
		isVulnerable = vulnerabilityStatus; 	
		portalCrossingStatus = portalStatus;
		
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(spawnBoxX, spawnBoxY);		
				break;
			case Character.crossingPortalFromLeftSide:
				spawnGhost(portalRightSideCrossingPointXCoordinate, portalYCoordinate);	
				currentDir = left;									
				break;
			case Character.crossingPortalFromRightSide:
				spawnGhost(portalLeftSideCrossingPointXCoordinate, portalYCoordinate);		
				currentDir = right;									
				break;
		}
		
		randomGen = new Random();
		generateNextDirection();
	}
	
	public void tick()
	{
		portalEvents(this);
		moveRandomly();
		manageVulnerableAnimationTiming();
		manageFlashingAnimationTiming();
	}
	
	private void manageVulnerableAnimationTiming()
	{
		elapsedFrameTimeInSecondsVulnerable += Game.secondsPerTick;
		
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
	
	private void manageFlashingAnimationTiming()
	{
		// Needs a mechanism change to make tentacles move at same speed
		// as when ghost is in other states. Currently does not in order
		// to show blue or white color for longer which makes the speed 
		// reduced.
		elapsedFrameTimeInSecondsFlashing += Game.secondsPerTick;
		
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
		if(isFlashing)
		{
			g.drawImage(Texture.flashGhost[frameIndexFlashing], x, y, width, height, null);
		}
		else if(!isFlashing)
		{
			g.drawImage(Texture.blueGhost[frameIndexVulnerable], x, y, width, height, null);
		}
	}
}
