package Game;

import java.awt.Rectangle;

public abstract class Character extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int movingRight = 0;
	public static final int movingLeft = 1;
	public static final int movingUpwards = 2;
	public static final int movingDownwards = 3;
	
	public static final int notCrossingPortal = 0;
	public static final int crossingPortalFromLeftSide = 1;
	public static final int crossingPortalFromRightSide = 2;
	
	public static int leftPortalEntryXCoordinate = 0;
	public static int leftPortalEntryYCoordinate = 320;
	public static int rightPortalEntryCoordinate = 640;
	public static int rightPortalEntryYCoordinate = 320;
	
	public static int portalCrossingPointLeftSideXCoordinate = 0;
	public static int portalCrossingPointLeftSideYCoordinate = 320;
	public static int portalCrossingPointRightSideXCoordinate = 640;
	public static int portalCrossingPointRightSideYCoordinate = 320;
	
	public static int pixelsTravelledPerTick = 2;
	
	public Character()
	{
		
	}
	
	public static boolean isInPortal(Rectangle character)
	{
		return ((character.x < 160 || character.x > 480) && character.y == 320) ? true : false;
	}
	
	public static boolean isCrossingPortalFromLeftSide(Character character)
	{
		return (isInPortal(character) && character.getCurrentDirection() == Character.movingLeft) ? true : false;
	}
	
	public static boolean isCrossingPortalFromRightSide(Character character)
	{
		return (isInPortal(character) && character.getCurrentDirection() == Character.movingRight) ? true : false;
	}

	public static boolean atLeftPortalEntry(Character character)
	{
		return (character.x == 160 && character.y == 320) ? true : false;
	}
	
	public static boolean atRightPortalEntry(Character character)
	{
		return (character.x == 480 && character.y == 320) ? true : false;
	}
	
	public static boolean isAboutToCrossPortalFromLeftSide(Character character)
	{
		return (character.x == 0 && character.y == 320) ? true : false;
	}
	
	public static boolean isAboutToCrossPortalFromRightSide(Character character)
	{
		return (character.x == 640 && character.y == 320) ? true : false;
	}
	
	
	protected static void portalEvents(Character character)
	{		
		if(character instanceof Ghost)
		{
			if(isCrossingPortalFromLeftSide(character))
			{
				character.setPortalCrossingStatus(crossingPortalFromLeftSide);
				character.setCurrentDirection(movingLeft); 
				moveGivenCharacterInGivenDirection(character, movingLeft);
				
				if(Character.isAboutToCrossPortalFromLeftSide(character))								
				{	
					Ghost.ghostArray[character.getID()] = new Ghost(character.getID(), character.getMovementType(), character.getPortalCrossingStatus(), character.getVulnerabilityStatus());
				}
				
				if(atRightPortalEntry(character))
				{
					character.setPortalCrossingStatus(notCrossingPortal);
				}
			}
			else if(isCrossingPortalFromRightSide(character))
			{
				character.setPortalCrossingStatus(crossingPortalFromRightSide);
				character.setCurrentDirection(movingRight);
				moveGivenCharacterInGivenDirection(character, movingRight);
				
				if(Character.isAboutToCrossPortalFromRightSide(character))
				{
					Ghost.ghostArray[character.getID()] = new Ghost(character.getID(), character.getMovementType(), character.getPortalCrossingStatus(), character.getVulnerabilityStatus());
				}
				
				if(Character.atLeftPortalEntry(character))
				{
					character.setPortalCrossingStatus(notCrossingPortal);
				}
			}
		}
		else if(character instanceof Pacman)
		{
			if(Character.isAboutToCrossPortalFromLeftSide(character))	
			{
				Pacman.pacman = new Pacman(crossingPortalFromLeftSide, character.getNextDirection());
			}
			
			if(Character.isAboutToCrossPortalFromRightSide(character))			
			{
				Pacman.pacman = new Pacman(Character.crossingPortalFromRightSide, character.getNextDirection());		
			}
		}
	}
	
	public static boolean canMove(int direction, Character character)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case movingRight: nextx = character.x + pixelsTravelledPerTick; nexty = character.y; break;
			case movingLeft: nextx = character.x - pixelsTravelledPerTick; nexty = character.y; break;
			case movingUpwards: nextx = character.x; nexty = character.y - pixelsTravelledPerTick; break;
			case movingDownwards: if(character.x == 320 && character.y == 256) {return false;}
								  nextx = character.x; nexty = character.y + pixelsTravelledPerTick; break;
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
				case movingRight: character.x += pixelsTravelledPerTick; break;
				case movingLeft: character.x -= pixelsTravelledPerTick; break;
				case movingUpwards: character.y -= pixelsTravelledPerTick; break;
				case movingDownwards: character.y += pixelsTravelledPerTick; break;
			}
			
			return;
		}

		// Continue moving in last direction until direction change is possible
		if(direction == movingRight || direction == movingLeft)
		{
			if(character.getCurrentDirection() == movingUpwards && canMove(movingUpwards, character))
			{
				character.y -= pixelsTravelledPerTick;
			}
			if(character.getCurrentDirection() == movingDownwards && canMove(movingDownwards, character))
			{
				character.y += pixelsTravelledPerTick;
			}
		}
		else if(direction == movingUpwards || direction == movingDownwards)
		{
			if(character.getCurrentDirection() == movingLeft && canMove(movingLeft, character))
			{
				character.x -= pixelsTravelledPerTick;
			}
			if(character.getCurrentDirection() == movingRight && canMove(movingRight, character))
			{
				character.x += pixelsTravelledPerTick;
			}
		}
	}
	
	abstract int getCurrentDirection();
	abstract void setCurrentDirection(int dir);
	abstract int getPortalCrossingStatus();
	abstract void setPortalCrossingStatus(int portalStatus);
	abstract int getNextDirection();
	abstract int getID();
	abstract int getMovementType();
	abstract boolean getVulnerabilityStatus();
}