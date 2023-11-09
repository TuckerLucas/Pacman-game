/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: BonusScore.java
* 
* Description: 
* 
* This file contains the implementation for the bonus scores
* that appear when pacman eats a ghost.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BonusScore extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static double elapsedFrameTimeInSeconds = 0.0;
	private double targetTimePerFrameInSeconds = 0.1;
	public static double elapsedAnimationTimeInSeconds = 0.0;
	private double targetTimeForAnimationInSeconds = 5.0;
	public static int frameIndex = 0;
	private static int totalNumberOfFrames = Texture.bonusScore.length;
	public static boolean isBeingDisplayed = false;
	
	public static BonusScore bonusScore;
	
	public BonusScore()
	{
		setBounds(0, 0, Texture.objectWidth, Texture.objectHeight);
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
		
		if(elapsedAnimationTimeInSeconds < targetTimeForAnimationInSeconds)
		{
			elapsedFrameTimeInSeconds += Game.secondsPerTick;
			
			if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
			{
				frameIndex++;
				elapsedFrameTimeInSeconds = 0;
				
				if(frameIndex >= totalNumberOfFrames)
				{
					frameIndex = 0;
				}
			}
		}
		else
		{
			frameIndex = 0;
			elapsedAnimationTimeInSeconds = 0;
			isBeingDisplayed = false;
		}
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
	
	public static void displayBonusScore(int xCoordinate, int yCoordinate)
	{
		bonusScore.x = xCoordinate;
		bonusScore.y = yCoordinate;
		
		isBeingDisplayed = true;
		elapsedAnimationTimeInSeconds = 0;
		elapsedFrameTimeInSeconds = 0;
		frameIndex = 0;
	}
	
	public void render(Graphics g)
	{
		if(isBeingDisplayed)
		{
			g.drawImage(Texture.bonusScore[frameIndex], x, y, width, height, null);
		}
	}
}

