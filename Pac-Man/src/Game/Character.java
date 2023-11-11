package Game;

import java.awt.Rectangle;

public abstract class Character extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int movingRight = 0;
	public static final int movingLeft = 1;
	public static final int movingUpwards = 2;
	public static final int movingDownwards = 3;
	
	public static final int notCrossingPortal = -1;
	public static final int crossingPortalFromRightSide = 0;
	public static final int crossingPortalFromLeftSide = 1;
	
	public static int portalLeftSideCrossingPointXCoordinate = 0;
	public static int portalRightSideCrossingPointXCoordinate = 640;
	
	private static int portalLeftSideEntryXCoordinate = 160;
	private static int portalRightSideEntryXCoordinate = 480;
	
	public static int portalYCoordinate = 320;
	
	public static int pixelsTravelledPerTick = 2;
	
	public Character()
	{
		
	}
	
	protected static void portalEvents(Character character)
	{		
		if(isCrossingPortalFromGivenSide(character, movingLeft))
		{
			managePortalCrossingFromGivenSide(character, movingLeft);
		}
		else if(isCrossingPortalFromGivenSide(character, movingRight))
		{
			managePortalCrossingFromGivenSide(character, movingRight);
		}
	}
	
	public static boolean isCrossingPortalFromGivenSide(Character character, int side)
	{
		if(side == movingLeft)
		{
			return (isInPortalCorridor(character) && character.getCurrentDirection() == Character.movingLeft) ? true : false;
		}
		else if(side == movingRight)
		{
			return (isInPortalCorridor(character) && character.getCurrentDirection() == Character.movingRight) ? true : false;
		}
		
		return false;
	}
	
	private static void managePortalCrossingFromGivenSide(Character character, int side)
	{
		if(character instanceof Pacman)
		{
			Pacman.blockMovement = true;
		}
		
		character.setPortalCrossingStatus(side);
		character.setCurrentDirection(side);
		
		if(Game.gameStatus != Game.lifeLost)
		{
			moveGivenCharacterInGivenDirection(character, side);
		}
		
		if(isAtPortalCrossingPoint(character, side))
		{
			makeCharacterCrossPortal(character, side);
		}
		
		if(isAtPortalEntry(character, side ^= 1))
		{
			character.setPortalCrossingStatus(notCrossingPortal);
			
			if(character instanceof Pacman)
			{
				Pacman.blockMovement = false;
			}
		}
	}

	private static boolean isInPortalCorridor(Rectangle character)
	{
		return ((character.x < portalLeftSideEntryXCoordinate || character.x > portalRightSideEntryXCoordinate) 
				&& character.y == portalYCoordinate) ? true : false;
	}

	private static boolean isAtPortalEntry(Character character, int side)
	{
		if(side == movingLeft)
		{
			return (character.x == portalLeftSideEntryXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		else if(side == movingRight)
		{
			return (character.x == portalRightSideEntryXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		
		return false;
	}
	
	private static boolean isAtPortalCrossingPoint(Character character, int side)
	{
		if(side == movingLeft)
		{
			return (character.x == portalLeftSideCrossingPointXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		else if(side == movingRight)
		{
			return (character.x == portalRightSideCrossingPointXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		
		return false;
	}
	
	private static void makeCharacterCrossPortal(Character character, int side)
	{
		if(character instanceof Pacman)
		{
			Pacman.pacman = new Pacman(side, character.getNextDirection());
		}
		else if(character instanceof Ghost)
		{
			Ghost.ghostArray[character.getID()] = new Ghost(character.getID(), character.getMovementType(), character.getPortalCrossingStatus(), character.getVulnerabilityStatus());
		}
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
	
	abstract int getCurrentDirection();
	abstract void setCurrentDirection(int dir);
	abstract int getPortalCrossingStatus();
	abstract void setPortalCrossingStatus(int portalStatus);
	abstract int getNextDirection();
	abstract int getID();
	abstract int getMovementType();
	abstract boolean getVulnerabilityStatus();
}