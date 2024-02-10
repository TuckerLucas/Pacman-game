package main;

import entity.Ghost_Flashing;
import entity.Ghost_Hostile;
import entity.Ghost_Vulnerable;
import entity.Pacman;
import entity.Pacman_Dead;
import food.Food;
import food.Food_Energizer;
import food.Food_Pellet;

public class EventHandler 
{
	GamePanel gp;
	
	public boolean isActive = false;
	public int numberOfEatenGhosts = 0;	
	
	public double timeInstantToBeginFlashingInSeconds = 5.0; 
	public double activeTargetTimeInSeconds = 8.0;
	public double elapsedTimeWhileActiveInSeconds = 0.0;
	
	public EventHandler(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void tick()
	{
		checkEnergizerActivity();
	}
	
	public void checkEnergizerActivity()
	{
		if(isActive == false)
		{
			return;
		}
		
		if(elapsedTimeWhileActiveInSeconds < activeTargetTimeInSeconds)	
		{
			elapsedTimeWhileActiveInSeconds += gp.secondsPerTick;
			
			if(elapsedTimeWhileActiveInSeconds >= timeInstantToBeginFlashingInSeconds)
			{
				vulnerableToFlashing();
			}
		}
		else if(elapsedTimeWhileActiveInSeconds >= activeTargetTimeInSeconds)		
		{
			flashingToHostile();
		}
	}
	
	public void eat(Food food)
	{	
		if(food instanceof Food_Energizer)
		{
			gp.playSE(1);
			allToVulnerable();
			gp.energizerList.remove(food);
		}
		else if(food instanceof Food_Pellet)
		{
			gp.playSE(4);
			gp.pelletList.remove(food);
		}

		gp.score += food.getFoodPoints();
	}
	
	public void eatGhost(int i)
	{
		gp.playSE(3);
		
		gp.bonusScore.displayBonusScore(gp.ghostArray[i].x, gp.ghostArray[i].y);
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i);
		gp.ghostArray[i].x = 320;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
				
		numberOfEatenGhosts++;
		
		gp.bonusScore.sumBonusScoreToGameScore();
	}
	
	public void die(Pacman pacman)
	{
		gp.playSE(2);
		gp.lives--;
		gp.bonusScore.isBeingDisplayed = false;
		elapsedTimeWhileActiveInSeconds = 0.0;
		gp.pacman = new Pacman_Dead(gp);
		gp.pacman.x = pacman.x;
		gp.pacman.y = pacman.y;
		gp.gameState = gp.lifeLostState;
	}
	
	public void allToVulnerable()
	{
		isActive = true;
		elapsedTimeWhileActiveInSeconds = 0.0;
		numberOfEatenGhosts = 0;

		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			gp.ghostArray[i] = new Ghost_Vulnerable(gp, i);
		}
	}
	
	public void vulnerableToFlashing()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i] instanceof Ghost_Vulnerable)
			{
				gp.ghostArray[i] = new Ghost_Flashing(gp, i);
			}
		}
	}
	
	public void flashingToHostile()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i] instanceof Ghost_Flashing)
			{
				gp.ghostArray[i] = new Ghost_Hostile(gp, i);
			}
		}
	}
}
