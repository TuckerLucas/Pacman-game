package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

import character.Ghost;
import main.Game;

public class BonusScore extends Rectangle
{
	Game game;
	
	private static final long serialVersionUID = 1L;
	
	public static boolean isBeingDisplayed = false;
	
	private static double elapsedAnimationTimeInSeconds = 0.0;
	private double targetTimeForAnimationInSeconds = 5.0;
	
	private static double elapsedFrameTimeInSeconds = 0.0;
	private double targetTimePerFrameInSeconds = 0.1;
	
	private static int frameIndex = 0;
	private static int totalNumberOfFrames = 2;
	
	public BonusScore(Game game)
	{
		this.game = game;
		setBounds(0, 0, Level.objectWidth*2, Level.objectHeight);
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	private void manageAnimationTiming()
	{
		if(!isBeingDisplayed)
		{
			return;
		}
		
		elapsedAnimationTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedAnimationTimeInSeconds >= targetTimeForAnimationInSeconds)
		{
			isBeingDisplayed = false;
		}
		
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			
			if(frameIndex >= totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
	}
	
	public void displayBonusScore(int xCoordinate, int yCoordinate)
	{
		game.bonusScore.x = xCoordinate;
		game.bonusScore.y = yCoordinate;
		
		isBeingDisplayed = true;
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;
		elapsedAnimationTimeInSeconds = 0;
	}

	public void sumBonusScoreToGameScore()
	{	
		int bonusScore = (int)calculateBonusScore();
		game.score += bonusScore;
	}
	
	private static double calculateBonusScore()
	{
		return Math.pow(2.0, (double)Ghost.numberOfEatenGhosts) * 100.0;
	}
	
	public void render(Graphics g)
	{
		if(isBeingDisplayed)
		{
			switch(Ghost.numberOfEatenGhosts)
			{
				case 1: 
					g.drawImage(Animation.bonusScore200Sprites[frameIndex], x, y, width, height, null);
					break;
				case 2: 
					g.drawImage(Animation.bonusScore400Sprites[frameIndex], x, y, width, height, null);
					break;
				case 3: 
					g.drawImage(Animation.bonusScore800Sprites[frameIndex], x, y, width, height, null);
					break;
				case 4:
					g.drawImage(Animation.bonusScore1600Sprites[frameIndex], x, y, width, height, null);
					break;
			}
		}
	}
}

