package character;

import java.awt.Graphics;

import Game.Animation;
import Game.Level;
import main.Game;

public class DeadPacman extends Pacman
{
	private static final long serialVersionUID = 1L;
	
	public static boolean pacmanDeathAnimationHasFinished = false;
	
	public DeadPacman(int lastAliveX, int lastAliveY, Game game)
	{
		super(game);
		spawnDeadPacman(lastAliveX, lastAliveY);
	}
	
	private void spawnDeadPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Level.objectWidth, Level.objectHeight);
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
			
			if(frameIndex >= Animation.deadPacmanSprites.length)
			{
				frameIndex = 0;
				pacmanDeathAnimationHasFinished = true;
			}
		}
	}
	
	public void render(Graphics g)
	{	
		g.drawImage(Animation.deadPacmanSprites[frameIndex], x, y, width, height, null);
	}
}