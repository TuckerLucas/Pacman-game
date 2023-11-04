package Game;

import java.awt.Graphics;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	private static int nextDir = 0;
	private static int currentDir = 0;
	
	private int imageIndexEnemy = 0;
	
	private int timeImage = 0;		
	private int targetTimeImage = 4;
	
	VulnerableGhost(int id, int movement, int portalStatus)
	{
		
	}
	
	private void flashGhost(Graphics g)
	{
		g.drawImage(Texture.flashGhost[Texture.flashAnimationPhase], x, y, width, height, null);
	}
	
	private void stayBlue(Graphics g)
	{
		g.drawImage(Texture.blueGhost[imageIndexEnemy], x, y, width, height, null);
	}
	
	private void animation()
	{
		timeImage ++;
		
		if(timeImage == targetTimeImage)
		{
			timeImage = 0;
			imageIndexEnemy ++;
		}
		
		flashAnimationTime++;
		
		if(flashAnimationTime == flashAnimationTargetTime)
		{
			flashAnimationTime = 0;
			
			if(Texture.flashAnimationPhase == 3)
			{
				Texture.flashAnimationPhase = 0;
			}
			else
			{
				Texture.flashAnimationPhase++;
			}
		}
	}
	public void render(Graphics g)
	{
		if(imageIndexEnemy == 2)
		{
			imageIndexEnemy = 0;
		}
		
		if(isFlashing)
		{
			flashGhost(g);
		}
		else if(!isFlashing)
		{
			stayBlue(g);
		}
	}
	

	
	public void tick()
	{
		
		animation();
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	public void setPortalCrossingStatus(int portalStatus)
	{
		portalCrossingStatus = portalStatus;
	}
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public int getID()
	{
		return ghostID;
	}
	
	int getPortalCrossingStatus() 
	{
		return portalCrossingStatus;
	}

	int getMovementType() 
	{
		return movementType;
	}

	boolean getVulnerabilityStatus() 
	{
		return isVulnerable;
	}
}
