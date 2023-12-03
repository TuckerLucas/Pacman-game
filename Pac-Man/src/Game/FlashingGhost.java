package Game;

import java.awt.Graphics;

public class FlashingGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private static int totalNumberOfFrames = Animation.flashingGhostSprites.length;
	
	public FlashingGhost(Ghost ghost)
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
		manageAnimationTiming(totalNumberOfFrames);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.flashingGhostSprites[frameIndex], x, y, width, height, null);
	}
}
