package entity;

import java.awt.Graphics;

import main.GamePanel;

public class BonusScore extends Character
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public boolean isBeingDisplayed = false;
	
	public double elapsedAnimationTimeInSeconds = 0.0;
	public double targetTimeForAnimationInSeconds = 5.0;
	
	public BonusScore(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
		this.width = gp.tileSize*2;
		
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;		
		targetTimePerFrameInSeconds = 0.1;
		totalNumberOfFrames = 2;
	}
	
	public void tick()
	{
		manageAnimationTiming(this);
	}
	
	public void displayBonusScore(int x, int y)
	{
		gp.bonusScore.x = x;
		gp.bonusScore.y = y;
		
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
		return Math.pow(2.0, (double)gp.eHandler.numberOfEatenGhosts) * 100.0;
	}
	
	public void render(Graphics g)
	{
		if(isBeingDisplayed)
		{
			switch(gp.eHandler.numberOfEatenGhosts)
			{
				case 1: 
					g.drawImage(gp.animation.bonusScore200Sprites[frameIndex], x, y, width, height, null);
					break;
				case 2: 
					g.drawImage(gp.animation.bonusScore400Sprites[frameIndex], x, y, width, height, null);
					break;
				case 3: 
					g.drawImage(gp.animation.bonusScore800Sprites[frameIndex], x, y, width, height, null);
					break;
				case 4:
					g.drawImage(gp.animation.bonusScore1600Sprites[frameIndex], x, y, width, height, null);
					break;
			}
		}
	}
}

