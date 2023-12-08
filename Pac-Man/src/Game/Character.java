package Game;

import java.awt.Rectangle;

public abstract class Character extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int stopped = -1;
	public static final int right = 0;
	public static final int left = 1;
	public static final int upwards = 2;
	public static final int downwards = 3;
	
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
		if(isCrossingPortalFromGivenSide(character, left))
		{
			managePortalCrossingFromGivenSide(character, left);
		}
		else if(isCrossingPortalFromGivenSide(character, right))
		{
			managePortalCrossingFromGivenSide(character, right);
		}
	}
	
	public static boolean isCrossingPortalFromGivenSide(Character character, int side)
	{
		return (isInPortalCorridor(character) && character.getCurrentDirection() == side) ? true : false;
	}
	
	private static void managePortalCrossingFromGivenSide(Character character, int side)
	{
		character.setCurrentDirection(side);
			
		if(isAtPortalCrossingPoint(character, side))
		{
			//makeCharacterCrossPortal(character, side);
		}
	}
	
	private static boolean isInPortalCorridor(Rectangle character)
	{
		return ((character.x < portalLeftSideEntryXCoordinate || character.x > portalRightSideEntryXCoordinate) 
				&& character.y == portalYCoordinate) ? true : false;
	}

	private static boolean isAtPortalCrossingPoint(Character character, int side)
	{
		if(side == right)
		{
			return (character.x == portalRightSideCrossingPointXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		else if(side == left)
		{
			return (character.x == portalLeftSideCrossingPointXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		
		return false;
	}

	/*
	private static boolean isAtPortalEntry(Character character, int side)
	{
		if(side == left)
		{
			return (character.x == portalLeftSideEntryXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		else if(side == right)
		{
			return (character.x == portalRightSideEntryXCoordinate && character.y == portalYCoordinate) ? true : false;
		}
		
		return false;
	}*/
	
	/*
	private static void makeCharacterCrossPortal(Character character, int side)
	{
		if(character instanceof Pacman)
		{
			Pacman.pacman = new AlivePacman(side, character.getNextDirection());
		}
		else if(character instanceof HostileGhost)
		{
			if(side == left)
			{
				Ghost.ghostArray[character.getID()] = new HostileGhost(Ghost.ghostArray[character.getID()], 640, 320);
			}
			else if(side == right)
			{
				Ghost.ghostArray[character.getID()] = new HostileGhost(Ghost.ghostArray[character.getID()], 0, 320);
			}
		}
		else if(character instanceof FlashingGhost)
		{
			if(side == left)
			{
				Ghost.ghostArray[character.getID()] = new FlashingGhost(Ghost.ghostArray[character.getID()], 640, 320);
			}
			else if(side == right)
			{
				Ghost.ghostArray[character.getID()] = new FlashingGhost(Ghost.ghostArray[character.getID()], 0, 320);
			}
		}
		else if(character instanceof VulnerableGhost)
		{
			if(side == left)
			{
				Ghost.ghostArray[character.getID()] = new VulnerableGhost(Ghost.ghostArray[character.getID()], 640, 320);
			}
			else if(side == right)
			{
				Ghost.ghostArray[character.getID()] = new VulnerableGhost(Ghost.ghostArray[character.getID()], 0, 320);
			}
		}	
	}*/
	
	public static void move(Character character, int direction)
	{
		if(Game.gameStatus == Game.lifeLost)
		{
			return;
		}
		
		if(!canMove(character, direction))
		{
			keepMovingUntilCanChangeDirection(character, direction);
		}
		else
		{
			shiftCharacterInGivenDirection(character, direction);
		}
	}

	public static boolean canMove(Character character, int direction)
	{
		int nextX = 0, nextY = 0;
		
		switch(direction)
		{
			case right: nextX = character.x + pixelsTravelledPerTick; nextY = character.y; break;
			case left: nextX = character.x - pixelsTravelledPerTick; nextY = character.y; break;
			case upwards: nextX = character.x; nextY = character.y - pixelsTravelledPerTick; break;
			case downwards: if(character.x == 320 && character.y == 256) {return false;}
							nextX = character.x; nextY = character.y + pixelsTravelledPerTick; break;
		}
		
		Rectangle bounds = new Rectangle();

		bounds.x = nextX;
		bounds.y = nextY;
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

	private static void keepMovingUntilCanChangeDirection(Character character, int direction)
	{
		if(direction == right || direction == left)
		{
			if(character.getCurrentDirection() == upwards && canMove(character, upwards))
			{
				shiftCharacterInGivenDirection(character, upwards);
			}
			if(character.getCurrentDirection() == downwards && canMove(character, downwards))
			{
				shiftCharacterInGivenDirection(character, downwards);
			}
		}
		else if(direction == upwards || direction == downwards)
		{
			if(character.getCurrentDirection() == left && canMove(character, left))
			{
				shiftCharacterInGivenDirection(character, left);
			}
			if(character.getCurrentDirection() == right && canMove(character, right))
			{
				shiftCharacterInGivenDirection(character, right);
			}
		}
	}
	
	private static void shiftCharacterInGivenDirection(Character character, int direction)
	{
		switch(direction)
		{
			case right: character.x += pixelsTravelledPerTick; break;
			case left: character.x -= pixelsTravelledPerTick; break;
			case upwards: character.y -= pixelsTravelledPerTick; break;
			case downwards: character.y += pixelsTravelledPerTick; break;
		}
	}
	
	abstract int getCurrentDirection();
	abstract void setCurrentDirection(int dir);
	abstract int getNextDirection();
	abstract int getID();
	abstract boolean getVulnerabilityStatus();
}