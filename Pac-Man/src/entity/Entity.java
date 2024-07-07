package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.GamePanel;

public class Entity extends Rectangle
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
	
	public int frameIndex;
	public double elapsedFrameTimeInSeconds;
	public double targetTimePerFrameInSeconds;
	public int totalNumberOfFrames;
	
	public String name;
	public String action;
	
	public Entity(GamePanel gp)
	{
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
	
	public void render(Graphics g)
	{
		
	}
	
	public void manageAnimationTiming(Entity entity)
	{
		if(entity instanceof BonusScore)
		{
			if(!gp.bonusScore.isBeingDisplayed)
			{
				return;
			}
			
			gp.bonusScore.elapsedAnimationTimeInSeconds += gp.secondsPerTick;
			
			if(gp.bonusScore.elapsedAnimationTimeInSeconds >= gp.bonusScore.targetTimeForAnimationInSeconds)
			{
				gp.bonusScore.isBeingDisplayed = false;
			}
		}
		
		entity.elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(entity.elapsedFrameTimeInSeconds >= entity.targetTimePerFrameInSeconds)
		{
			entity.elapsedFrameTimeInSeconds = 0;
			entity.frameIndex++;
			
			if(entity.frameIndex == entity.totalNumberOfFrames)
			{
				entity.frameIndex = 0;
				
				if(entity instanceof Pacman_Dead)
				{
					if(gp.lives == 0)
					{
						gp.fHandler.reorderLeaderboardInfo();
						gp.fHandler.writeLeaderboardInfo();
						gp.gameState = gp.gameOverState;
					}
					else
					{
						gp.respawnCharacters();
						gp.gameState = gp.playState;
					}
				}
			}
		}
	}
}