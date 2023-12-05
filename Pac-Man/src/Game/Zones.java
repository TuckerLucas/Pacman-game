package Game;

public class Zones 
{
	/*
	static class zoneDirections
	{	
		int methodicalDir1;
		int methodicalDir2;
		int findDir1;
		int findDir2;
	}
	
	private int deltaX;
	private int deltaY;
	private int detectionRange = 80;
	
	private int pacmanZone = 1;
	
	private double timeMovingMethodicallyInSeconds = 0.0;
	private double targetTimeMovingMethodicallyInSeconds = 12.0; 
	
	private static zoneDirections zoneDirectionsArray[] = new zoneDirections[16];
	
	private boolean findDir1Blocked = false;
	private boolean isFindingPath = false;
	
	
	public static void loadZoneDirectionsArray()
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
	
	private void moveMethodically()
	{
		if(this instanceof VulnerableGhost)
		{
			timeMovingMethodicallyInSeconds = 0;
			findDir1Blocked = false;
			isFindingPath = false;
			movementType = randomMovement;
			return;
		}
		
		if(!isFindingPath)
		{
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
				isFindingPath = true;
			}
		}
		else if(isFindingPath)
		{
			if(canMove(this, zoneDirectionsArray[pacmanZone].methodicalDir1))
			{
				findDir1Blocked = false;
				isFindingPath = false;
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
		
		timeMovingMethodicallyInSeconds += Game.secondsPerTick;	
		
		if(timeMovingMethodicallyInSeconds >= targetTimeMovingMethodicallyInSeconds) 				
		{			
			timeMovingMethodicallyInSeconds = 0;	
			movementType = randomMovement;	
		}
	}
	
	
	protected void updateDistanceToPacman()
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
	
	protected void selectGhostMovementType()
	{
		switch(movementType)
		{
			case randomMovement: moveRandomly(); break;
			case methodicalMovement: moveMethodically(); break;
		}			
	}*/
}
