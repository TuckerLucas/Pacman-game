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
	
	private double currentFrameTimeInSeconds = 0.0;
	private double frameTargetTimeInSeconds = 5.0;
	public static boolean isBeingDisplayed = false;
	public static int bonusScoreValue;
	
	private static int frameIndex = 0;
	private static int totalNumberOfFrames = Texture.bonusScore.length;
	
	public static BonusScore bonusScore;
	
	public BonusScore()
	{
		setBounds(0, 0, Texture.objectWidth, Texture.objectHeight);
	}
	
	public void tick()
	{
		runAnimation();
		checkStatus();
	}
	
	private void runAnimation()
	{
		currentFrameTimeInSeconds += Game.secondsPerTick;
		
		if(currentFrameTimeInSeconds >= frameTargetTimeInSeconds)
		{
			currentFrameTimeInSeconds = 0;	
			//frameIndex++;
			isBeingDisplayed = false;
		}
		
		if(frameIndex >= totalNumberOfFrames)
		{
			frameIndex = 0;
		}
	}
	
	private void checkStatus()
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

