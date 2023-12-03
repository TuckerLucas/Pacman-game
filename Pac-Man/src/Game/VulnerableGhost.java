package Game;

import java.awt.Graphics;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.vulnerableGhostSprites.length;
	
	public VulnerableGhost()
	{
		
	}
	
	public VulnerableGhost(Ghost ghost)
	{
		ghostID = ghost.ghostID;
		x = ghost.x;
		y = ghost.y;
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
		g.drawImage(Animation.vulnerableGhostSprites[frameIndex], x, y, width, height, null);
	}
}
