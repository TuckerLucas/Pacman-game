package Game;

import java.awt.Graphics; // remove

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public VulnerableGhost()
	{
		
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	private void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(isFlashing)
			{
				if(frameIndex >= Texture.flashGhost.length)
				{
					frameIndex = 0;
				}
			}
			else if(!isFlashing)
			{
				if(frameIndex >= Texture.blueGhost.length)
				{
					frameIndex = 0;
				}
			}
		}
	}
	
	public static void startFlashing()
	{
		isFlashing = true;
	}
	
	public void render(Graphics g)
	{
		if(isFlashing)
		{
			g.drawImage(Texture.flashGhost[frameIndex], x, y, width, height, null);
		}
		else if(!isFlashing)
		{
			g.drawImage(Texture.blueGhost[frameIndex], x, y, width, height, null);
		}
	}
}
