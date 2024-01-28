package character;

import java.awt.Graphics;
import java.util.Random;

import main.GamePanel;

public class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	private Random randomGen;
	
	public int ghostID;
	public int movementType;
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	public boolean findDir1Blocked = false;
	public boolean isFindingPath = false;
	
	public int nextDir = 0;
	public int currentDir = 0;
	
	public double timeMovingMethodicallyInSeconds = 0.0;
	public double targetTimeMovingMethodicallyInSeconds = 12.0; 
	public boolean isCoolingDown = false;
	public double coolDownTimeInSeconds = 0.0;
	public double coolDownTargetTimeInSeconds = 5.0; 
	
	public int deltaX;
	public int deltaY;
	
	public int pacmanZone;
	
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public Ghost(int ID, int cD, int x, int y, int movementStatus, GamePanel gp)
	{	
		super(gp);
		
		ghostID = ID;                  			
		movementType = movementStatus; 		 	
		currentDir = cD;
		setBounds(x, y, gp.tileSize, gp.tileSize);
		
		randomGen = new Random();
		nextDir = randomGen.nextInt(4);
	}
	
	public void tick()
	{	

	}

	public boolean isCrossingPortal(int i, Ghost ghost)
	{
		if((gp.ghostArray[i].x < 160 || gp.ghostArray[i].x > 480) && gp.ghostArray[i].y == 320)
		{
			if(gp.ghostArray[i].currentDir == left)
			{
				if(gp.ghostArray[i].x == 0 && gp.ghostArray[i].y == 320)
				{
					if(gp.ghostArray[i] instanceof HostileGhost)
					{
						gp.ghostArray[i] = new HostileGhost(i, left, 640, 320, gp.ghostArray[i].movementType, gp);
					}
					else if(gp.ghostArray[i] instanceof FlashingGhost)
					{
						gp.ghostArray[i] = new FlashingGhost(i, left, 640, 320, gp.ghostArray[i].movementType, gp);
					}
					else if(gp.ghostArray[i] instanceof VulnerableGhost)
					{
						gp.ghostArray[i] = new VulnerableGhost(i, left, 640, 320, gp.ghostArray[i].movementType, gp);
					}
				}
				
				move(gp.ghostArray[i], left);
				
				if(gp.ghostArray[i].x == 480 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			else if(gp.ghostArray[i].currentDir == right)
			{
				if(gp.ghostArray[i].x == 640 && gp.ghostArray[i].y == 320)
				{
					if(gp.ghostArray[i] instanceof HostileGhost)
					{
						gp.ghostArray[i] = new HostileGhost(i, right, 0, 320, gp.ghostArray[i].movementType, gp);
					}
					else if(gp.ghostArray[i] instanceof FlashingGhost)
					{
						gp.ghostArray[i] = new FlashingGhost(i, right, 0, 320, gp.ghostArray[i].movementType, gp);
					}
					else if(gp.ghostArray[i] instanceof VulnerableGhost)
					{
						gp.ghostArray[i] = new VulnerableGhost(i, right, 0, 320, gp.ghostArray[i].movementType, gp);
					}
				}
				
				move(gp.ghostArray[i], right);
				
				if(gp.ghostArray[i].x == 160 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
		
	public void moveRandomly(Ghost ghost)
	{
		if(isCoolingDown == true)
		{
			coolDownTimeInSeconds += gp.secondsPerTick;
			
			if(coolDownTimeInSeconds >= coolDownTargetTimeInSeconds)
			{
				isCoolingDown = false;
				coolDownTimeInSeconds = 0;
			}
		}
		else if(isCoolingDown == false)
		{
			if(gp.pathFinder.pacmanIsClose(deltaX, deltaY) && ghost instanceof HostileGhost && !isInSpawnBox(this))
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
		
		nextDir = randomGen.nextInt(4);
	}

	public void moveMethodically()
	{
		if(!isFindingPath)
		{
			pacmanZone = gp.pathFinder.updatePacmanZone(deltaX, deltaY);
			
			if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][0]))
			{
				currentDir = gp.pathFinder.pathFinderArray[pacmanZone][0];
				
				move(this, gp.pathFinder.pathFinderArray[pacmanZone][0]);
			}
			else if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][1]))
			{
				currentDir = gp.pathFinder.pathFinderArray[pacmanZone][1];
				
				move(this, gp.pathFinder.pathFinderArray[pacmanZone][1]);
			}
			else
			{
				isFindingPath = true;
			}
		}
		else if(isFindingPath)
		{
			if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][0]))
			{
				findDir1Blocked = false;
				isFindingPath = false;
			}
			else
			{
				if(findDir1Blocked == false)
				{
					if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][2]))
					{
						currentDir = gp.pathFinder.pathFinderArray[pacmanZone][2];
						
						move(this, gp.pathFinder.pathFinderArray[pacmanZone][2]);
					}
					else
					{
						findDir1Blocked = true;
					}
				}
				else if(findDir1Blocked == true)
				{
					currentDir = gp.pathFinder.pathFinderArray[pacmanZone][3];
					
					move(this, gp.pathFinder.pathFinderArray[pacmanZone][3]);
				}
			}
		}
		
		timeMovingMethodicallyInSeconds += gp.secondsPerTick;	
		
		if(timeMovingMethodicallyInSeconds >= targetTimeMovingMethodicallyInSeconds) 				
		{			
			findDir1Blocked = false;
			isFindingPath = false;
			timeMovingMethodicallyInSeconds = 0;				
			isCoolingDown = true;			
			movementType = randomMovement;	
		}
	}
	
	public static boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 256)) ? true : false;
	}

	public void render(Graphics g)
	{

	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
}