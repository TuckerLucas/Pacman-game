package Game;

import java.awt.Graphics;

public class DeadPacman extends Pacman
{
	private static final long serialVersionUID = 1L;

	public DeadPacman()
	{
		
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(frameIndex >= Texture.pacmanDie.length)
			{
				frameIndex = 0;
				Game.loadGameElements();
				
				if(numberOfLives == 0) 
				{
					Game.gameStatus = Game.lose;
				}
				else
				{	
					Game.gameStatus = Game.play;
				}
			}
		}
	}
	
	public void render(Graphics g)
	{	
		g.drawImage(Texture.pacmanDie[frameIndex], x, y, width, height, null);
	}
}