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
	
	private int spd = 2;
	
	private Random randomGen;
	
	// Type of movement variables
	private int movementType;
	
	private final int randomMovement = 0;
	private final int methodicalMovement  = 1;
	private final int findingPath	 = 2;
	
	private boolean findDir1Blocked = false;
	
	public static int flashTime  = 60*5;
	
	public static final int right = 0; 
	public static final int left  = 1;
	public static final int up 	  = 2;
	public static final int down  = 3;
	
	private int nextDir 	= -1;
	private int currentDir = -1;
	
	private int methodicalTime			= 0;
	private int methodicalTargetTime; 
	private boolean coolDown		= false;
	private int coolDownTime    	= 0;
	private int coolDownTargetTime	= 60*3; 
	private int timeImage 			= 0;		
	private int targetTimeImage 	= 4;
	
	private Zone ZonesArray[] = new Zone[16];
	
	private int deltaX;
	private int deltaY;
	private int detectionRange;
	
	private int portalCrossingStatus;
	
	// Spawn variables
	public static final int notCrossingPortal   = 0;
	public static final int crossingLeftPortal  = 1;
	public static final int crossingRightPortal = 2;
	
	private int imageIndexEnemy = 0;
	
	public boolean isVulnerable      = false;
	public static boolean isFlashing = false;
	
	public static int flashAnimationTime       = 0;
	public static int flashAnimationTargetTime = 20;
	
	public int ghostID;
	private int pacmanZone = 1;
	
	class Zone
	{	
		int methodicalDir1;
		int methodicalDir2;
		int findDir1;
		int findDir2;
	}
	
	// Constructor
	public Ghost(int ID, int portalStatus, boolean vulnerabilityStatus)
	{		
		// Initialise zones
		initZones();
		
		ghostID  	         = ID;                  // Update ghost ID
		movementType         = randomMovement; 		// Set default ghost movement type
		isVulnerable         = vulnerabilityStatus; // Update vulnerability status
		portalCrossingStatus = portalStatus;		// Update portal crossing status
		
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
		nextDir = randomGen.nextInt(4);
	}
	
	private void spawnGhost(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}
	
	private void initZones()
	{	
		for(int zone = 0; zone < ZonesArray.length; zone++)
		{
			ZonesArray[zone] = new Zone();
			
			switch(zone)
			{
			case 0: 
				ZonesArray[zone].methodicalDir1 = left;
				ZonesArray[zone].methodicalDir2 = up;
				ZonesArray[zone].findDir1  = down;
				
				break;
			case 1:
				ZonesArray[zone].methodicalDir1 = up;
				ZonesArray[zone].methodicalDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 2:
				ZonesArray[zone].methodicalDir1 = up;
				ZonesArray[zone].methodicalDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 3:
				ZonesArray[zone].methodicalDir1 = up;
				ZonesArray[zone].methodicalDir2 = -1;
				ZonesArray[zone].findDir1  = right;
				ZonesArray[zone].findDir2  = left;
				
				break;
			case 4:
				ZonesArray[zone].methodicalDir1 = up;
				ZonesArray[zone].methodicalDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 5:
				ZonesArray[zone].methodicalDir1 = up;
				ZonesArray[zone].methodicalDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 6:
				ZonesArray[zone].methodicalDir1 = right;
				ZonesArray[zone].methodicalDir2 = up;
				ZonesArray[zone].findDir1  = down;
				
				break;
			case 7:
				ZonesArray[zone].methodicalDir1 = left;
				ZonesArray[zone].methodicalDir2 = -1;
				ZonesArray[zone].findDir1  = down;
				ZonesArray[zone].findDir2  = up;
				
				break;
			case 8:
				ZonesArray[zone].methodicalDir1 = right;
				ZonesArray[zone].methodicalDir2 = -1;
				ZonesArray[zone].findDir1  = up;
				ZonesArray[zone].findDir2  = down;
				
				break;
			case 9:
				ZonesArray[zone].methodicalDir1 = left;
				ZonesArray[zone].methodicalDir2 = down;
				ZonesArray[zone].findDir1  = up;
				
				break;
			case 10:
				ZonesArray[zone].methodicalDir1 = down;
				ZonesArray[zone].methodicalDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 11:
				ZonesArray[zone].methodicalDir1 = down;
				ZonesArray[zone].methodicalDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 12:
				ZonesArray[zone].methodicalDir1 = down;
				ZonesArray[zone].methodicalDir2 = -1;
				ZonesArray[zone].findDir1  = right;
				ZonesArray[zone].findDir2  = left;
				
				break;
			case 13:
				ZonesArray[zone].methodicalDir1 = down;
				ZonesArray[zone].methodicalDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 14:
				ZonesArray[zone].methodicalDir1 = down;
				ZonesArray[zone].methodicalDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 15:
				ZonesArray[zone].methodicalDir1 = right;
				ZonesArray[zone].methodicalDir2 = down;
				ZonesArray[zone].findDir1  = up;
				
				break;
			}
		}
	}
	
	// Check if ghost is in a portal
	private boolean inPortal()
	{
		return ((x < 160 || x > 480) && y == 320) ? true : false;
	}
	
	private boolean atPortalEntry(int portal)
	{
		switch(portal)
		{
			case left:  return (x == 160 && y == 320) ? true : false;
				
			case right: return (x == 480 && y == 320) ? true : false;
		}
		
		return false;
	}
	
	private void updateDistanceToPacman()
	{
		deltaX = x - Game.pacman.x;
		deltaY = y - Game.pacman.y;
	}
	
	private boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	
	// Check if ghost is in spawn box
	private boolean inSpawnBox()
	{
		return ((x < 368 && x > 272) && (y < 336 && y > 304)) ? true : false;
	}

	private void updatePacmanZone()
	{	
		if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
		{
			pacmanZone = 1;
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX == deltaY)					
		{
			pacmanZone = 2;
		}
		else if(deltaX > 0 && deltaY > 0 && deltaY > deltaX)						
		{
			pacmanZone = 3;
		}
		else if(deltaX == 0 && deltaY > 0)									
		{
			pacmanZone = 4;
		}
		else if(deltaX < 0 && deltaY > 0 && deltaY > -deltaX)						
		{
			pacmanZone = 5;
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX == deltaY)					
		{
			pacmanZone = 6;
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX > deltaY)						
		{
			pacmanZone = 7;
		}
		else if(deltaX > 0 && deltaY == 0)										
		{
			pacmanZone = 8;
		}
		else if(deltaX < 0 && deltaY == 0)										
		{
			pacmanZone = 9;
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			pacmanZone = 10;
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			pacmanZone = 11;
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			pacmanZone = 12;
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			pacmanZone = 13;
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			pacmanZone = 14;
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			pacmanZone = 15;
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			pacmanZone = 16;
		}
	}
	
	private void moveRandomly()
	{
		// Update ghost's distance to pacman
		updateDistanceToPacman();
		
		if(coolDown == true)
		{
			coolDownTime++;
			
			if(coolDownTime == coolDownTargetTime)
			{
				coolDown = false;
				coolDownTime = 0;
			}
		}
		else if(coolDown == false)
		{
			if(pacmanIsClose() && isVulnerable == false && !inSpawnBox())
			{
				movementType = methodicalMovement;
			}
		}
		
		if(canMove(nextDir))
		{
			move(nextDir);
		}
		else if(canMove(currentDir))
		{
			move(currentDir);
			return;
		}
		
		nextDir = randomGen.nextInt(4);
	}
	
	private void moveMethodically()
	{
		// Update ghost's distance to pacman
		updateDistanceToPacman();
		
		// Check if energizer is active or ghost is in the spawn box
		if(Energizer.isActive == true || inSpawnBox())
		{
			// Change movement type to random
			movementType = randomMovement;
		}

		// Update pacman's zone relative to the ghost
		updatePacmanZone();
		
		// Check if ghost can move in the first methodical direction
		if(canMove(ZonesArray[pacmanZone-1].methodicalDir1))
		{
			// Move in the first methodical direction
			move(ZonesArray[pacmanZone-1].methodicalDir1);
		}
		// Check if ghost can move in the second methodical direction
		else if(canMove(ZonesArray[pacmanZone-1].methodicalDir2))
		{
			// Move in the second methodical direction
			move(ZonesArray[pacmanZone-1].methodicalDir2);
		}
		// Cannot move in either methodical direction
		else
		{
			// Ghost is stuck, need to change movement type to find path back
			movementType = findingPath;
		}
		
		// Increase time that ghost has been moving methodically
		methodicalTime++;								
		
		// Check if we have reached the target time for methodical movement
		if(methodicalTime == methodicalTargetTime) 				
		{			
			methodicalTime    = 0;			    // Reset counter for methodical movement
			coolDown 	 = true;			// Activate cool down period for methodical movement
			movementType = randomMovement;	// Return to random movement
		}
	}

	private void findingPath()
	{
		if(canMove(ZonesArray[pacmanZone-1].methodicalDir1))
		{
			findDir1Blocked = false;
			movementType = methodicalMovement;
		}
		else
		{
			if(findDir1Blocked == false)
			{
				if(canMove(ZonesArray[pacmanZone-1].findDir1))
				{
					move(ZonesArray[pacmanZone-1].findDir1);
				}
				else
				{
					findDir1Blocked = true;
				}
			}
			else if(findDir1Blocked == true)
			{
				move(ZonesArray[pacmanZone-1].findDir2);
			}
		}
	}

	
	// Manage ghost movement
	private void selectMovementType()
	{
		switch(movementType)
		{
			case randomMovement: moveRandomly(); break;	// Move in a random fashion
			case methodicalMovement:  moveMethodically();  break;	// Chase pacman
			case findingPath: 	 findingPath();  break;	// Find path to pacman when stuck
		}			
	}
	
	private void managePortalCrossing()
	{
		// Check if ghost is crossing left portal
		if(inPortal() && currentDir == left)
		{
			// Set portal crossing status flag
			portalCrossingStatus = crossingLeftPortal;
			
			// Keep moving left
			move(left);
			
			// Check if ready to cross the left portal
			if(x == 0 && y == 320)								
			{	
				// Spawn ghost on the other side of the map (ghost crossed portal)
				Game.ghostArray[ghostID] = new Ghost(ghostID, portalCrossingStatus, isVulnerable);
			}
			
			// Check if arrived at right portal entry
			if(atPortalEntry(right))
			{
				// Left portal crossed successfully. Not crossing portal anymore.
				portalCrossingStatus = notCrossingPortal;
			}
		}
		// Check if ghost is crossing right portal
		else if(inPortal() && currentDir == right)
		{
			// Set portal crossing status flag
			portalCrossingStatus = crossingRightPortal;
			
			// Keep moving right
			move(right);
			
			// Check if ready to cross the right portal
			if(x == 640 && y == 320)
			{
				// Spawn ghost on the other side of the map (ghost crossed portal)
				Game.ghostArray[ghostID] = new Ghost(ghostID, portalCrossingStatus, isVulnerable);
			}
			
			// Check if arrived at left portal entry
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
	
	private void look(int where, Graphics g)
	{
		switch(where)
		{
			case right: 
				
				switch(this.ghostID)
				{
					case 0: g.drawImage(Texture.blinkyLookRight[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookRight[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookRight[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookRight[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case left:
				
				switch(ghostID)
				{
					case 0: g.drawImage(Texture.blinkyLookLeft[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookLeft[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookLeft[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookLeft[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case up:
				
				switch(ghostID)
				{
					case 0: g.drawImage(Texture.blinkyLookUp[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookUp[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookUp[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookUp[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case down:
				
				switch(ghostID)
				{
					case 0:	g.drawImage(Texture.blinkyLookDown[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookDown[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookDown[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookDown[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
		}
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
	
	public void tick()
	{		
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
			case up: 	y-=spd; currentDir = up;    break;
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
 