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
import java.awt.Rectangle;
import java.util.Random;

public class Ghost extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	// 
	private int spd = 2;
	
	private Random randomGen;
	
	// Ghost movement variables
	private int movementType;
	private final int randomMovement = 0;
	private final int methodicalMovement = 1;
	private final int findingPath = 2;
	private boolean findDir1Blocked = false;
	
	public static int flashTime = 60*5;
	
	public static final int right = 0; 
	public static final int left = 1;
	public static final int up = 2;
	public static final int down  = 3;
	
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
	
	private int portalCrossingStatus;

	public static final int notCrossingPortal = 0;
	public static final int crossingLeftPortal = 1;
	public static final int crossingRightPortal = 2;
	
	private int imageIndexEnemy = 0;
	
	public boolean isVulnerable = false;
	public static boolean isFlashing = false;
	
	public static int flashAnimationTime = 0;
	public static int flashAnimationTargetTime = 20;
	
	private boolean canLeaveBox;
	private int inBoxTime = 0;
	private int inBoxTargetTime = 60*3;
	
	public int ghostID;
	private int pacmanZone = 1;
	
	class zoneDirections
	{	
		int methodicalDir1;
		int methodicalDir2;
		int findDir1;
		int findDir2;
	}
	
	// Constructor
	public Ghost(int ID, int portalStatus, boolean vulnerabilityStatus, boolean boxStatus)
	{		
		// Initialize zone directions array
		loadZoneDirectionsArray();
		
		ghostID = ID;                  			// Update ghost ID
		movementType = randomMovement; 			// Set default ghost movement type
		isVulnerable = vulnerabilityStatus; 	// Update vulnerability status
		portalCrossingStatus = portalStatus;	// Update portal crossing status
		canLeaveBox = boxStatus;
		
		// Check ghost's portal crossing status
		switch(portalCrossingStatus)
		{
			case notCrossingPortal:
				spawnGhost(Game.centerBoxX,Game.centerBoxY);		// Spawn ghost in spawn box
				break;
			case crossingLeftPortal:
				spawnGhost(Game.rightPortalX,Game.rightPortalY);	// Spawn ghost in the right portal (crossed left portal)
				currentDir = left;									// Update the ghost's current direction of movement
				break;
			case crossingRightPortal:
				spawnGhost(Game.leftPortalX,Game.leftPortalY);		// Spawn ghost in the left portal (crossed right portal)
				currentDir = right;									// Update the ghost's current direction of movement
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
	
	private void spawnGhost(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
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
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = down;
					zoneDirectionsArray[zone].findDir2 = up;
					break;
				case 1:
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = up;
					zoneDirectionsArray[zone].findDir1 = down;
					break;
				case 2:
					zoneDirectionsArray[zone].methodicalDir1 = up;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 3:
					zoneDirectionsArray[zone].methodicalDir1 = up;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 4:
					zoneDirectionsArray[zone].methodicalDir1 = up;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = right;
					zoneDirectionsArray[zone].findDir2 = left;
					break;
				case 5:
					zoneDirectionsArray[zone].methodicalDir1 = up;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 6:
					zoneDirectionsArray[zone].methodicalDir1 = up;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 7:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = up;
					zoneDirectionsArray[zone].findDir1 = down;
					break;
				case 8:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = up;
					zoneDirectionsArray[zone].findDir2 = down;
					break;
				case 9:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = down;
					zoneDirectionsArray[zone].findDir1 = up;
					break;
				case 10:
					zoneDirectionsArray[zone].methodicalDir1 = down;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 11:
					zoneDirectionsArray[zone].methodicalDir1 = down;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 12:
					zoneDirectionsArray[zone].methodicalDir1 = down;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = right;
					zoneDirectionsArray[zone].findDir2 = left;
					break;
				case 13:
					zoneDirectionsArray[zone].methodicalDir1 = down;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 14:
					zoneDirectionsArray[zone].methodicalDir1 = down;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 15:
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = down;
					zoneDirectionsArray[zone].findDir1 = up;
					break;
			}
		}
	}
	
	// Check if ghost is in a portal
	private boolean inPortal()
	{
		return ((x < 160 || x > 480) && y == 320) ? true : false;
	}
	
	// Check if ghost is at a portal entry
	private boolean atPortalEntry(int portal)
	{
		switch(portal)
		{
			// Left portal entry
			case left:  return (x == 160 && y == 320) ? true : false; 
			
			// Right portal entry
			case right: return (x == 480 && y == 320) ? true : false;
		}
		
		return false;
	}
	
	// Update ghost's distance to pacman
	private void updateDistanceToPacman()
	{
		deltaX = x - Game.pacman.x; // X axis distance
		deltaY = y - Game.pacman.y; // Y axis distance
	}
	
	// Check if pacman is within detection range
	private boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	
	// Check if ghost is in spawn box
	private boolean inSpawnBox()
	{
		if(!canLeaveBox && currentDir == up)
		{
			generateNextDirection();
		}
		
		return ((x < 368 && x > 272) && (y < 336 && y > 304)) ? true : false;
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
	}
	
	private void moveRandomly()
	{
		// Update ghost's distance to pacman
		updateDistanceToPacman();
		
		// In cool down period from methodical movement
		if(coolDown == true)
		{
			// Increase cool down time
			coolDownTime++;
			
			// Cool down time has reached the target time 
			if(coolDownTime == coolDownTargetTime)
			{
				// Clear cool down flag
				coolDown = false;
				
				// Reset cool down timer 
				coolDownTime = 0;
			}
		}
		// No longer in cool down period from methodical movement
		else if(coolDown == false)
		{
			// Ghost not vulnerable, not in spawn box and pacman within detection range
			if(pacmanIsClose() && !isVulnerable && !inSpawnBox())
			{
				// Switch movement type to methodical
				movementType = methodicalMovement;
			}
		}
		
		// Ghost can move in the next defined direction
		if(canMove(nextDir))
		{
			// Move in the next defined direction
			move(nextDir);
		}
		// Ghost can move in the current defined direction
		else if(canMove(currentDir))
		{
			// Continue moving in the current defined direction
			move(currentDir);
			
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
		if(Energizer.isActive == true || inSpawnBox())
		{
			// Change movement type to random
			movementType = randomMovement;
		}

		// Update pacman's zone relative to the ghost
		updatePacmanZone();
		
		// Ghost can move in the first methodical direction
		if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir1))
		{
			// Move in the first methodical direction
			move(zoneDirectionsArray[pacmanZone].methodicalDir1);
		}
		// Ghost can move in the second methodical direction
		else if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir2))
		{
			// Move in the second methodical direction
			move(zoneDirectionsArray[pacmanZone].methodicalDir2);
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
			coolDown 	 = true;			// Activate cool down period for methodical movement
			movementType = randomMovement;	// Return to random movement
		}
	}

	private void findingPath()
	{
		// Ghost can move in first methodical direction
		if(canMove(zoneDirectionsArray[pacmanZone].methodicalDir1))
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
				if(canMove(zoneDirectionsArray[pacmanZone].findDir1))
				{
					// Move in first find path direction
					move(zoneDirectionsArray[pacmanZone].findDir1);
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
				// Move in second find path direction (which is known not to be blocked)
				move(zoneDirectionsArray[pacmanZone].findDir2);
			}
		}
	}

	
	// Manage ghost movement
	private void selectMovementType()
	{
		switch(movementType)
		{
			case randomMovement: 	 moveRandomly();     break;	// Move in a random fashion
			case methodicalMovement: moveMethodically(); break;	// Move in a methodical fashion (chase pacman)
			case findingPath: 	 	 findingPath();      break;	// Find path to pacman when stuck
		}			
	}
	
	private void managePortalCrossing()
	{		
		// Ghost is crossing left portal
		if(inPortal() && currentDir == left)
		{
			// Set portal crossing status flag
			portalCrossingStatus = crossingLeftPortal;
			
			// Keep moving left
			move(left);
			
			// Ghost ready to cross the left portal
			if(x == 0 && y == 320)								
			{	
				// Spawn ghost on the other side of the map (ghost crossed portal)
				Game.ghostArray[ghostID] = new Ghost(ghostID, portalCrossingStatus, isVulnerable, true);
			}
			
			// Ghost arrived at right portal entry
			if(atPortalEntry(right))
			{
				// Left portal crossed successfully. Not crossing portal anymore.
				portalCrossingStatus = notCrossingPortal;
			}
		}
		// Ghost is crossing right portal
		else if(inPortal() && currentDir == right)
		{
			// Set portal crossing status flag
			portalCrossingStatus = crossingRightPortal;
			
			// Keep moving right
			move(right);
			
			// Ghost ready to cross the right portal
			if(x == 640 && y == 320)
			{
				// Spawn ghost on the other side of the map (ghost crossed portal)
				Game.ghostArray[ghostID] = new Ghost(ghostID, portalCrossingStatus, isVulnerable, true);
			}
			
			// Arrived at left portal entry
			if(atPortalEntry(left))
			{
				// Right portal crossed successfully. Not crossing portal anymore.
				portalCrossingStatus = notCrossingPortal;
			}
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
			if(isFlashing)
			{
				flashGhost(g);
			}
			else if(!isFlashing)
			{
				stayBlue(g);
			}
		}
	}
	
	private void spawnBoxManager()
	{
		if(inSpawnBox())
		{
			if(inBoxTime == inBoxTargetTime)
			{
				canLeaveBox = true;
			}
			
			inBoxTime++;
		}
	}
	
	public void tick()
	{		
		spawnBoxManager();
		managePortalCrossing();
		
		if(portalCrossingStatus == notCrossingPortal)
		{
			selectMovementType();
		}
		
		animation();
	}
	
	private void move(int dir)
	{
		switch(dir)
		{
			case right: x+=spd; currentDir = right; break;
			case left: 	x-=spd; currentDir = left;  break;
			case up: 	if(canLeaveBox){y-=spd;}; currentDir = up; break;
			case down: 	y+=spd; currentDir = down;  break;
		}
	}
	
	private boolean canMove(int dir)
	{
		int nextx = 0, nexty = 0;
		
		switch(dir)
		{
			case right:	nextx = x+spd; nexty = y; break;
			case left:	nextx = x-spd; nexty = y; break;
			case up:	nextx = x; nexty = y-spd; break;
			case down:	if(x == 320 && y == 256) {return false;}			// Prevent ghosts from reentering spawn box
						nextx = x; nexty = y+spd; break;
		}
		
		Rectangle bounds = new Rectangle(nextx, nexty, width, height);
		
		for(int xx = 0; xx < Level.tiles.length; xx++)
		{
			for(int yy = 0; yy < Level.tiles[0].length; yy++)
			{
				// If not null, we have intercepted a map wall
				if(Level.tiles[xx][yy] != null)								
				{
					// Intercepted a map wall. Can no longer move in this direction
					if(bounds.intersects(Level.tiles[xx][yy]))				
					{
						// Cannot move
						return false;								
					}
				}
			}
		}	
		
		// Can move
		return true;
	}
}