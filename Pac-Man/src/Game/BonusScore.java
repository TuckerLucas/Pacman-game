package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BonusScore extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static boolean isBeingDisplayed = false;
	
	private static double elapsedAnimationTimeInSeconds = 0.0;
	private double targetTimeForAnimationInSeconds = 5.0;
	
	private static double elapsedFrameTimeInSeconds = 0.0;
	private double targetTimePerFrameInSeconds = 0.1;
	
	private static int frameIndex = 0;
	private static int totalNumberOfFrames = Texture.bonusScore200.length;
	
	public static BonusScore bonusScore;
	
	public BonusScore()
	{
		setBounds(0, 0, Texture.objectWidth * 2, Texture.objectHeight);
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
	
	public static void displayBonusScore(int xCoordinate, int yCoordinate)
	{
		bonusScore.x = xCoordinate;
		bonusScore.y = yCoordinate;
		
		isBeingDisplayed = true;
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;
		elapsedAnimationTimeInSeconds = 0;
	}

	public static void sumBonusScoreToGameScore()
	{	
		int bonusScore = (int)calculateBonusScore();
		Game.score += bonusScore;
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
					g.drawImage(Texture.bonusScore200[frameIndex], x, y, width, height, null);
					break;
				case 2: 
					g.drawImage(Texture.bonusScore400[frameIndex], x, y, width, height, null);
					break;
				case 3: 
					g.drawImage(Texture.bonusScore800[frameIndex], x, y, width, height, null);
					break;
				case 4:
					g.drawImage(Texture.bonusScore1600[frameIndex], x, y, width, height, null);
					break;
			}
		}
	}
}

