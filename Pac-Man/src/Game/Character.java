package Game;

import java.awt.Rectangle;

public class Character extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int right 	= 0;
	public static final int left  	= 1;
	public static final int up    	= 2;
	public static final int down  	= 3;
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	public static int speed = 2;
	
	public int currentDir;
	public static int nextDir;
	
	public Character()
	{
		
	}
	
	public static boolean canMove(int direction, Character character)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case right: nextx = character.x + speed; nexty = character.y; break;
			case left:	nextx = character.x - speed; nexty = character.y; break;
			case up: 	nextx = character.x; nexty = character.y - speed; break;
			case down: 	if(character.x == 320 && character.y == 256) {return false;}
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
				case right: character.x+=speed; break;//character.currentDir = right; break;
				case left: 	character.x-=speed; break;//character.currentDir = left;  break;
				case up: 	character.y-=speed; break;//character.currentDir = up; break;
				case down: 	character.y+=speed; break;//character.currentDir = down;  break;
			}
			return;
		}

		
		// Continue moving in last direction until direction change is possible
		if(direction == right || direction == left)
		{
			if(character.currentDir == up && canMove(up, character))
			{
				character.y -= speed;
			}
			if(character.currentDir == down && canMove(down, character))
			{
				character.y += speed;
			}
		}
		else if(direction == up || direction == down)
		{
			if(character.currentDir == left && canMove(left, character))
			{
				character.x -= speed;
			}
			if(character.currentDir == right && canMove(right, character))
			{
				character.x += speed;
			}
		}
	}
}