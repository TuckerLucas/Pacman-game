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
	private final int findingPath    = 2;
	
	public static int flashTime  = 60*5;
	
	public static final int right = 0; 
	public static final int left  = 1;
	public static final int up 	  = 2;
	public static final int down  = 3;
	
	private int dir 	= -1;
	private int lastDir = -1;
	private int findDir = -1;
	
	private int timeImage 			= 0;		
	private int targetTimeImage 	= 4;
	
	private Zone ZonesArray[] = new Zone[16];

	private int deltaX;
	private int deltaY;
	
	private int detectionRange = 80;
	
	private int spawn;
	
	// Spawn variables
	public static final int spawnInBox = 0;
	public static final int spawnRight = 1;
	public static final int spawnLeft  = 2;
	
	private int imageIndexEnemy = 0;
	
	public boolean isVulnerable      = false;
	public static boolean isFlashing = false;
	
	public static int flashAnimationTime       = 0;
	public static int flashAnimationTargetTime = 20;
	
	public int enemyID;
	private int pacmanZone = 1;
	class Zone
	{
		int smartDir1;
		int smartDir2;
		
		int findDir1;
		int findDir2;
	}
	
	// Constructor
	public Ghost(int ID, int spawnPoint, boolean IV)
	{			
		// Set default ghost movement type
		movementType = randomMovement;
		
		spawn 			= spawnPoint;
		enemyID  		= ID;
		isVulnerable 	= IV;
		
		initZones();
		
		switch(spawn)
		{
			case spawnInBox:
				spawnGhost(Game.centerBoxX,Game.centerBoxY);
				break;
			case spawnLeft:
				spawnGhost(Game.rightPortalX,Game.rightPortalY);
				move(left);
				break;
			case spawnRight:
				spawnGhost(Game.leftPortalX,Game.leftPortalY);
				move(right);
				break;
		}
		
		randomGen = new Random();
		dir = randomGen.nextInt(4);
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
					ZonesArray[zone].findDir1  = up;
					ZonesArray[zone].findDir2  = down;
					
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
	
	private void spawnGhost(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}

	
	private boolean randomMovement(int direction)
	{
		if(canMove(direction))			
		{
			move(direction);
			dir = randomGen.nextInt(4);
			return true;
		}
		
		return false;
	}
	
	private void updatePacmanLocation()
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
		if(pacmanIsClose() && !inSpawnBox() && !isVulnerable)
		{
			movementType = smartMovement;
			return;
		}
		
		if(spawn != spawnInBox)
		{
			return;
		}
		
		if(randomMovement(dir))
		{
			return;
		}
		
		for(int direction = right; direction <= down; direction++)
		{	
			if(lastDir == direction && canMove(direction))
			{
				move(direction);
				return;
			}
		}
		
		dir = randomGen.nextInt(4); 
		
		return;
	}
	
	// Check if ghost is in the spawn box
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
		if(isVulnerable == true || inSpawnBox())
		{
			movementType = randomMovement;
		}
		
		if(spawn != spawnInBox)
		{
			return;
		}
		
		pacmanZone();
		
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
	}
	
	private void findPath()
	{	
		if(!canMove(ZonesArray[pacmanZone-1].smartDir1))
		{
			if(findDir == -1)
			{
				if(canMove(ZonesArray[pacmanZone-1].findDir1))
				{
					findDir = ZonesArray[pacmanZone-1].findDir1;
				}
				else
				{
					findDir = ZonesArray[pacmanZone-1].findDir2;
				}
			}
			
			move(findDir);
		}
		else
		{
			findDir = -1;
			movementType = smartMovement;
		}
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

	// Manage ghost movement
	private void ghostMovement()
	{
		switch(movementType)
		{
			case randomMovement: moveRandomly(); break;
			case smartMovement:  moveSmartly();  break;
			case findingPath:	 findPath();	 break;
		}
	}
	
	private void portalCrossing()
	{
		if(x == 0 && y == 320)								
		{
			lastDir = left;
			spawn   = spawnLeft;
			
			Game.ghostArray[enemyID] = new Ghost(enemyID, spawn, isVulnerable);
		}
		else if(x == 640 && y == 320)
		{
			lastDir = right;
			spawn   = spawnRight;

			Game.ghostArray[enemyID] = new Ghost(enemyID, spawn, isVulnerable);
		}
		
		if(spawn == spawnLeft)
		{
			lastDir = left;
			
			x -= spd;
			
			if(atPortalEntry(right))
			{
				spawn = spawnInBox;
			}
		}
		else if(spawn == spawnRight)
		{
			lastDir = right;
			
			x += spd;
			
			if(atPortalEntry(left))
			{
				spawn = spawnInBox;
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
				
				switch(this.enemyID)
				{
					case 0: g.drawImage(Texture.blinkyLookRight[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookRight[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookRight[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookRight[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case left:
				
				switch(enemyID)
				{
					case 0: g.drawImage(Texture.blinkyLookLeft[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookLeft[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookLeft[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookLeft[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case up:
				
				switch(enemyID)
				{
					case 0: g.drawImage(Texture.blinkyLookUp[imageIndexEnemy], x, y, width, height, null); break;
					case 1: g.drawImage(Texture.inkyLookUp[imageIndexEnemy], x, y, width, height, null);   break;
					case 2: g.drawImage(Texture.pinkyLookUp[imageIndexEnemy], x, y, width, height, null);  break;
					case 3: g.drawImage(Texture.clydeLookUp[imageIndexEnemy], x, y, width, height, null);  break;
				}
				
				break;
				
			case down:
				
				switch(enemyID)
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
			look(lastDir, g);
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
		updatePacmanLocation();
		ghostMovement();
		portalCrossing();
		animation();
	}
	
	private void setDirection(int dir)
	{
		switch(dir)
		{
			case right: x+=spd; lastDir = right; break;
			case left: 	x-=spd; lastDir = left;  break;
			case up: 	y-=spd; lastDir = up;    break;
			case down: 	y+=spd; lastDir = down;  break;
		}
	}
	
	private void move(int dir)
	{
		if(canMove(dir))
		{
			setDirection(dir);
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
			case down:	if(x == 320 && y == 256) {return false;}	// Prevent ghosts from reentering spawn box
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
 