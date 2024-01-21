package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.GamePanel;

public class BonusScore extends Rectangle
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public static boolean isBeingDisplayed = false;
	
	private static double elapsedAnimationTimeInSeconds = 0.0;
	private double targetTimeForAnimationInSeconds = 5.0;
	
	private static double elapsedFrameTimeInSeconds = 0.0;
	private double targetTimePerFrameInSeconds = 0.1;
	
	private static int frameIndex = 0;
	private static int totalNumberOfFrames = 2;
	
	public BonusScore(GamePanel gp)
	{
		this.gp = gp;
		setBounds(0, 0, gp.tileSize*2, gp.tileSize);
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
		
		elapsedAnimationTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedAnimationTimeInSeconds >= targetTimeForAnimationInSeconds)
		{
			isBeingDisplayed = false;
		}
		
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
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
		gp.bonusScore.x = xCoordinate;
		gp.bonusScore.y = yCoordinate;
		
		isBeingDisplayed = true;
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;
		elapsedAnimationTimeInSeconds = 0;
	}

	public void sumBonusScoreToGameScore()
	{	
		int bonusScore = (int)calculateBonusScore();
		gp.score += bonusScore;
	}
	
	private double calculateBonusScore()
	{
		return Math.pow(2.0, (double)gp.numberOfEatenGhosts) * 100.0;
	}
	
	public void render(Graphics g)
	{
		if(isBeingDisplayed)
		{
			switch(gp.numberOfEatenGhosts)
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

