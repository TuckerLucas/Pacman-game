/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Energizer.java
* 
* Description: 
* 
* This file contains the implementation for the 4 Energizers
* displayed in the game map.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;

public class Energizer extends Food
{
	private static final long serialVersionUID = 1L;
	
	private int spriteFrameDisplayTimeMilliseconds = 0;			
	private int spriteFrameTargetDisplayTimeMilliseconds = 10;	
	
	private static int currentSpriteFrameIndex = 0;
	private int totalNumberOfSpriteFrames = 2;
	
	public static int activeTime = 0; 
	private final int activeTargetTime = 60 * 8;
	
	private int points = 50;
	
	public static boolean isActive = false;
	
	public static Energizer energizer;
	
	public Energizer(int x, int y)
	{
		setBounds(x+2, y+2, 28, 28);
	}
	
	public static void activate()
	{
		new Sounds(Sounds.energizerSoundPath);
		
		Energizer.isActive = true;	
		Energizer.activeTime = 0;
		
		turnGhostsVulnerable();
	}
	
	public static void deactivate()
	{
		Energizer.activeTime = 0;
		Energizer.isActive = false;
		
		turnGhostsHostile();
	}
	
	public static void turnGhostsVulnerable()
	{
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			Ghost.ghostArray[i].isVulnerable = true;
		}
		
		Ghost.nEatenGhosts = 0;
	}
	
	public static void turnGhostsHostile()
	{
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			Ghost.ghostArray[i].isVulnerable = false;
		}
	}
	
	public int getFoodPoints()
	{
		return points;
	}

	
	public void tick()
	{	
		runAnimation();
		checkEnergizerActivityStatus();
	}
	
	public void runAnimation()
	{
		spriteFrameDisplayTimeMilliseconds++;
		
		if(spriteFrameDisplayTimeMilliseconds == spriteFrameTargetDisplayTimeMilliseconds)
		{
			spriteFrameDisplayTimeMilliseconds = 0;
			currentSpriteFrameIndex++;
		}
	}
	
	public void checkEnergizerActivityStatus()
	{
		if(!isActive)
		{
			return;
		}
		
		if(activeTime < activeTargetTime)	
		{
			checkEnergizerActivePhase();
		}
		else if(activeTime == activeTargetTime)		
		{
			deactivate();
		}
	}
	
	public void checkEnergizerActivePhase()
	{
		if(activeTime >= Ghost.flashTime)
		{
			Ghost.isFlashing = true;
		}
		else if(activeTime < Ghost.flashTime)
		{
			Ghost.isFlashing = false;
		}
		
		activeTime++;
	}
	
	public void render(Graphics g)
	{
		if(currentSpriteFrameIndex >= totalNumberOfSpriteFrames)
		{
			currentSpriteFrameIndex = 0;
		}
		
		g.drawImage(Texture.energizer[currentSpriteFrameIndex], x, y, width, height, null);	
	}
}