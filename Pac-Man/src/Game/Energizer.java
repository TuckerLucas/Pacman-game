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
	
	// Energizer animation variables
	private int frameTime = 0;			// Time current frame has been displayed
	private int frameTargetTime = 10;	// Target time for each frame to be displayed
	private static int spriteFrame = 0;	// Array index of frame being displayed
	private int spriteTargetFrame = 2;	// Array index of last frame of the animation 
	
	public static int activeTime = 0; 
	public static int activeTargetTime = 60*8;
	
	public static int points = 50;
	
	public static boolean isActive = false;
	
	public static Energizer energizer;
	
	public Energizer(int x, int y)
	{
		setBounds(x+2,y+2,28,28);
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
			Ghost.ghostArray[i] = new VulnerableGhost(Ghost.ghostArray[i].x, Ghost.ghostArray[i].y, Ghost.ghostArray[i].ghostID, Ghost.ghostArray[i].movementType, Ghost.ghostArray[i].portalCrossingStatus);
		}
		
		Ghost.nEatenGhosts = 0;
	}
	
	public static void turnGhostsHostile()
	{
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			Ghost.ghostArray[i] = new HostileGhost(Ghost.ghostArray[i].x, Ghost.ghostArray[i].y, Ghost.ghostArray[i].ghostID, Ghost.ghostArray[i].movementType, Ghost.ghostArray[i].portalCrossingStatus);
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
		frameTime++;
		
		if(frameTime == frameTargetTime)
		{
			frameTime = 0;
			spriteFrame++;
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
		if(spriteFrame >= spriteTargetFrame)
		{
			spriteFrame = 0;
		}
		
		g.drawImage(Texture.energizer[spriteFrame], x, y, width, height, null);	
	}
}