package main;

import entity.Ghost_Flashing;
import entity.Ghost_Hostile;
import entity.Ghost_Vulnerable;

public class EventHandler 
{
	GamePanel gp;
	
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
		if(gp.isActive == false)
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
	
	public void allToVulnerable()
	{
		gp.playSE(1);
		
		elapsedTimeWhileActiveInSeconds = 0.0f;
		
		gp.isActive = true;
		
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			gp.ghostArray[i] = new Ghost_Vulnerable(gp, i);
		}
		
		gp.numberOfEatenGhosts = 0;
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
