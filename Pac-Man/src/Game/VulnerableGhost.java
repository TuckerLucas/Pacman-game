package Game;

import java.awt.Graphics; // remove

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public VulnerableGhost()
	{
		spawnGhost(spawnBoxX, spawnBoxY);
	}
	
	public static void startFlashing()
	{
		isFlashing = true;
	}
	
	void tick()
	{
		if(!VulnerableGhost.isFlashing)
		{
			manageVulnerableGhostAnimationTiming();
		}
		else if(VulnerableGhost.isFlashing)
		{
			manageFlashingGhostAnimationTiming();
		}
	}
	
	void render(Graphics g)
	{
		if(VulnerableGhost.isFlashing)
		{
			g.drawImage(Texture.flashGhost[frameIndexFlash], x, y, width, height, null);
		}
		else if(!VulnerableGhost.isFlashing)
		{
			g.drawImage(Texture.blueGhost[frameIndexBlue], x, y, width, height, null);
		}
	}
}
