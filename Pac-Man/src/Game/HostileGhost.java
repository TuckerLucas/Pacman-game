package Game;

import java.awt.Graphics;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private static int methodicalDir1;
	private static int methodicalDir2;
	private static int findDir1;
	private static int findDir2;
	
	private static int deltaX;
	private static int deltaY;
	private static int detectionRange = 90;
	
	private double timeMovingMethodicallyInSeconds = 0.0;
	private double targetTimeMovingMethodicallyInSeconds = 12.0; 
	private boolean findDir1Blocked = false;
	private boolean isFindingPath = false;
	
	private static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;		
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	public HostileGhost(int ID, int xx, int yy, int cD, int nD)
	{
		ghostID = ID;
		x = xx;
		y = yy;
		currentDir = cD;
		nextDir = nD;
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public HostileGhost(Ghost ghost, int xx, int yy)
	{
		ghostID = ghost.ghostID;
		x = xx;
		y = yy;
		currentDir = ghost.currentDir;
		nextDir = ghost.nextDir;
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public void tick()
	{
		portalEvents(this);
		updateDistanceToPacman();
		
		if(pacmanIsClose() && !isInSpawnBox(this) && !isInPortalCorridor(this))
		{
			moveMethodically();
		}
		else
		{
			moveRandomly();
		}
		
		manageAnimationTiming();
	}
	
	private static void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			
			if(frameIndex == totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
	}
	
	private boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	
	private void moveMethodically()
	{
		updateMethodicalDirections();
		
		if(!isFindingPath)
		{
			if(canMove(this, methodicalDir1))
			{
				currentDir = methodicalDir1;
				
				move(this, methodicalDir1);
			}
			else if(canMove(this, methodicalDir2))
			{
				currentDir = methodicalDir2;
				
				move(this, methodicalDir2);
			}
			else
			{
				isFindingPath = true;
			}
		}
		else if(isFindingPath)
		{
			if(canMove(this, methodicalDir1))
			{
				findDir1Blocked = false;
				isFindingPath = false;
			}
			else
			{
				if(findDir1Blocked == false)
				{
					
					if(canMove(this, findDir1))
					{
						currentDir = findDir1;
						
						move(this, findDir1);
					}
					else
					{
						findDir1Blocked = true;
					}
				}
				else if(findDir1Blocked == true)
				{
					currentDir = findDir2;
					
					move(this, findDir2);
				}
			}
		}
		
		timeMovingMethodicallyInSeconds += Game.secondsPerTick;	
		
		if(timeMovingMethodicallyInSeconds >= targetTimeMovingMethodicallyInSeconds) 				
		{			
			timeMovingMethodicallyInSeconds = 0;
		}
	}
	
	public void updateMethodicalDirections()
	{	
		if(deltaX > 0 && deltaY == 0)										
		{
			methodicalDir1 = left;
			methodicalDir2 = -1;
			findDir1 = downwards;
			findDir2 = upwards;	
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
		{
			methodicalDir1 = left;
			methodicalDir2 = upwards;
			findDir1 = downwards;									
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX == deltaY)					
		{
			methodicalDir1 = upwards;
			methodicalDir2 = left;
			findDir1 = right;										
		}
		else if(deltaX > 0 && deltaY > 0 && deltaY > deltaX)						
		{
			methodicalDir1 = upwards;
			methodicalDir2 = left;
			findDir1 = right;										
		}
		else if(deltaX == 0 && deltaY > 0)									
		{
			methodicalDir1 = upwards;
			methodicalDir2 = -1;
			findDir1 = right;
			findDir2 = left;		
		}
		else if(deltaX < 0 && deltaY > 0 && deltaY > -deltaX)						
		{
			methodicalDir1 = upwards;
			methodicalDir2 = right;
			findDir1 = left;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX == deltaY)					
		{
			methodicalDir1 = upwards;
			methodicalDir2 = right;
			findDir1 = left;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX > deltaY)						
		{
			methodicalDir1 = right;
			methodicalDir2 = upwards;
			findDir1 = downwards;										
		}
		else if(deltaX < 0 && deltaY == 0)										
		{
			methodicalDir1 = right;
			methodicalDir2 = -1;
			findDir1 = upwards;
			findDir2 = downwards;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			methodicalDir1 = right;
			methodicalDir2 = downwards;
			findDir1 = upwards;									
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			methodicalDir1 = downwards;
			methodicalDir2 = right;
			findDir1 = left;									
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			methodicalDir1 = downwards;
			methodicalDir2 = right;
			findDir1 = left;									
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			methodicalDir1 = downwards;
			methodicalDir2 = -1;
			findDir1 = right;
			findDir2 = left;									
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			methodicalDir1 = downwards;
			methodicalDir2 = left;
			findDir1 = right;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			methodicalDir1 = downwards;
			methodicalDir2 = left;
			findDir1 = right;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			methodicalDir1 = left;
			methodicalDir2 = downwards;
			findDir1 = upwards;									
		}
	}
	
	private void updateDistanceToPacman()
	{
		deltaX = x - Pacman.pacman.x; 
		deltaY = y - Pacman.pacman.y;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
	}
}
