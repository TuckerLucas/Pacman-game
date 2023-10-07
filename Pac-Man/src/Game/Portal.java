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
	
	public static boolean isGhostCrossingLeftPortal(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Movement.left) ? true : false;
	}
	
	public static boolean isGhostCrossingRightPortal(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Movement.right) ? true : false;
	}

	
	public static boolean atPortalEntry(int portal, Rectangle character)
	{
		switch(portal)
		{
			// Left portal entry
			case Movement.left:  return (character.x == 160 && character.y == 320) ? true : false; 
			
			// Right portal entry
			case Movement.right: return (character.x == 480 && character.y == 320) ? true : false;
		}
		
		return false;
	}
	
	public static boolean isAboutToCrossLeftPortal(Rectangle character)
	{
		return (character.x == 0 && character.y == 320) ? true : false;
	}
	
	public static boolean isAboutToCrossRightPortal(Rectangle character)
	{
		return (character.x == 640 && character.y == 320) ? true : false;
	}
}
