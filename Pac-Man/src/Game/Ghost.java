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
import java.util.Random;

public class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	private Random randomGen;
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	// Ghost movement variables
	private int movementType;

	private boolean findDir1Blocked = false;
	
	private int nextDir = 0;
	public int currentDir = 0;
	
	private int methodicalTime = 0;
	private int methodicalTargetTime; 
	private boolean coolDown = false;
	private int coolDownTime = 0;
	private int coolDownTargetTime = 60*3; 
	private int timeImage = 0;		
	private int targetTimeImage = 4;
	
	private zoneDirections zoneDirectionsArray[] = new zoneDirections[16];
	
	private int deltaX;
	private int deltaY;
	private int detectionRange;
	
	public static int numberOfEatenGhosts = 0;
	
	private int portalCrossingStatus;
	
	private int imageIndexEnemy = 0;
	
	public boolean isVulnerable = false;
	
	public static int flashAnimationTime = 0;
	public static int flashAnimationTargetTime = 20;
	
	private int timeSpentInSpawnBoxInSeconds = 0;
	
	public int ghostID;
	private int pacmanZone = 1;
	
	public static int spawnBoxX = 320;
	public static int spawnBoxY = 320;
	
	public static Ghost ghostArray[] = new Ghost[4];
	
	public static int minimumTimeToBeSpentInSpawnBoxInSeconds = 60*6; 
	
	class zoneDirections
	{	
		int methodicalDir1;
		int methodicalDir2;
		int findDir1;
		int findDir2;
	}
	
	public Ghost()
	{
		
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
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public int getID()
	{
		return ghostID;
	}
	
	// Constructor
	public Ghost(int ID, int movementStatus, int portalStatus, boolean vulnerabilityStatus)
	{		
		// Initialize zone directions array
		loadZoneDirectionsArray();
		
		ghostID = ID;                  			// Update ghost ID
		movementType = movementStatus; 			// Set default ghost movement type
		isVulnerable = vulnerabilityStatus; 	// Update vulnerability status
		portalCrossingStatus = portalStatus;	// Update portal crossing status
		
		// Check ghost's portal crossing status
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(spawnBoxX, spawnBoxY);		// Spawn ghost in spawn box
				break;
			case Character.crossingPortalFromLeftSide:
				spawnGhost(portalCrossingPointRightSideXCoordinate, rightPortalEntryYCoordinate);	// Spawn ghost in the movingRight portal (crossed movingLeft portal)
				currentDir = movingLeft;									// Update the ghost's current direction of movement
				break;
			case Character.crossingPortalFromRightSide:
				spawnGhost(portalCrossingPointLeftSideXCoordinate, portalCrossingPointLeftSideYCoordinate);		// Spawn ghost in the movingLeft portal (crossed movingRight portal)
				currentDir = movingRight;									// Update the ghost's current direction of movement
				break;
		}

		// Change ghost's detection range and time moving in a methodical manner based on selected difficulty
		switch(Game.difficulty)
		{
			case 1:
				detectionRange = 80;
				methodicalTargetTime = 60 * 10;
				break;
			case 2:
				detectionRange = 100;
				methodicalTargetTime = 60 * 15;
				break;
			case 3:
				detectionRange = 120;
				methodicalTargetTime = 60 * 20;
				break;
			case 4:
				detectionRange = 150;
				methodicalTargetTime = 60 * 25;
				break;
		}
		
		randomGen = new Random();
		generateNextDirection();
	}
	
	public static boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 304)) ? true : false;
	}
	
	public static boolean canLeaveSpawnBox(int timeSpentInSpawnBoxInSeconds)
	{
		return (timeSpentInSpawnBoxInSeconds == minimumTimeToBeSpentInSpawnBoxInSeconds) ? true : false;
	}
	
	private void spawnGhost(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}
	
	public static void turnAllVulnerable()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i].isVulnerable = true;
		}
		
		numberOfEatenGhosts = 0;
	}
	
	public static void turnAllHostile()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i].isVulnerable = false;
		}
	}
	
	// Populate the zone directions array
	private void loadZoneDirectionsArray()
	{	
		// Iterate through the zone directions array
		for(int zone = 0; zone < zoneDirectionsArray.length; zone++)
		{
			zoneDirectionsArray[zone] = new zoneDirections();
			
			// Populate each zone element with the ghost's required directions of travel to this zone
			switch(zone)
			{
				case 0:
					zoneDirectionsArray[zone].methodicalDir1 = movingLeft;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = movingDownwards;
					zoneDirectionsArray[zone].findDir2 = movingUpwards;
					break;
				case 1:
					zoneDirectionsArray[zone].methodicalDir1 = movingLeft;
					zoneDirectionsArray[zone].methodicalDir2 = movingUpwards;
					zoneDirectionsArray[zone].findDir1 = movingDownwards;
					break;
				case 2:
					zoneDirectionsArray[zone].methodicalDir1 = movingUpwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingLeft;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					break;
				case 3:
					zoneDirectionsArray[zone].methodicalDir1 = movingUpwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingLeft;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					break;
				case 4:
					zoneDirectionsArray[zone].methodicalDir1 = movingUpwards;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					zoneDirectionsArray[zone].findDir2 = movingLeft;
					break;
				case 5:
					zoneDirectionsArray[zone].methodicalDir1 = movingUpwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingRight;
					zoneDirectionsArray[zone].findDir1 = movingLeft;
					break;
				case 6:
					zoneDirectionsArray[zone].methodicalDir1 = movingUpwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingRight;
					zoneDirectionsArray[zone].findDir1 = movingLeft;
					break;
				case 7:
					zoneDirectionsArray[zone].methodicalDir1 = movingRight;
					zoneDirectionsArray[zone].methodicalDir2 = movingUpwards;
					zoneDirectionsArray[zone].findDir1 = movingDownwards;
					break;
				case 8:
					zoneDirectionsArray[zone].methodicalDir1 = movingRight;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = movingUpwards;
					zoneDirectionsArray[zone].findDir2 = movingDownwards;
					break;
				case 9:
					zoneDirectionsArray[zone].methodicalDir1 = movingRight;
					zoneDirectionsArray[zone].methodicalDir2 = movingDownwards;
					zoneDirectionsArray[zone].findDir1 = movingUpwards;
					break;
				case 10:
					zoneDirectionsArray[zone].methodicalDir1 = movingDownwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingRight;
					zoneDirectionsArray[zone].findDir1 = movingLeft;
					break;
				case 11:
					zoneDirectionsArray[zone].methodicalDir1 = movingDownwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingRight;
					zoneDirectionsArray[zone].findDir1 = movingLeft;
					break;
				case 12:
					zoneDirectionsArray[zone].methodicalDir1 = movingDownwards;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					zoneDirectionsArray[zone].findDir2 = movingLeft;
					break;
				case 13:
					zoneDirectionsArray[zone].methodicalDir1 = movingDownwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingLeft;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					break;
				case 14:
					zoneDirectionsArray[zone].methodicalDir1 = movingDownwards;
					zoneDirectionsArray[zone].methodicalDir2 = movingLeft;
					zoneDirectionsArray[zone].findDir1 = movingRight;
					break;
				case 15:
					zoneDirectionsArray[zone].methodicalDir1 = movingLeft;
					zoneDirectionsArray[zone].methodicalDir2 = movingDownwards;
					zoneDirectionsArray[zone].findDir1 = movingUpwards;
					break;
			}
		}
	}

	
	// Update ghost's distance to pacman
	private void updateDistanceToPacman()
	{
		deltaX = x - Pacman.pacman.x; // X axis distance
		deltaY = y - Pacman.pacman.y; // Y axis distance
	}
	
	// Check if pacman is within detection range
	private boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	


	// Get zone that pacman is in relative to the ghost
	private void updatePacmanZone()
	{	
		if(deltaX > 0 && deltaY == 0)										
		{
			pacmanZone = 0;										// Pacman in zone 0
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
		{
			pacmanZone = 1;										// Pacman in zone 1
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX == deltaY)					
		{
			pacmanZone = 2;										// Pacman in zone 2
		}
		else if(deltaX > 0 && deltaY > 0 && deltaY > deltaX)						
		{
			pacmanZone = 3;										// Pacman in zone 3
		}
		else if(deltaX == 0 && deltaY > 0)									
		{
			pacmanZone = 4;										// Pacman in zone 4
		}
		else if(deltaX < 0 && deltaY > 0 && deltaY > -deltaX)						
		{
			pacmanZone = 5;										// Pacman in zone 5
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX == deltaY)					
		{
			pacmanZone = 6;										// Pacman in zone 6
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX > deltaY)						
		{
			pacmanZone = 7;										// Pacman in zone 7
		}
		else if(deltaX < 0 && deltaY == 0)										
		{
			pacmanZone = 8;										// Pacman in zone 8
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			pacmanZone = 9;										// Pacman in zone 9
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			pacmanZone = 10;									// Pacman in zone 10
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			pacmanZone = 11;									// Pacman in zone 11
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			pacmanZone = 12;									// Pacman in zone 12
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			pacmanZone = 13;									// Pacman in zone 13
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			pacmanZone = 14;									// Pacman in zone 14
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			pacmanZone = 15;									// Pacman in zone 15
		}
	}
	
	// Generate the next direction of travel at random
	private void generateNextDirection()
	{
		nextDir = randomGen.nextInt(4);
		
		/*
		if(!canLeaveSpawnBox(timeSpentInSpawnBoxInSeconds) && nextDir == movingUpwards)
		{
			nextDir = currentDir;
		}*/
	}
	
	private void moveRandomly()
	{
		// Update ghost's distance to pacman
		updateDistanceToPacman();
		
		// In cool movingDownwards period from methodical movement
		if(coolDown == true)
		{
			// Increase cool movingDownwards time
			coolDownTime++;
			
			// Cool movingDownwards time has reached the target time 
			if(coolDownTime == coolDownTargetTime)
			{
				// Clear cool movingDownwards flag
				coolDown = false;
				
				// Reset cool movingDownwards timer 
				coolDownTime = 0;
			}
		}
		// No longer in cool movingDownwards period from methodical movement
		else if(coolDown == false)
		{
			// Ghost not vulnerable, not in spawn box and pacman within detection range
			if(pacmanIsClose() && !isVulnerable && !isInSpawnBox(this))
			{
				// Switch movement type to methodical
				movementType = methodicalMovement;
			}
		}
		
		// Ghost can move in the next defined direction
		if(canMove(nextDir, this))
		{
			currentDir = nextDir;
			// Move in the next defined direction
			moveGivenCharacterInGivenDirection(this, nextDir);
		}
		// Ghost can move in the current defined direction
		else if(canMove(currentDir, this))
		{
			// Continue moving in the current defined direction
			moveGivenCharacterInGivenDirection(this, currentDir);
			
			// Will continue moving in the current direction until can move in the next one
			return;
		}
		
		// Generate a new direction for the ghost to move in
		generateNextDirection();
	}
	
	private void moveMethodically()
	{
		// Update ghost's distance to pacman
		updateDistanceToPacman();
		
		// Energizer in active state or ghost is in the spawn box
		if(Energizer.isActive == true || isInSpawnBox(this))
		{
			// Change movement type to random
			movementType = randomMovement;
		}

		// Update pacman's zone relative to the ghost
		updatePacmanZone();
		
		// Ghost can move in the first methodical direction
		if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir1,  this))
		{
			currentDir = zoneDirectionsArray[pacmanZone].methodicalDir1;
			// Move in the first methodical direction
			moveGivenCharacterInGivenDirection(this, zoneDirectionsArray[pacmanZone].methodicalDir1);
		}
		// Ghost can move in the second methodical direction
		else if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir2, this))
		{
			currentDir = zoneDirectionsArray[pacmanZone].methodicalDir2;
			// Move in the second methodical direction
			moveGivenCharacterInGivenDirection(this, zoneDirectionsArray[pacmanZone].methodicalDir2);
		}
		// Cannot move in either methodical direction
		else
		{
			// Ghost is stuck, need to change movement type to find path back
			movementType = findingPath;
		}
		
		// Increase time that ghost has been moving methodically
		methodicalTime++;								
		
		// Target time for methodical movement reached
		if(methodicalTime == methodicalTargetTime) 				
		{			
			methodicalTime = 0;				// Reset timer for methodical movement
			coolDown 	 = true;			// Activate cool movingDownwards period for methodical movement
			movementType = randomMovement;	// Return to random movement
		}
	}

	private void findingPath()
	{
		// Ghost can move in first methodical direction
		if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir1, this))
		{
			// Reset flag regarding blocked first find path direction
			findDir1Blocked = false;
			
			// Change movement type back to methodical
			movementType = methodicalMovement;
		}
		// Ghost cannot move in first methodical direction
		else
		{
			// First find path direction isn't blocked
			if(findDir1Blocked == false)
			{
				// Ghost can move in first find path direction
				if(canMove(zoneDirectionsArray[pacmanZone].findDir1, this))
				{
					currentDir = zoneDirectionsArray[pacmanZone].findDir1;
					// Move in first find path direction
					moveGivenCharacterInGivenDirection(this, zoneDirectionsArray[pacmanZone].findDir1);
				}
				// Cannot move in first find path direction
				else
				{
					// First find path direction is blocked, set flag
					findDir1Blocked = true;
				}
			}
			// First find path direction is blocked 
			else if(findDir1Blocked == true)
			{
				currentDir = zoneDirectionsArray[pacmanZone].findDir2;
				// Move in second find path direction (which is known not to be blocked)
				moveGivenCharacterInGivenDirection(this, zoneDirectionsArray[pacmanZone].findDir2);
			}
		}
	}

	
	// Manage ghost movement
	private void selectGhostMovementType()
	{
		switch(movementType)
		{
			case randomMovement: moveRandomly(); break;	// Move in a random fashion
			case methodicalMovement: moveMethodically(); break;	// Move in a methodical fashion (chase pacman)
			case findingPath: findingPath(); break;	// Find path to pacman when stuck
		}			
	}
	
	private void animation()
	{
		timeImage ++;
		
		if(timeImage == targetTimeImage)
		{
			timeImage = 0;
			imageIndexEnemy ++;
		}
		
		flashAnimationTime++;
		
		if(flashAnimationTime == flashAnimationTargetTime)
		{
			flashAnimationTime = 0;
			
			if(Texture.flashAnimationPhase == 3)
			{
				Texture.flashAnimationPhase = 0;
			}
			else
			{
				Texture.flashAnimationPhase++;
			}
		}
	}
	
	
	private void flashGhost(Graphics g)
	{
		g.drawImage(Texture.flashGhost[Texture.flashAnimationPhase], x, y, width, height, null);
	}
	
	private void stayBlue(Graphics g)
	{
		g.drawImage(Texture.blueGhost[imageIndexEnemy], x, y, width, height, null);
	}
	
	private void look(int direction, Graphics g)
	{
		g.drawImage(Texture.ghostLook[ghostID][direction][imageIndexEnemy], x, y, width, height, null);
	}

	public void render(Graphics g)
	{
		if(imageIndexEnemy == 2)
		{
			imageIndexEnemy = 0;
		}

		if(isVulnerable == false)
		{
			look(currentDir, g);
		}
		else
		{
			if(VulnerableGhost.isFlashing)
			{
				flashGhost(g);
			}
			else if(!VulnerableGhost.isFlashing)
			{
				stayBlue(g);
			}
		}
	}
		
	private void spawnBoxEvents()
	{
		if(isInSpawnBox(this))
		{
			if(!canLeaveSpawnBox(timeSpentInSpawnBoxInSeconds))
			{
				timeSpentInSpawnBoxInSeconds++;
			}
		}
	}
	
	public void tick()
	{		
		//spawnBoxEvents();
		Character.portalEvents(this);
		
		if(portalCrossingStatus == Character.notCrossingPortal)
		{
			selectGhostMovementType();
		}
		
		animation();
	}

	int getPortalCrossingStatus() 
	{
		return portalCrossingStatus;
	}

	int getMovementType() 
	{
		return movementType;
	}

	boolean getVulnerabilityStatus() 
	{
		return isVulnerable;
	}
}