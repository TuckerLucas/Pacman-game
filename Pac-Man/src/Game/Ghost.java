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
	private final int smartMovement  = 1;
	private final int findingPath	 = 2;
	
	private boolean findDir1Blocked = false;
	
	public static int flashTime  = 60*5;
	
	public static final int right = 0; 
	public static final int left  = 1;
	public static final int up 	  = 2;
	public static final int down  = 3;
	
	private int nextDir 	= -1;
	private int currentDir = -1;
	
	private int smartTime			= 0;
	private int smartTargetTime; 
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
		int smartDir1;
		int smartDir2;
		int findDir1;
		int findDir2;
	}
	
	// Constructor
	public Ghost(int ID, int portalStatus, boolean vulnerabilityStatus)
	{			
		initZones();
		
		// Set default ghost movement type
		movementType = randomMovement;
		
		portalCrossingStatus = portalStatus;
		ghostID  		= ID;
		isVulnerable 	= vulnerabilityStatus;
		
		switch(portalCrossingStatus)
		{
			case notCrossingPortal:
				spawnGhost(Game.centerBoxX,Game.centerBoxY);
				break;
			case crossingLeftPortal:
				spawnGhost(Game.rightPortalX,Game.rightPortalY);
				move(left);
				break;
			case crossingRightPortal:
				spawnGhost(Game.leftPortalX,Game.leftPortalY);
				move(right);
				break;
		}

		switch(Game.difficulty)
		{
			case 1:
				detectionRange = 80;
				smartTargetTime = 60 * 10;
				break;
			case 2:
				detectionRange = 100;
				smartTargetTime = 60 * 15;
				break;
			case 3:
				detectionRange = 120;
				smartTargetTime = 60 * 20;
				break;
			case 4:
				detectionRange = 150;
				smartTargetTime = 60 * 25;
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
				ZonesArray[zone].smartDir1 = left;
				ZonesArray[zone].smartDir2 = up;
				ZonesArray[zone].findDir1  = down;
				
				break;
			case 1:
				ZonesArray[zone].smartDir1 = up;
				ZonesArray[zone].smartDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 2:
				ZonesArray[zone].smartDir1 = up;
				ZonesArray[zone].smartDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 3:
				ZonesArray[zone].smartDir1 = up;
				ZonesArray[zone].smartDir2 = -1;
				ZonesArray[zone].findDir1  = right;
				ZonesArray[zone].findDir2  = left;
				
				break;
			case 4:
				ZonesArray[zone].smartDir1 = up;
				ZonesArray[zone].smartDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 5:
				ZonesArray[zone].smartDir1 = up;
				ZonesArray[zone].smartDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 6:
				ZonesArray[zone].smartDir1 = right;
				ZonesArray[zone].smartDir2 = up;
				ZonesArray[zone].findDir1  = down;
				
				break;
			case 7:
				ZonesArray[zone].smartDir1 = left;
				ZonesArray[zone].smartDir2 = -1;
				ZonesArray[zone].findDir1  = down;
				ZonesArray[zone].findDir2  = up;
				
				break;
			case 8:
				ZonesArray[zone].smartDir1 = right;
				ZonesArray[zone].smartDir2 = -1;
				ZonesArray[zone].findDir1  = up;
				ZonesArray[zone].findDir2  = down;
				
				break;
			case 9:
				ZonesArray[zone].smartDir1 = left;
				ZonesArray[zone].smartDir2 = down;
				ZonesArray[zone].findDir1  = up;
				
				break;
			case 10:
				ZonesArray[zone].smartDir1 = down;
				ZonesArray[zone].smartDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 11:
				ZonesArray[zone].smartDir1 = down;
				ZonesArray[zone].smartDir2 = left;
				ZonesArray[zone].findDir1  = right;
				
				break;
			case 12:
				ZonesArray[zone].smartDir1 = down;
				ZonesArray[zone].smartDir2 = -1;
				ZonesArray[zone].findDir1  = right;
				ZonesArray[zone].findDir2  = left;
				
				break;
			case 13:
				ZonesArray[zone].smartDir1 = down;
				ZonesArray[zone].smartDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 14:
				ZonesArray[zone].smartDir1 = down;
				ZonesArray[zone].smartDir2 = right;
				ZonesArray[zone].findDir1  = left;
				
				break;
			case 15:
				ZonesArray[zone].smartDir1 = right;
				ZonesArray[zone].smartDir2 = down;
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
	
	private void moveRandomly()
	{
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
				movementType = smartMovement;
			}
		}
		
		if(canMove(nextDir))			
		{
			move(nextDir);
			nextDir = randomGen.nextInt(4);
			return;
		}
		else
		{
			for(int direction = right; direction <= down; direction++)
			{	
				if(currentDir == direction && canMove(direction))
				{
					move(direction);
					return;
				}
			}
		}
		
		nextDir = randomGen.nextInt(4);
		
		return;
	}
	
	// Check if ghost is in spawn box
	private boolean inSpawnBox()
	{
		return ((x < 368 && x > 272) && (y < 336 && y > 304)) ? true : false;
	}

	private void pacmanZone()
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
	
	private void moveSmartly()
	{
		if(Energizer.isActive == true || inSpawnBox())
		{
			movementType = randomMovement;
		}

		pacmanZone();
		
		// Move to pacman's zone
		if(canMove(ZonesArray[pacmanZone-1].smartDir1))
		{
			move(ZonesArray[pacmanZone-1].smartDir1);
		}
		else if(canMove(ZonesArray[pacmanZone-1].smartDir2))
		{
			move(ZonesArray[pacmanZone-1].smartDir2);
		}
		else
		{
			movementType = findingPath;
		}
		
		smartTime++;								
		
		if(smartTime == smartTargetTime) 				
		{
			coolDown = true;
			movementType = randomMovement;							
			smartTime = 0;						
		}
	}

	private void findingPath()
	{
		if(canMove(ZonesArray[pacmanZone-1].smartDir1))
		{
			findDir1Blocked = false;
			movementType = smartMovement;
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
	private void ghostMovement()
	{
		updateDistanceToPacman();
		
		switch(movementType)
		{
			case randomMovement: 	moveRandomly(); break;	// Move in a random fashion
			case smartMovement: 	moveSmartly();  break;	// Chase pacman
			case findingPath: 	    findingPath();  break;	// Find path to pacman when stuck
		}			
	}
	
	private void portalCrossing()
	{
		// Ghost in right portal moving right
		if(inPortal() && currentDir == right)
		{
			// Ensure ghost crosses portal to the other side of the map
			portalCrossingStatus = crossingRightPortal;
		}
		// Ghost in left portal moving left
		else if(inPortal() && currentDir == left)
		{
			// Ensure ghost crosses portal to the other side of the map
			portalCrossingStatus = crossingLeftPortal;
		}
					
		if(portalCrossingStatus == crossingLeftPortal)
		{
			currentDir = left;
			x-=spd;
			
			if(atPortalEntry(right))
			{
				portalCrossingStatus = notCrossingPortal;
			}
		}
		else if(portalCrossingStatus == crossingRightPortal)
		{
			currentDir = right;
			x+=spd;
			
			if(atPortalEntry(left))
			{
				portalCrossingStatus = notCrossingPortal;
			}
		}
		
		if(x == 0 && y == 320)								
		{
			currentDir = left;
			portalCrossingStatus = crossingLeftPortal;
			
			switch(ghostID)
			{
				case 0: Game.ghostArray[0] = new Ghost(0, portalCrossingStatus, isVulnerable); break;
				case 1: Game.ghostArray[1] = new Ghost(1, portalCrossingStatus, isVulnerable); break;
				case 2: Game.ghostArray[2] = new Ghost(2, portalCrossingStatus, isVulnerable); break;
				case 3: Game.ghostArray[3] = new Ghost(3, portalCrossingStatus, isVulnerable); break;
			}
		}
		else if(x == 640 && y == 320)
		{
			currentDir = right;
			portalCrossingStatus = crossingRightPortal;

			Game.ghostArray[ghostID] = new Ghost(ghostID, portalCrossingStatus, isVulnerable);
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
		portalCrossing();
		
		if(portalCrossingStatus == notCrossingPortal)
		{
			ghostMovement();
		}
		
		animation();
	}
	
	private void move(int dir)
	{
		switch(dir)
		{
			case right: x+=spd; currentDir = right; break;
			case left: 	x-=spd; currentDir = left; break;
			case up: 	y-=spd; currentDir = up; break;
			case down: 	y+=spd; currentDir = down; break;
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
						return false;								
					}
				}
			}
		}	
		return true;
	}
}
 