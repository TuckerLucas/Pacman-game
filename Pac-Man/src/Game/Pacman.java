/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Pacman.java
* 
* Description: 
* 
* This file contains the implementation for the Pacman 
* character regarding movement around the map, collisions with
* other characters/objects and animations.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	// Intersected ghost variable
	private int intersectedGhost = -1;
	
	// Pacman spawn coordinate variables
	public static int spawnX = 320;
	public static int spawnY = 512;
	
	public static int currentDir;
	public static int nextDir;
	
	public static int portalCrossingStatus = notCrossingPortal;
	
	public static double elapsedFrameTimeInSeconds = 0.0;	
	public static double targetTimePerFrameInSeconds = 0.1;
	
	public static int frameIndex = 0;
	
	public static boolean deathAnimationDisplayed = false;
	
	public static Pacman pacman;
	
	public static int LIVES = 3;
	
	// Pacman lives variable
	public static int lives = LIVES;
	
	// Constructor
	public Pacman(int spawnType, int nD)
	{
		// Check where to spawn pacman
		switch(spawnType)
		{
			// Normal spawn
			case Character.notCrossingPortal:
				
				// Spawn pacman normally
				spawnPacman(spawnX, spawnY);
				
				nextDir = stopped;
				
				// Make pacman look right on start-up
				currentDir = right;
				
				break;
				
			// Crossing left portal
			case Character.crossingPortalFromLeftSide:
				
				// Spawn pacman at the right portal
				spawnPacman(portalRightSideCrossingPointXCoordinate, portalYCoordinate);
				
				// Keep pacman moving left
				currentDir = left;
				
				nextDir = nD;
				
				break;
				
			// Left portal spawn
			case Character.crossingPortalFromRightSide:
				
				// Spawn pacman at the left portal
				spawnPacman(portalLeftSideCrossingPointXCoordinate, portalYCoordinate);
				
				// Keep pacman moving right
				currentDir = right;
				
				nextDir = nD;
				
				break;
		}
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		if(portalCrossingStatus == notCrossingPortal)
		{
			move(this, nextDir);
		}
		

		portalEvents(this);
		foodCollision();
		ghostCollision();
		manageAnimationTiming();
	}

	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	public void setPortalCrossingStatus(int portalStatus)
	{
		portalCrossingStatus = portalStatus;
	}
	
	// Spawn pacman at the given coordinates
	private void spawnPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth,Texture.objectHeight);
	}

	private void foodCollision()
	{	
		for(int i = 0; i < Food.foodList.size(); i++) 		
		{    
			if(this.intersects(Food.foodList.get(i)))							
			{
				Food.eat(Food.foodList.get(i));
				break;
			}
		}
	}	

	public boolean pacmanIntersectedGhost()
	{
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			if(Ghost.ghostArray[i].intersects(this))
			{	
				intersectedGhost = i;
				
				return true;
			}
		}
		return false;
	}

	private void eatGhost()
	{
		Sounds.playSoundEffect(Sounds.ghostEatenSoundPath);
		
		Ghost.ghostArray[intersectedGhost] = new Ghost(intersectedGhost, Ghost.randomMovement, notCrossingPortal, false);
		
		Ghost.numberOfEatenGhosts++;
		
		if(Ghost.numberOfEatenGhosts == Ghost.ghostArray.length)
		{
			Energizer.deactivate();
		}
		
		BonusScore.displayBonusScore(x, y);
		BonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		lives--;
		Game.gameStatus = Game.lifeLost;
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
	}
	
	public void ghostCollision()
	{
		if(pacmanIntersectedGhost())
		{
			if(Energizer.isActive)
			{
				if(Ghost.ghostArray[intersectedGhost].isVulnerable)
				{
					eatGhost();
					
					return;
				}
			}
			
			if(Game.gameStatus == Game.play)
			{
				die();
			}
		}
	}
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public int getID()
	{
		return -1;
	}
	
	int getPortalCrossingStatus() 
	{
		return -1;
	}

	int getMovementType() 
	{
		return -1;
	}

	boolean getVulnerabilityStatus() 
	{
		return false;
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
		}
	}
	
	public void render(Graphics g)
	{
		if(Game.gameStatus == Game.lifeLost)
		{	
			if(frameIndex == Texture.pacmanDie.length)
			{
				deathAnimationDisplayed = true;
				frameIndex = 0;
				Game.loadGameElements();
				
				if(lives == 0)
				{
					Game.gameStatus = Game.lose;
				}
				else
				{	
					Game.gameStatus = Game.play;
				}
			}
			
			if(!deathAnimationDisplayed)
			{
				g.drawImage(Texture.pacmanDie[frameIndex], x, y, width, height, null);
			}
		}
		else
		{
			if(frameIndex >= Texture.pacmanLook[currentDir].length)
			{
				frameIndex = 0;
			}
			
			g.drawImage(Texture.pacmanLook[currentDir][frameIndex], x, y, width, height, null);
		}
	}
}