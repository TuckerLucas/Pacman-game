package Game;

import java.awt.Graphics;

public class DeadPacman extends Pacman
{
	private static final long serialVersionUID = 1L;
	
	public static boolean deathAnimationHasFinished = false;
	
	public DeadPacman(int x, int y)
	{
		
		spawnPacman(x, y);
	}
	
	private void spawnPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}
	
	public void tick()
	{
		manageAnimationTiming();
		checkGameStatusNeedsChanging();
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
				deathAnimationHasFinished = true;
			}
		}
	}
	
	private void checkGameStatusNeedsChanging()
	{
		if(!deathAnimationHasFinished)
		{
			return;
		}
		
		numberOfLives--;
		
		deathAnimationHasFinished = false;
		
		if(numberOfLives == 0) 
		{
			Game.gameStatus = Game.lose;
		}
		else
		{	
			Game.loadGameElements();
			Game.gameStatus = Game.play;
		}
	}
	
	public void render(Graphics g)
	{	
		g.drawImage(Texture.pacmanDie[frameIndex], x, y, width, height, null);
	}
}