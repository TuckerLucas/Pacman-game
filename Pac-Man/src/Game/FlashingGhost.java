package Game;

import java.awt.Graphics;

public class FlashingGhost extends VulnerableGhost
{
	private static final long serialVersionUID = 1L;

	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.flashingGhostSprites.length;
	
	public FlashingGhost(Ghost ghost, int xx, int yy)
	{
		ghostID = ghost.ghostID;
		x = xx;
		y = yy;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public void tick()
	{
		portalEvents(this);
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
		g.drawImage(Animation.flashingGhostSprites[frameIndex], x, y, width, height, null);
	}
}
