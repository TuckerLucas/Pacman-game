package Game;

import java.awt.Graphics;

public class FlashingGhost extends VulnerableGhost
{
	private static final long serialVersionUID = 1L;
	
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
		moveRandomly(this);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.flashingGhostSprites[Animation.frameIndexFlashingGhosts], x, y, width, height, null);
	}
}
