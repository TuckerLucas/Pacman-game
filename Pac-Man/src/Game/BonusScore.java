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
	
	public static int bonusScoreValue;
	
	public static BonusScore bonusScore;
	
	public BonusScore()
	{
		setBounds(0, 0, Texture.objectWidth, Texture.objectHeight);
	}
	
	public void tick()
	{
		manageAnimationTiming();
		checkBonusScoreValue();
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
	
	private void checkBonusScoreValue()
	{
		switch(Ghost.nEatenGhosts)
		{
			case 1: 
				bonusScoreValue = 200;
				break;
			case 2: 
				bonusScoreValue = 400; 
				break;
			case 3: 
				bonusScoreValue = 800;  
				break;
			case 4: 
				bonusScoreValue = 1600; 
				break;
		}
	}
	
	public void render(Graphics g)
	{
		if(isBeingDisplayed)
		{
			g.drawImage(Texture.bonusScore[frameIndex], Game.xEvent, Game.yEvent, width, height, null);
		}
	}
}

