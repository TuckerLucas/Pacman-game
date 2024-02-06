package main;

import entity.Ghost_Flashing;
import entity.Ghost_Hostile;
import entity.Ghost_Vulnerable;

public class EventHandler 
{
	GamePanel gp;
	
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
		
		if(gp.elapsedTimeWhileActiveInSeconds < gp.activeTargetTimeInSeconds)	
		{
			gp.elapsedTimeWhileActiveInSeconds += gp.secondsPerTick;
			
			if(gp.elapsedTimeWhileActiveInSeconds >= gp.timeInstantToBeginFlashingInSeconds)
			{
				vulnerableToFlashing();
			}
		}
		else if(gp.elapsedTimeWhileActiveInSeconds >= gp.activeTargetTimeInSeconds)		
		{
			flashingToHostile();
		}
	}
	
	public void allToVulnerable()
	{
		gp.playSE(1);
		
		gp.elapsedTimeWhileActiveInSeconds = 0.0f;
		
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
