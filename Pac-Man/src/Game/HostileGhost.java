package Game;

import java.awt.Graphics; // remove when ghost abstract

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public HostileGhost()
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
			
			if(frameIndex >= Texture.pacmanLook[currentDir].length)
			{
				frameIndex = 0;
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Texture.ghostLook[ghostID][currentDir][frameIndex], x, y, width, height, null);
	}
}
