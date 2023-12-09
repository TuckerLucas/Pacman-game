package Game;

import java.awt.Graphics;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	public VulnerableGhost()
	{
		
	}
	
	public VulnerableGhost(Ghost ghost, int xx, int yy)
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
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.vulnerableGhostSprites[Animation.frameIndex], x, y, width, height, null);
	}
}
