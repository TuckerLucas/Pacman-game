package Game;

import java.awt.Graphics;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	public HostileGhost(int ID, int xx, int yy, int cD, int nD, int portalStatus)
	{
		ghostID = ID;
		x = xx;
		y = yy;
		portalCrossingStatus = portalStatus;
		currentDir = cD;
		nextDir = nD;
		
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(x, y);		
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
	}
	
	public HostileGhost(Ghost ghost)
	{
		ghostID = ghost.ghostID;
		x = ghost.x;
		y = ghost.y;
		portalCrossingStatus = ghost.portalCrossingStatus;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(x, y);		
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
	}
	
	public HostileGhost(Ghost ghost, int xx, int yy)
	{
		ghostID = ghost.ghostID;
		x = xx;
		y = yy;
		portalCrossingStatus = ghost.portalCrossingStatus;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		
		
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(x, y);		
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
	}
	
	public void tick()
	{
		portalEvents(this);
		updateDistanceToPacman();
		if(portalCrossingStatus == notCrossingPortal)
		{
			selectGhostMovementType();
		}
		manageAnimationTiming();
	}
	
	private static void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
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
