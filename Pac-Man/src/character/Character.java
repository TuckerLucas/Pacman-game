package character;

import java.awt.Rectangle;

import main.GamePanel;

public class Character extends Rectangle
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public static final int stopped = -1;
	public static final int right = 0;
	public static final int left = 1;
	public static final int upwards = 2;
	public static final int downwards = 3;
	
	public static final int notCrossingPortal = -1;
	public static final int crossingPortalFromRightSide = 0;
	public static final int crossingPortalFromLeftSide = 1;
	
	public static int portalLeftSideCrossingPointXCoordinate = 0 - 32;
	public static int portalRightSideCrossingPointXCoordinate = 640 + 32;
	
	private static int portalLeftSideEntryXCoordinate = 160;
	private static int portalRightSideEntryXCoordinate = 480;
	
	public static int portalYCoordinate = 320;
	
	public static int pixelsTravelledPerTick = 2;
	
	public Character(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void portalCross()
	{
		if(gp.pacman.x == 0 && gp.pacman.y == 320 && gp.pacman.currentDir == left)
		{
			gp.pacman = new AlivePacman(left, gp.pacman.nextDir, 640, 320, gp);
		}
		
		if(gp.pacman.x == 640 && gp.pacman.y == 320 && gp.pacman.currentDir == right)
		{
			gp.pacman = new AlivePacman(right, gp.pacman.nextDir, 0, 320, gp);
		}
	}
	
	public void portalCrossGhosts(int i)
	{
		if((gp.ghostArray[i].x < 160 || gp.ghostArray[i].x > 480) && gp.ghostArray[i].y == 320)
		{
			if(gp.ghostArray[i].currentDir == left)
			{
				if(gp.ghostArray[i].x == 0)
				{
					gp.ghostArray[i] = new Ghost(i, gp.ghostArray[i].movementType, crossingPortalFromLeftSide, gp.ghostArray[i].isVulnerable, gp);
				}
				
				if(gp.ghostArray[i].x == 480)
				{
					gp.ghostArray[i].portalCrossingStatus = notCrossingPortal;
				}
			}
			else if(gp.ghostArray[i].currentDir == right)
			{
				if(gp.ghostArray[i].x == 640)
				{
					gp.ghostArray[i] = new Ghost(i, gp.ghostArray[i].movementType, crossingPortalFromRightSide, gp.ghostArray[i].isVulnerable, gp);
				}
				
				if(gp.ghostArray[i].x == 160)
				{
					gp.ghostArray[i].portalCrossingStatus = notCrossingPortal;
				}
			}
		}
	}
	
	protected void portalEvents(Character character)
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
	
	private void managePortalCrossingFromGivenSide(Character character, int side)
	{
		character.setPortalCrossingStatus(side);
		character.setCurrentDirection(side);
		
		if(!(character instanceof Pacman))
		{
			move(character, side);
		}
		
				
		if(isAtPortalCrossingPoint(character, side))
		{
			makeCharacterCrossPortal(character, side);
		}
		
		// Check opposite portal side's entry, hence side ^= 1
		if(isAtPortalEntry(character, side ^= 1))	
		{
			character.setPortalCrossingStatus(notCrossingPortal);
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
	}
	
	
	private void makeCharacterCrossPortal(Character character, int side)
	{
		if(character instanceof Pacman)
		{
			if(side == left)
			{
				gp.pacman = new AlivePacman(left, gp.pacman.nextDir, 640, 320, gp);
			}
			else if(side == right)
			{
				gp.pacman = new AlivePacman(right, gp.pacman.nextDir, 0, 320, gp);
			}
		}
		else if(character instanceof Ghost)
		{
			gp.ghostArray[character.getID()] = new Ghost(character.getID(), character.getMovementType(), character.getPortalCrossingStatus(), character.getVulnerabilityStatus(), gp);
		}
	}
	
	public void move(Character character, int direction)
	{
		if(gp.gameState == gp.lifeLostState)
		{
			return;
		}
		
		if(canMove(character, direction))
		{
			shiftCharacterInGivenDirection(character, direction);
		}
		else
		{
			keepMovingUntilCanChangeDirection(character, direction);
		}
	}

	public boolean canMove(Character character, int direction)
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
		
		for(int xx = 0; xx < gp.wallMatrix.length; xx++)
		{
			for(int yy = 0; yy < gp.wallMatrix[0].length; yy++)
			{
				if(gp.wallMatrix[xx][yy] != null)								
				{
					if(bounds.intersects(gp.wallMatrix[xx][yy]))						
					{
						return false;								
					}
				}
			}
		}
		return true;
	}
	
	public static void shiftCharacterInGivenDirection(Character character, int direction)
	{
		switch(direction)
		{
			case right: character.x += pixelsTravelledPerTick; break;
			case left: character.x -= pixelsTravelledPerTick; break;
			case upwards: character.y -= pixelsTravelledPerTick; break;
			case downwards: character.y += pixelsTravelledPerTick; break;
		}
	}
	
	private void keepMovingUntilCanChangeDirection(Character character, int direction)
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
	
	
	public int getCurrentDirection() {return -1;}
	public void setCurrentDirection(int dir) {}
	public int getPortalCrossingStatus() {return -1;}
	public void setPortalCrossingStatus(int portalStatus) {}
	public int getNextDirection(){return -1;}
	public int getID(){return -1;}
	public int getMovementType(){return -1;}
	public boolean getVulnerabilityStatus(){return false;}
}