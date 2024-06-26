package entity;

import java.awt.Graphics;
import java.util.Random;

import main.GamePanel;

public class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	private Random randomGen = new Random();
	
	public int ghostID;
	public String movementType;
	
	public boolean findDir1Blocked = false;
	public boolean isFindingPath = false;
	
	public String nextDir;
	public String currentDir;
	
	public double timeMovingMethodicallyInSeconds = 0.0;
	public double targetTimeMovingMethodicallyInSeconds = 12.0; 
	public boolean isCoolingDown = false;
	public double coolDownTimeInSeconds = 0.0;
	public double coolDownTargetTimeInSeconds = 5.0;
	
	public boolean isEaten = false;
	public double timeSpentInBoxInSeconds = 0.0;
	public double targetTimeSpentInBoxInSeconds = 5.0;
	
	public int deltaX;
	public int deltaY;
	
	public int pacmanZone;
	
	public Ghost(GamePanel gp, int i)
	{
		super(gp);
		
		// Need to ensure we're not instantiating ghosts for the first time
		// Because if we are then gp.ghostArray[i] is null leading to problems
		if(gp.ghostArray[i] != null)
		{
			movementType = gp.ghostArray[i].movementType;
			currentDir = gp.ghostArray[i].currentDir;
			ghostID = i;
			this.x = gp.ghostArray[i].x;
			this.y = gp.ghostArray[i].y;
		}
		
		solidArea.x = x + 12;
		solidArea.y = y + 12;
		solidArea.width = 10;
		solidArea.height = 10;
		
		generateNextDir(2);
	}
	
	public void tick()
	{	

	}

	public boolean isCrossingPortal(int i, Ghost ghost)
	{
		if((gp.ghostArray[i].x < 160 || gp.ghostArray[i].x > 480) && gp.ghostArray[i].y == 320)
		{
			if(gp.ghostArray[i].currentDir == "left")
			{
				if(gp.ghostArray[i].x == 0 && gp.ghostArray[i].y == 320)
				{
					gp.ghostArray[i].x = 640;
					gp.ghostArray[i].y = 320;
				}
				
				manageMovement(gp.ghostArray[i], "left");
				
				if(gp.ghostArray[i].x == 480 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			else if(gp.ghostArray[i].currentDir == "right")
			{
				if(gp.ghostArray[i].x == 640 && gp.ghostArray[i].y == 320)
				{
					gp.ghostArray[i].x = 0;
					gp.ghostArray[i].y = 320;
				}
				
				manageMovement(gp.ghostArray[i], "right");
				
				if(gp.ghostArray[i].x == 160 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public void generateNextDir(int n)
	{
		int temp;
		
		temp = randomGen.nextInt(n);
		
		switch(temp)
		{
			case 0: nextDir = "right"; break;
			case 1: nextDir = "left"; break;
			case 2: nextDir = "up"; break;
			case 3: nextDir = "down"; break;
		}
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
			if(gp.pathFinder.pacmanIsClose(deltaX, deltaY) && ghost instanceof Ghost_Hostile && !isInSpawnBox(ghost))
			{
				movementType = "methodical";
			}
		}
		
		if(canMove(ghost, nextDir))
		{
			currentDir = nextDir;
			manageMovement(ghost, nextDir);
		}
		else if(canMove(ghost, currentDir))
		{
			manageMovement(ghost, currentDir);
			return;
		}
		
		generateNextDir(4);
	}

	public void moveMethodically(Ghost ghost)
	{
		if(!isFindingPath)
		{
			pacmanZone = gp.pathFinder.updatePacmanZone(deltaX, deltaY);
			
			if(canMove(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][0]))
			{
				currentDir = gp.pathFinder.pathFinderArray[ghostID][pacmanZone][0];
				
				manageMovement(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][0]);
			}
			else if(canMove(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][1]))
			{
				currentDir = gp.pathFinder.pathFinderArray[ghostID][pacmanZone][1];
				
				manageMovement(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][1]);
			}
			else
			{
				isFindingPath = true;
			}
		}
		else if(isFindingPath)
		{
			if(canMove(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][0]))
			{
				findDir1Blocked = false;
				isFindingPath = false;
			}
			else
			{
				if(findDir1Blocked == false)
				{
					if(canMove(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][2]))
					{
						currentDir = gp.pathFinder.pathFinderArray[ghostID][pacmanZone][2];
						
						manageMovement(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][2]);
					}
					else
					{
						findDir1Blocked = true;
					}
				}
				else if(findDir1Blocked == true)
				{
					currentDir = gp.pathFinder.pathFinderArray[ghostID][pacmanZone][3];
					
					manageMovement(ghost, gp.pathFinder.pathFinderArray[ghostID][pacmanZone][3]);
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
			movementType = "random";	
		}
	}
	
	public void moveSideToSide(Ghost ghost)
	{
		if(canMove(ghost, nextDir))
		{
			currentDir = nextDir;
			manageMovement(ghost, nextDir);
			return;
		}
		else if(canMove(ghost, currentDir))
		{
			manageMovement(ghost, currentDir);
			return;
		}
		
		generateNextDir(2);
	}
	
	public boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 256)) ? true : false;
	}

	public void render(Graphics g)
	{

	}
	
	public String getCurrentDirection()
	{
		return currentDir;
	}
}