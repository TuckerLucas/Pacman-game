package Game;

import java.awt.Graphics;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	public HostileGhost(int ID, int xx, int yy, int cD, int nD)
	{
		ghostID = ID;
		x = xx;
		y = yy;
		currentDir = cD;
		nextDir = nD;
		spawnGhost(x, y);
	}
	
	public HostileGhost(Ghost ghost)
	{
		ghostID = ghost.ghostID;
		x = ghost.x;
		y = ghost.y;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		spawnGhost(x, y);
	}
	
	public HostileGhost(Ghost ghost, int xx, int yy)
	{
		ghostID = ghost.ghostID;
		x = xx;
		y = yy;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		spawnGhost(x, y);
	}
	
	public void tick()
	{
		moveRandomly();
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
