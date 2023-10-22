package Game;

import java.awt.Rectangle;

public class Character extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int movingRight = 0;
	public static final int movingLeft = 1;
	public static final int movingUpwards = 2;
	public static final int movingDownwards = 3;
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	public static int leftPortalX = 0;
	public static int leftPortalY = 320;
	public static int rightPortalX = 640;
	public static int rightPortalY = 320;
	
	public static int speed = 2;
	
	public int currentDir;
	public static int nextDir;
	
	public static final int notCrossingPortal = 0;
	public static final int crossingPortalFromLeftSide = 1;
	public static final int crossingPortalFromRightSide = 2;
	
	public Character()
	{
		
	}
	
	public static boolean isInPortal(Rectangle character)
	{
		return ((character.x < 160 || character.x > 480) && character.y == 320) ? true : false;
	}
	
	public static boolean isCrossingPortalFromLeftSide(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Character.movingLeft) ? true : false;
	}
	
	public static boolean isCrossingPortalFromRightSide(Ghost ghost)
	{
		return (isInPortal(ghost) && ghost.currentDir == Character.movingRight) ? true : false;
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
	
	public static boolean canMove(int direction, Character character)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case movingRight: nextx = character.x + speed; nexty = character.y; break;
			case movingLeft:	nextx = character.x - speed; nexty = character.y; break;
			case movingUpwards: 	nextx = character.x; nexty = character.y - speed; break;
			case movingDownwards: 	if(character.x == 320 && character.y == 256) {return false;}
						nextx = character.x; nexty = character.y + speed; break;
		}
		
		Rectangle bounds = new Rectangle();

		bounds.x = nextx;
		bounds.y = nexty;
		bounds.width = character.width;
		bounds.height = character.height;
		
		for(int xx = 0; xx < Wall.wallMatrix.length; xx++)
		{
			for(int yy = 0; yy < Wall.wallMatrix[0].length; yy++)
			{
				if(Wall.wallMatrix[xx][yy] != null)								
				{
					if(bounds.intersects(Wall.wallMatrix[xx][yy]))						
					{
						return false;								
					}
				}
			}
		}
		return true;
	}
	
	public static void moveGivenCharacterInGivenDirection(Character character, int direction)
	{
		if(canMove(direction, character))
		{
			switch(direction)
			{
				case movingRight: character.x+=speed; break;//character.currentDir = right; break;
				case movingLeft: 	character.x-=speed; break;//character.currentDir = left;  break;
				case movingUpwards: 	character.y-=speed; break;//character.currentDir = up; break;
				case movingDownwards: 	character.y+=speed; break;//character.currentDir = down;  break;
			}
			return;
		}

		
		// Continue moving in last direction until direction change is possible
		if(direction == movingRight || direction == movingLeft)
		{
			if(character.currentDir == movingUpwards && canMove(movingUpwards, character))
			{
				character.y -= speed;
			}
			if(character.currentDir == movingDownwards && canMove(movingDownwards, character))
			{
				character.y += speed;
			}
		}
		else if(direction == movingUpwards || direction == movingDownwards)
		{
			if(character.currentDir == movingLeft && canMove(movingLeft, character))
			{
				character.x -= speed;
			}
			if(character.currentDir == movingRight && canMove(movingRight, character))
			{
				character.x += speed;
			}
		}
	}
}