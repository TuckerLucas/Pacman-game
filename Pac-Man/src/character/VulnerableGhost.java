package character;

import java.awt.Graphics;

import Game.Animation;
import main.GamePanel;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public int frameIndexVulnerable = 0;
	public double elapsedFrameTimeInSecondsVulnerable = 0;		
	public double targetTimePerFrameInSecondsVulnerable = 0.05;
	public int totalNumberOfFramesVulnerable = Animation.vulnerableGhostSprites.length;
	
	public VulnerableGhost(int ID, int cD, int x, int y, int movementStatus, GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, gp);
	}

	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			moveRandomly(this);
		}
		
		manageVulnerableAnimationTiming();
	}
	
	public void manageVulnerableAnimationTiming()
	{
		elapsedFrameTimeInSecondsVulnerable += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsVulnerable >= targetTimePerFrameInSecondsVulnerable)
		{
			elapsedFrameTimeInSecondsVulnerable = 0;
			frameIndexVulnerable++;
			
			if(frameIndexVulnerable == totalNumberOfFramesVulnerable)
			{
				frameIndexVulnerable = 0;
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.vulnerableGhostSprites[frameIndexVulnerable], x, y, width, height, null);
	}
}
