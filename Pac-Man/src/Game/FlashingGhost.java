package Game;

import java.awt.Graphics;

public class FlashingGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.flashingGhostSprites.length;
	
	public FlashingGhost(int ID, int xx, int yy)
	{
		ghostID = ID;
		x = xx;
		y = yy;
		spawnGhost(x, y);
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	public static void manageAnimationTiming()
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
