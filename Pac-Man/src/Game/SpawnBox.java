package Game;


public class SpawnBox 
{
	public static int minimumTimeToBeSpentInSpawnBoxInSeconds = 60*3; 
	
	SpawnBox()
	{
		
	}
	
	public static boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 304)) ? true : false;
	}
	
	public static boolean canLeaveSpawnBox(int timeSpentInSpawnBoxInSeconds)
	{
		return (timeSpentInSpawnBoxInSeconds == minimumTimeToBeSpentInSpawnBoxInSeconds) ? true : false;
	}
}
