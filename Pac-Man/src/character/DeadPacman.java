package character;

import java.awt.Graphics;

import main.GamePanel;

public class DeadPacman extends Pacman
{
	private static final long serialVersionUID = 1L;
	
	public DeadPacman(int lastAliveX, int lastAliveY, GamePanel gp)
	{
		super(gp);
		spawnDeadPacman(lastAliveX, lastAliveY);
	}
	
	private void spawnDeadPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, gp.tileSize, gp.tileSize);
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
				
				if(gp.numberOfLives == 0)
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