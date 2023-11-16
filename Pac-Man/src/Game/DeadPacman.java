package Game;

import java.awt.Graphics;

public class DeadPacman extends Pacman
{
	private static final long serialVersionUID = 1L;
	
	public static boolean pacmanDeathAnimationHasFinished = false;
	
	public DeadPacman(int lastAliveX, int lastAliveY)
	{
		spawnDeadPacman(lastAliveX, lastAliveY);
	}
	
	private void spawnDeadPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
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
				pacmanDeathAnimationHasFinished = true;
			}
		}
	}
	
	public void render(Graphics g)
	{	
		g.drawImage(Texture.pacmanDie[frameIndex], x, y, width, height, null);
	}
}