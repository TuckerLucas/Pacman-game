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
	
	// Direction variables
	public static final int stopped = -1;
	
	// Pacman animation variables
	public static int eatingAnimationTime 		= 0;	
	public static int eatingAnimationTargetTime = 6;
	
	public static int deathAnimationTime 		= 0;	
	public static int deathAnimationTargetTime = 6;
	
	public static boolean deathAnimationDisplayed = false;
	
	public static boolean blockMovement = false;
	
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
				
				// Pacman starts by moving right
				//nextDir = nD;
				
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
			// Check for collision with food
			if(this.intersects(Food.foodList.get(i)))							
			{
				// Eat the food
				Food.eat(Food.foodList.get(i));
				
				break;
			}
		}
	}	

	// Check if pacman and a ghost have intersected
	public boolean intersectedWithGhost()
	{
		// Iterate through the ghost array
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			// Check for intersection between ghost and pacman
			if(Ghost.ghostArray[i].intersects(this))
			{	
				// Identify the intersected ghost
				intersectedGhost = i;
				
				return true;
			}
		}
		
		return false;
	}

	// Make pacman eat intersected ghost
	private void eatGhost()
	{
		// Ghost eaten sound effect
		new Sounds(Sounds.ghostEatenSoundPath);
		
		// Respawn eaten ghost
		Ghost.ghostArray[intersectedGhost] = new Ghost(intersectedGhost, Ghost.randomMovement, notCrossingPortal, false);
		
		Ghost.numberOfEatenGhosts++;
		
		if(Ghost.numberOfEatenGhosts == 4)
		{
			Energizer.deactivate();
		}
		
		BonusScore.displayBonusScore(x, y);
		BonusScore.sumBonusScoreToGameScore();
	}
	
	// Manage pacman death
	private void die()
	{
		// Pacman death sound effect
		new Sounds(Sounds.pacmanDeathSoundPath);
		
		// Decrement amount of lives
		lives--;

		Game.gameStatus = Game.lifeLost;				
	}
	
	// Manage pacman collisions with ghosts
	public void ghostCollision()
	{
		// Check for collision between pacman and ghost
		if(intersectedWithGhost() == true)
		{
			// Check if energizer is active
			if(Energizer.isActive == true)
			{
				// Check if ghost hasn't been eaten yet
				if(Ghost.ghostArray[intersectedGhost].isVulnerable == true)
				{
					// Eat the ghost
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
	
	// Manage pacman animation timing
	public void eatingAnimation()
	{
		// Increase current animation phase time
		eatingAnimationTime++;
		
		// Check if time for animation phase is complete
		if(eatingAnimationTime == eatingAnimationTargetTime)
		{
			// Reset timer for animation phase
			eatingAnimationTime = 0;
			
			// Move to the next animation phase
			Texture.pacmanEatingAnimationPhase++;
		}
	}
	
	public void deathAnimation()
	{
		// Increase current animation phase time
		deathAnimationTime++;
		
		// Check if time for animation phase is complete
		if(deathAnimationTime == deathAnimationTargetTime)
		{
			// Reset timer for animation phase
			deathAnimationTime = 0;
			
			// Move to the next animation phase
			Texture.pacmanDeathAnimationPhase++;
		}
	}
	
	// Render object
	public void render(Graphics g)
	{
		if(Game.gameStatus == Game.lifeLost)
		{
			nextDir = stopped;
			
			// Check if death animation is in the last phase
			if(Texture.pacmanDeathAnimationPhase == 21)
			{
				deathAnimationDisplayed = true;
				Texture.pacmanDeathAnimationPhase = 0;
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
				g.drawImage(Texture.pacmanDie[Texture.pacmanDeathAnimationPhase], x, y, width, height, null);
			}
		}
		else
		{
			// Check if eating animation is in the last phase
			if(Texture.pacmanEatingAnimationPhase == 3)
			{
				// Restart animation
				Texture.pacmanEatingAnimationPhase = 0;
			}
			
			g.drawImage(Texture.pacmanLook[currentDir][Texture.pacmanEatingAnimationPhase], x, y, width, height, null);
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
		
		if(Game.gameStatus == Game.lifeLost)
		{
			deathAnimation();
		}
		else
		{
			eatingAnimation();
		}
	}
}