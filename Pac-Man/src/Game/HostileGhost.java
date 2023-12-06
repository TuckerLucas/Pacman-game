package Game;

import java.awt.Graphics;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	// initial info loading
	public HostileGhost(int ID, int xx, int yy, int cD, int nD)
	{
		ghostID = ID;
		x = xx;
		y = yy;
		currentDir = cD;
		nextDir = nD;
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public HostileGhost(Ghost ghost, int xx, int yy)
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
		g.drawImage(Animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
	}
}
