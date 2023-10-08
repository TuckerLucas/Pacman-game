package Game;

import java.awt.Rectangle;

public class SpawnBox 
{
	SpawnBox()
	{
		
	}
	
	public static boolean isInSpawnBox(Rectangle character)
	{
		return ((character.x < 368 && character.x > 272) && (character.y < 336 && character.y > 304)) ? true : false;
	}
	
	public static boolean canLeaveSpawnBox(Rectangle character)
	{
		return (isInSpawnBox(character) && Ghost.canLeaveSpawnBox) ? true : false;
	}
}
