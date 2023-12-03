package Game;

import java.awt.Graphics;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	private static int totalNumberOfFrames = Animation.vulnerableGhostSprites.length;
	
	public VulnerableGhost(Ghost ghost)
	{
		ghostID = ghost.ghostID;
		x = ghost.x;
		y = ghost.y;
		spawnGhost(x, y);
	}
	
	public void tick()
	{
		manageAnimationTiming(totalNumberOfFrames);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.vulnerableGhostSprites[frameIndex], x, y, width, height, null);
	}
}
