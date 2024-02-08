package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman_Dead extends Pacman
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Pacman_Dead(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(frameIndex >= gp.animation.deadPacmanSprites.length)
			{
				frameIndex = 0;
				
				if(gp.lives == 0)
				{
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
	
	public void render(Graphics g)
	{	
		g.drawImage(gp.animation.deadPacmanSprites[frameIndex], x, y, width, height, null);
	}
}