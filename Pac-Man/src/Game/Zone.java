package Game;

public class Zone 
{
	private static int deltaX;
	private static int deltaY;
	private static int detectionRange = 80;
	
	public Zone()
	{
		
	}
	
	public static void updateMethodicalDirections(Ghost ghost, int zone)
	{	
		switch(zone)
		{
			case 0:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.downwards;
				ghost.findDir2 = Character.upwards;
				break;
			case 1:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = Character.upwards;
				ghost.findDir1 = Character.downwards;
				break;
			case 2:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 3:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 4:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.right;
				ghost.findDir2 = Character.left;
				break;
			case 5:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 6:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 7:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = Character.upwards;
				ghost.findDir1 = Character.downwards;
				break;
			case 8:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.upwards;
				ghost.findDir2 = Character.downwards;
				break;
			case 9:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = Character.downwards;
				ghost.findDir1 = Character.upwards;
				break;
			case 10:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 11:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 12:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.right;
				ghost.findDir2 = Character.left;
				break;
			case 13:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 14:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 15:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = Character.downwards;
				ghost.findDir1 = Character.upwards;
				break;
		}
	}	
	
	public static void updateDistanceToPacman(int x, int y)
	{
		deltaX = x - Pacman.pacman.x; 
		deltaY = y - Pacman.pacman.y;
	}
	
	public static boolean pacmanIsClose()
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
	
	public static int updatePacmanZone()
	{	
		if(deltaX > 0 && deltaY == 0)										
		{
			return 0;	
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
		{
			return 1;										
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX == deltaY)					
		{
			return 2;										
		}
		else if(deltaX > 0 && deltaY > 0 && deltaY > deltaX)						
		{
			return 3;										
		}
		else if(deltaX == 0 && deltaY > 0)									
		{
			return 4;		
		}
		else if(deltaX < 0 && deltaY > 0 && deltaY > -deltaX)						
		{
			return 5;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX == deltaY)					
		{
			return 6;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX > deltaY)						
		{
			return 7;										
		}
		else if(deltaX < 0 && deltaY == 0)										
		{
			return 8;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			return 9;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			return 10;									
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			return 11;									
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			return 12;									
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			return 13;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			return 14;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			return 15;									
		}
		
		return 0;
	}
}
