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
	
	public Ghost(int ID, int movementStatus, int portalStatus, boolean vulnerabilityStatus)
	{		
		loadZoneDirectionsArray();
		
		ghostID = ID;                  			
		movementType = movementStatus; 			
		isVulnerable = vulnerabilityStatus; 	
		portalCrossingStatus = portalStatus;	
		
		switch(portalCrossingStatus)
		{
			case Character.notCrossingPortal:
				spawnGhost(spawnBoxX, spawnBoxY);		
				break;
			case Character.crossingPortalFromLeftSide:
				spawnGhost(portalRightSideCrossingPointXCoordinate, portalYCoordinate);	
				currentDir = left;									
				break;
			case Character.crossingPortalFromRightSide:
				spawnGhost(portalLeftSideCrossingPointXCoordinate, portalYCoordinate);		
				currentDir = right;									
				break;
		}

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
	
	private void loadZoneDirectionsArray()
	{	
		for(int zone = 0; zone < zoneDirectionsArray.length; zone++)
		{
			zoneDirectionsArray[zone] = new zoneDirections();
			
			switch(zone)
			{
				case 0:
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = downwards;
					zoneDirectionsArray[zone].findDir2 = upwards;
					break;
				case 1:
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = upwards;
					zoneDirectionsArray[zone].findDir1 = downwards;
					break;
				case 2:
					zoneDirectionsArray[zone].methodicalDir1 = upwards;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 3:
					zoneDirectionsArray[zone].methodicalDir1 = upwards;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 4:
					zoneDirectionsArray[zone].methodicalDir1 = upwards;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = right;
					zoneDirectionsArray[zone].findDir2 = left;
					break;
				case 5:
					zoneDirectionsArray[zone].methodicalDir1 = upwards;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 6:
					zoneDirectionsArray[zone].methodicalDir1 = upwards;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 7:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = upwards;
					zoneDirectionsArray[zone].findDir1 = downwards;
					break;
				case 8:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = upwards;
					zoneDirectionsArray[zone].findDir2 = downwards;
					break;
				case 9:
					zoneDirectionsArray[zone].methodicalDir1 = right;
					zoneDirectionsArray[zone].methodicalDir2 = downwards;
					zoneDirectionsArray[zone].findDir1 = upwards;
					break;
				case 10:
					zoneDirectionsArray[zone].methodicalDir1 = downwards;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 11:
					zoneDirectionsArray[zone].methodicalDir1 = downwards;
					zoneDirectionsArray[zone].methodicalDir2 = right;
					zoneDirectionsArray[zone].findDir1 = left;
					break;
				case 12:
					zoneDirectionsArray[zone].methodicalDir1 = downwards;
					zoneDirectionsArray[zone].methodicalDir2 = -1;
					zoneDirectionsArray[zone].findDir1 = right;
					zoneDirectionsArray[zone].findDir2 = left;
					break;
				case 13:
					zoneDirectionsArray[zone].methodicalDir1 = downwards;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 14:
					zoneDirectionsArray[zone].methodicalDir1 = downwards;
					zoneDirectionsArray[zone].methodicalDir2 = left;
					zoneDirectionsArray[zone].findDir1 = right;
					break;
				case 15:
					zoneDirectionsArray[zone].methodicalDir1 = left;
					zoneDirectionsArray[zone].methodicalDir2 = downwards;
					zoneDirectionsArray[zone].findDir1 = upwards;
					break;
			}
		}
	}

	private void updateDistanceToPacman()
	{
		deltaX = x - Pacman.pacman.x; 
		deltaY = y - Pacman.pacman.y;
	}
	
	private boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	
	private void updatePacmanZone()
	{	
		if(deltaX > 0 && deltaY == 0)										
		{
			pacmanZone = 0;	
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
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
		else if(deltaX < 0 && deltaY == 0)										
		{
			pacmanZone = 8;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			pacmanZone = 9;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			pacmanZone = 10;									
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			pacmanZone = 11;									
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			pacmanZone = 12;									
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			pacmanZone = 13;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			pacmanZone = 14;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			pacmanZone = 15;									
		}
	}
	
	private void generateNextDirection()
	{
		nextDir = randomGen.nextInt(4);
	}
	
	private void moveRandomly()
	{
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
			if(pacmanIsClose() && !isVulnerable && !isInSpawnBox(this))
			{
				movementType = methodicalMovement;
			}
		}
		
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
			
			move(this, nextDir);
		}
		else if(canMove(this, currentDir))
		{
			move(this, currentDir);
			
			return;
		}
		
		generateNextDirection();
	}
	
	private void moveMethodically()
	{
		updateDistanceToPacman();
		
		if(Energizer.isActive == true || isInSpawnBox(this))
		{
			movementType = randomMovement;
		}

		updatePacmanZone();
		
		if(canMove(this, zoneDirectionsArray[pacmanZone].methodicalDir1))
		{
			currentDir = zoneDirectionsArray[pacmanZone].methodicalDir1;
			
			move(this, zoneDirectionsArray[pacmanZone].methodicalDir1);
		}
		else if(canMove(this, zoneDirectionsArray[pacmanZone].methodicalDir2))
		{
			currentDir = zoneDirectionsArray[pacmanZone].methodicalDir2;
			
			move(this, zoneDirectionsArray[pacmanZone].methodicalDir2);
		}
		else
		{
			movementType = findingPath;
		}
		
		methodicalTime++;								
		
		if(methodicalTime == methodicalTargetTime) 				
		{			
			methodicalTime = 0;				
			coolDown 	 = true;			
			movementType = randomMovement;	
		}
	}

	private void findingPath()
	{
		if(canMove(this, zoneDirectionsArray[pacmanZone].methodicalDir1))
		{
			findDir1Blocked = false;
			
			movementType = methodicalMovement;
		}
		else
		{
			if(findDir1Blocked == false)
			{
				if(canMove(this, zoneDirectionsArray[pacmanZone].findDir1))
				{
					currentDir = zoneDirectionsArray[pacmanZone].findDir1;
					
					move(this, zoneDirectionsArray[pacmanZone].findDir1);
				}
				else
				{
					findDir1Blocked = true;
				}
			}
			else if(findDir1Blocked == true)
			{
				currentDir = zoneDirectionsArray[pacmanZone].findDir2;
				
				move(this, zoneDirectionsArray[pacmanZone].findDir2);
			}
		}
	}

	private void selectGhostMovementType()
	{
		switch(movementType)
		{
			case randomMovement: moveRandomly(); break;
			case methodicalMovement: moveMethodically(); break;
			case findingPath: findingPath(); break;	
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
	
	public void tick()
	{		
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
}