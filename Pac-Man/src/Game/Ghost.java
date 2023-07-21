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
	
	private final int random 		= 0;
	private final int smart  		= 1;
	private final int find_path 	= 2;
	
	public static int flashTime  = 60*5;
	
	public static final int right = 0; 
	public static final int left  = 1;
	public static final int up 	  = 2;
	public static final int down  = 3;
	
	private int dir 	= -1;
	private int lastDir = -1;
	
	private int smartTime			= 0;
	private int smartTargetTime; 
	private boolean coolDown		= false;
	private int coolDownTime    	= 0;
	private int coolDownTargetTime	= 60*3; 
	private int timeImage 			= 0;		
	private int targetTimeImage 	= 4;
	
	private Zone ZonesArray[] = new Zone[16];
	
	private boolean left4 	= false;
	private boolean right4 	= false;
	private boolean left13 	= false;
	private boolean right13 = false;
	private boolean up8 	= false;
	private boolean down8 	= false;
	private boolean up9 	= false;
	private boolean down9 	= false;
	
	private int difx;
	private int dify;
	private int radius;
	
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
	
	class Zone
	{
		boolean isActive = false;
		
		int smartDir1;
		int smartDir2;
		
		int findDir1;
		int findDir2;
		
	}
	
	// Constructor
	public Ghost(int ID, int spawnPoint, boolean IV)
	{			
		initZones();
		
		// Set default ghost movement type
		movementType = random;
		
		spawn 			= spawnPoint;
		enemyID  		= ID;
		isVulnerable 	= IV;
		
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

		switch(Game.difficulty)
		{
			case 1:
				radius = 800;
				smartTargetTime = 60 * 100;
				break;
			case 2:
				radius = 100;
				smartTargetTime = 60 * 8;
				break;
			case 3:
				radius = 120;
				smartTargetTime = 60 * 12;
				break;
			case 4:
				radius = 150;
				smartTargetTime = 60 * 20;
				break;
		}
		
		randomGen = new Random();
		dir = randomGen.nextInt(4);
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
					ZonesArray[zone].findDir2  = left;
					
					break;
				case 1:
					ZonesArray[zone].smartDir1 = up;
					ZonesArray[zone].smartDir2 = left;
					ZonesArray[zone].findDir1  = right;
					ZonesArray[zone].findDir2  = up;
					
					break;
				case 2:
					ZonesArray[zone].smartDir1 = up;
					ZonesArray[zone].smartDir2 = left;
					ZonesArray[zone].findDir1  = right;
					ZonesArray[zone].findDir2  = up;
					
					break;
				case 3:
					ZonesArray[zone].smartDir1 = up;
					ZonesArray[zone].smartDir2 = -1;
					ZonesArray[zone].findDir1  = left;
					ZonesArray[zone].findDir2  = up;
					
					break;
				case 4:
					ZonesArray[zone].smartDir1 = up;
					ZonesArray[zone].smartDir2 = right;
					ZonesArray[zone].findDir1  = left;
					ZonesArray[zone].findDir2  = up;
					
					break;
				case 5:
					ZonesArray[zone].smartDir1 = up;
					ZonesArray[zone].smartDir2 = right;
					ZonesArray[zone].findDir1  = left;
					ZonesArray[zone].findDir2  = up;
					
					break;
				case 6:
					ZonesArray[zone].smartDir1 = right;
					ZonesArray[zone].smartDir2 = up;
					ZonesArray[zone].findDir1  = down;
					ZonesArray[zone].findDir2  = right;
					
					break;
				case 7:
					ZonesArray[zone].smartDir1 = left;
					ZonesArray[zone].smartDir2 = -1;
					ZonesArray[zone].findDir1  = -2;
					ZonesArray[zone].findDir2  = -2;
					
					break;
				case 8:
					ZonesArray[zone].smartDir1 = right;
					ZonesArray[zone].smartDir2 = -1;
					ZonesArray[zone].findDir1  = -2;
					ZonesArray[zone].findDir2  = -2;
					
					break;
				case 9:
					ZonesArray[zone].smartDir1 = left;
					ZonesArray[zone].smartDir2 = down;
					ZonesArray[zone].findDir1  = up;
					ZonesArray[zone].findDir2  = left;
					
					break;
				case 10:
					ZonesArray[zone].smartDir1 = down;
					ZonesArray[zone].smartDir2 = left;
					ZonesArray[zone].findDir1  = right;
					ZonesArray[zone].findDir2  = down;
					
					break;
				case 11:
					ZonesArray[zone].smartDir1 = down;
					ZonesArray[zone].smartDir2 = left;
					ZonesArray[zone].findDir1  = right;
					ZonesArray[zone].findDir2  = down;
					
					break;
				case 12:
					ZonesArray[zone].smartDir1 = down;
					ZonesArray[zone].smartDir2 = -1;
					ZonesArray[zone].findDir1  = -2;
					ZonesArray[zone].findDir2  = -2;
					
					break;
				case 13:
					ZonesArray[zone].smartDir1 = down;
					ZonesArray[zone].smartDir2 = right;
					ZonesArray[zone].findDir1  = left;
					ZonesArray[zone].findDir2  = down;
					
					break;
				case 14:
					ZonesArray[zone].smartDir1 = down;
					ZonesArray[zone].smartDir2 = right;
					ZonesArray[zone].findDir1  = left;
					ZonesArray[zone].findDir2  = down;
					
					break;
				case 15:
					ZonesArray[zone].smartDir1 = right;
					ZonesArray[zone].smartDir2 = down;
					ZonesArray[zone].findDir1  = up;
					ZonesArray[zone].findDir2  = right;
					
					break;
			}
		}
	}
	
	private void updateZone(int currentZone)
	{
		resetZones();
		
		for(int i = 0; i < ZonesArray.length; i++)
		{
			ZonesArray[currentZone-1].isActive = true;
		}
	}
	
	private void resetZones()
	{
		for(int i = 0; i < ZonesArray.length; i++)
		{
			ZonesArray[i].isActive = false;
		}
	}

	private void getToZone(int zone)
	{
		if(canMove(ZonesArray[zone-1].smartDir1))
		{
			move(ZonesArray[zone-1].smartDir1);
		}
		else if(canMove(ZonesArray[zone-1].smartDir2))
		{
			move(ZonesArray[zone-1].smartDir2);
		}
		else
		{
			movementType = find_path;
		}
	}
	
	
	private void moveToZone(int zone)
	{
		updateZone(zone);
		
		switch(zone)
		{
			case 4: 
				if(canMove(up))
				{
					move(up);
				}
				else if(canMove(left))
				{
					left4 = true;
					movementType = find_path;
				}
				else if(canMove(right))
				{
					right4 = true;
					movementType = find_path;
				}
				return;
					
			case 8: 
				if(canMove(left))
				{
					move(left);
				}
				else if(canMove(up))
				{
					up8 = true;
					movementType = find_path;
				}
				else if(canMove(down))
				{
					down8 = true;
					movementType = find_path;
				}
				return;
				
			case 9: 
				if(canMove(right))
				{
					move(right);
				}
				else if(canMove(up))
				{
					up9 = true;
					movementType = find_path;
				}
				else if(canMove(down))
				{
					down9 = true;
					movementType = find_path;
				}
				return;
					 
			case 13: 
				if(canMove(down))
				{
					move(down);
				}
				else if(canMove(left))
				{
					left13 = true;
					movementType = find_path;
				}
				else if(canMove(right))
				{
					right13 = true;
					movementType = find_path;
				}
				return;
		}
		
		getToZone(zone);
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
	
	private void moveRandomly()
	{
		difx = x - Game.pacman.x;
		dify = y - Game.pacman.y;
		
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
			if((difx < radius && difx > -radius) && (dify < radius && dify > -radius))
			{
				if(isVulnerable == false && !inBox())
				{
					movementType = smart;
				}
			}
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
		else if(spawn == spawnInBox)
		{
			switch(dir)
			{
				case right:
					
					if(randomMovement(right))
						break;
					else							
					{
						if(lastDir == left && canMove(left))
						{
							move(left);
						}
						else if(lastDir == up && canMove(up))
						{
							move(up);
						}
						else if(lastDir == down && canMove(down))
						{
							move(down);
						}
						else 
						{
							dir = randomGen.nextInt(4); 
						}
						
					}
					
					break;
					
				case left:
					
					if(randomMovement(left))
						break;
					else							
					{
						if(lastDir == right && canMove(right))
						{
							x+=spd;
						}
						else if(lastDir == up && canMove(up))
						{
							y-=spd;
						}
						else if(lastDir == down && canMove(down))
						{
							y+=spd;
						}
						else 
						{
							dir = randomGen.nextInt(4); 
						}
					}
								
					break;
					
				case up:
					
					if(randomMovement(up))
					{
						break;
					}
					else							
					{
						if(lastDir == left && canMove(left))
						{
							x-=spd;
						}
						else if(lastDir == right && canMove(right))
						{
							x+=spd;
						}
						else if(lastDir == down && canMove(down))
						{
							y+=spd;
						}
						else 
						{
							dir = randomGen.nextInt(4);
						}
					}
					
					break;
					
				case down:
					
					if(randomMovement(down))
					{
						break;
					}
					else							
					{
						if(lastDir == left && canMove(left))
						{
							x-=spd;
						}
						else if(lastDir == up && canMove(up))
						{
							y-=spd;
						}
						else if(lastDir == right && canMove(right))
						{
							x+=spd;
						}
						else 
						{
							dir = randomGen.nextInt(4); 
						}
					}	
					
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
	
	private void moveSmartly()
	{
		difx = x - Game.pacman.x;
		dify = y - Game.pacman.y;

		if(Energizer.isActive == true || inBox())
		{
			movementType = random;
		}
		
		if(spawn == spawnLeft)
		{
			lastDir = left;
			x-=spd;
			
			if(atPortalEntry(right))
			{
				spawn = spawnInBox;
			}
		}
		else if(spawn == spawnRight)
		{
			lastDir = right;
			x+=spd;
			
			if(atPortalEntry(left))
			{
				spawn = spawnInBox;
			}
		}
		else if(spawn == spawnInBox)
		{
			// Ghost in right portal moving right
			if(inPortal() && lastDir == right)
			{
				// Ensure ghost crosses portal to the other side of the map
				spawn = spawnRight;
			}
			// Ghost in left portal moving left
			else if(inPortal() && lastDir == left)
			{
				// Ensure ghost crosses portal to the other side of the map
				spawn = spawnLeft;
			}
			// Ghost not in a portal
			else
			{
				if(difx > 0 && dify > 0 && difx > dify)			//Zona 1
				{
					moveToZone(1);
				}
				else if(difx > 0 && dify > 0 && difx == dify)	//Zona 2				
				{
					moveToZone(2);
				}
				else if(difx > 0 && dify > 0 && dify > difx)	//Zona 3					
				{
					moveToZone(3);
				}
				else if(difx == 0 && dify > 0)					//Zona 4				
				{
					moveToZone(4);
				}
				else if(difx < 0 && dify > 0 && dify > -difx)	//Zona 5					
				{
					moveToZone(5);
				}
				else if(difx < 0 && dify > 0 && -difx == dify)	//Zona 6					
				{
					moveToZone(6);
				}
				else if(difx < 0 && dify > 0 && -difx > dify)	//Zona 7					
				{
					moveToZone(7);
				}
				else if(difx > 0 && dify == 0)					//Zona 8					
				{
					moveToZone(8);
				}
				else if(difx < 0 && dify == 0)					//Zona 9					
				{
					moveToZone(9);
				}
				else if(difx > 0 && dify < 0 && difx > -dify)	//Zona 10 					
				{
					moveToZone(10);
				}
				else if(difx > 0 && dify < 0 && difx == -dify)	//Zona 11 					
				{
					moveToZone(11);
				}
				else if(difx > 0 && dify < 0 && -dify > difx)	//Zona 12					
				{
					moveToZone(12);
				}
				else if(difx == 0 && dify < 0)					//Zona 13					
				{
					moveToZone(13);
				}
				else if(difx < 0 && dify < 0 && -dify > -difx)	//Zona 14					
				{
					moveToZone(14);
				}
				else if(difx < 0 && dify < 0 && -dify == -difx)	//Zona 15					
				{
					moveToZone(15);
				}
				else if(difx < 0 && dify < 0 && -difx > -dify)	//Zona 16					
				{
					moveToZone(16);
				}
			}
		}
		smartTime++;								
		
		if(smartTime == smartTargetTime) 				
		{
			coolDown = true;
			movementType = random;							
			smartTime = 0;						
		}
	}

	private void findingPath()
	{
		if(ZonesArray[0].isActive)
		{
			moveUntil(down, left);
		}
		else if(ZonesArray[1].isActive)
		{
			moveUntil(right, up);
		}
		else if(ZonesArray[2].isActive)
		{
			moveUntil(right, up);
		}
		else if(ZonesArray[3].isActive)
		{
			if(left4 == true)
			{
				if(canMove(left))
				{
					moveUntil(left, up);
				}
				else
				{
					left4 = false;
					ZonesArray[3].isActive = false;
					moveUntil(-1, -1);
				}
			}
			else if(right4 == true)
			{
				if(canMove(right))
				{
					moveUntil(right, up);
				}
				else
				{
					right4 = false;
					ZonesArray[3].isActive = false;
					moveUntil(-1, -1);
				}
			}
		}
		else if(ZonesArray[4].isActive)
		{
			moveUntil(left, up);
		}
		else if(ZonesArray[5].isActive)
		{
			moveUntil(left, up);
		}
		else if(ZonesArray[6].isActive)
		{
			moveUntil(down, right);
		}
		else if(ZonesArray[7].isActive)
		{
			if(up8 == true)
			{
				if(canMove(up))
				{
					moveUntil(up, left);
				}
				else
				{
					up8 = false;
					ZonesArray[7].isActive = false;
					moveUntil(-1,-1);
				}
			}
			else if(down8 == true)
			{
				if(canMove(down))
				{
					moveUntil(down, left);
				}
				else
				{
					down8 = false;
					ZonesArray[7].isActive = false;
					moveUntil(-1,-1);
				}
			}
		}
		else if(ZonesArray[8].isActive)
		{
			if(up9 == true)
			{
				if(canMove(up))
				{
					moveUntil(up, right);
				}
				else
				{
					up9 = false;
					ZonesArray[8].isActive = false;
					moveUntil(-1,-1);
				}
			}
			else if(down9 == true)
			{
				if(canMove(down))
				{
					moveUntil(down, right);
				}
				else
				{
					down9 = false;
					ZonesArray[8].isActive = false;
					moveUntil(-1,-1);
				}
			}
		}
		else if(ZonesArray[9].isActive)
		{
			moveUntil(up, left);
		}
		else if(ZonesArray[10].isActive)
		{
			moveUntil(right, down);
		}
		else if(ZonesArray[11].isActive)
		{
			moveUntil(right, down);
		}
		else if(ZonesArray[12].isActive)
		{
			if(left13 == true)
			{
				if(canMove(left))
				{
					moveUntil(left, down);
				}
				else
				{
					ZonesArray[12].isActive = false;
					left13 = false;
					moveUntil(-1,-1);
				}
			}
			else if(right13 == true)
			{
				if(canMove(right))
				{
					moveUntil(right, down);
				}
				else
				{
					ZonesArray[12].isActive = false;
					right13 = false;
					moveUntil(-1,-1);
				}
			}
		}
		else if(ZonesArray[13].isActive)
		{
			moveUntil(left, down);
		}
		else if(ZonesArray[14].isActive)
		{
			moveUntil(left, down);
		}
		else if(ZonesArray[15].isActive)
		{
			moveUntil(up, right);
		}
	}

	private void moveUntil(int allowed_direction, int desired_direction)
	{
		switch(allowed_direction)
		{
			case right:
				
				move(right);
				
				if(desired_direction == up && canMove(up))
				{
					right4 = false;
					movementType = smart;
				}
				else if(desired_direction == down && canMove(down))
				{
					right13 = false;
					movementType = smart;
				}
				
				break;
			
			case left: 
				
				move(left);
				
				if(desired_direction == up && canMove(up))
				{
					left4 = false;
					movementType = smart;
				}
				else if(desired_direction == down && canMove(down))
				{
					left13 = false;
					movementType = smart;
				}
				
				break;
				
			case up:
				
				move(up);
				
				if(desired_direction == right && canMove(right))
				{
					up9 = false;
					movementType = smart;
				}
				else if(desired_direction == left && canMove(left))
				{
					up8 = false;
					movementType = smart;
				}
				
				break;
				
			case down:
				
				move(down);
				
				if(desired_direction == right && canMove(right))
				{
					down9 = false;
					movementType = smart;
				}
				else if(desired_direction == left && canMove(left))
				{
					down8 = false;
					movementType = smart;
				}
				
				break;
				
			case -1:	
				
				movementType = smart; 
			
				break;
		}
	}
	
	// Check if ghost is in spawn box
	private boolean inBox()
	{
		return ((x < 385 && x > 255) && (y < 385 && y > 255)) ? true : false;
	}

	// Manage ghost movement
	private void ghostMovement()
	{
		switch(movementType)
		{
			case random: 	moveRandomly(); break;	// Move in a random fashion
			case smart: 	moveSmartly();  break;	// Chase pacman
			case find_path: findingPath();  break;	// Find path to pacman when stuck
		}			
	}
	
	private void portalCrossing()
	{
		if(x == 0 && y == 320)								
		{
			lastDir = left;
			spawn = spawnLeft;
			
			switch(enemyID)
			{
				case 0: Game.ghostArray[0] = new Ghost(0, spawn, isVulnerable); break;
				case 1: Game.ghostArray[1] = new Ghost(1, spawn, isVulnerable); break;
				case 2: Game.ghostArray[2] = new Ghost(2, spawn, isVulnerable); break;
				case 3: Game.ghostArray[3] = new Ghost(3, spawn, isVulnerable); break;
			}
		}
		else if(x == 640 && y == 320)
		{
			lastDir = right;
			spawn   = spawnRight;

			Game.ghostArray[enemyID] = new Ghost(enemyID, spawn, isVulnerable);
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
		ghostMovement();
		portalCrossing();
		animation();
	}
	
	private void setDirection(int dir)
	{
		switch(dir)
		{
			case right: x+=spd; lastDir = right; break;
			case left: 	x-=spd; lastDir = left; break;
			case up: 	y-=spd; lastDir = up; break;
			case down: 	y+=spd; lastDir = down; break;
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
 