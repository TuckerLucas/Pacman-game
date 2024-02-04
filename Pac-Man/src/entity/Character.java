package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Character extends Entity
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public static final int stopped = -1;
	public static final int right = 0;
	public static final int left = 1;
	public static final int upwards = 2;
	public static final int downwards = 3;
	
	public static int pixelsTravelledPerTick = 2;
	
	public Character(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
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
	
	public int getCurrentDirection() 
	{
		return 0;
	}
}