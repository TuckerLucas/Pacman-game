/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Ghost.java
* 
* Description: 
* 
* This file contains the implementation for ghost behaviour 
* regarding movement within the game map as well as animations.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;

public abstract class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	// Ghost movement variables
	public int movementType;

	
	public static int flashTime = 60*5;
	
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	public static int nEatenGhosts = 0;
	
	public int portalCrossingStatus;
	
	public static boolean isFlashing = false;
	
	public static int flashAnimationTime = 0;
	public static int flashAnimationTargetTime = 20;
	
	public static int timeSpentInSpawnBoxInSeconds = 0;
	
	public int ghostID;
	
	public static int spawnBoxX = 320;
	public static int spawnBoxY = 320;
	
	public static Ghost ghostArray[] = new Ghost[4];
	public static Ghost ghost;
	
	public static int minimumTimeToBeSpentInSpawnBoxInSeconds = 60*3;
	
	public Ghost()
	{
		
	}
	
	public static boolean canLeaveSpawnBox(int timeSpentInSpawnBoxInSeconds)
	{
		return (timeSpentInSpawnBoxInSeconds == minimumTimeToBeSpentInSpawnBoxInSeconds) ? true : false;
	}
	
	
	abstract void render(Graphics g);
	abstract void tick();
}