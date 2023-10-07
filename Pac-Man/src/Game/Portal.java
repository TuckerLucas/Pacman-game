package Game;

import java.awt.Rectangle;

public class Portal 
{
	public Portal()
	{
		
	}
	
	public static boolean isInPortal(Rectangle character)
	{
		return ((character.x < 160 || character.x > 480) && character.y == 320) ? true : false;
	}
	
	public static boolean isCrossingPortalFromLeftSide(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Movement.left) ? true : false;
	}
	
	public static boolean isCrossingPortalFromRightSide(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Movement.right) ? true : false;
	}

	public static boolean atLeftPortalEntry(Rectangle character)
	{
		return (character.x == 160 && character.y == 320) ? true : false;
	}
	
	public static boolean atRightPortalEntry(Rectangle character)
	{
		return (character.x == 480 && character.y == 320) ? true : false;
	}
	
	public static boolean isAboutToCrossPortalFromLeftSide(Rectangle character)
	{
		return (character.x == 0 && character.y == 320) ? true : false;
	}
	
	public static boolean isAboutToCrossPortalFromRightSide(Rectangle character)
	{
		return (character.x == 640 && character.y == 320) ? true : false;
	}
}
