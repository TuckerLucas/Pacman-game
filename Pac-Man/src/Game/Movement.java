package Game;

import java.awt.Rectangle;

public class Movement extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static final int right 	= 0;
	public static final int left  	= 1;
	public static final int up    	= 2;
	public static final int down  	= 3;
	
	public static int speed = 2;
	
	public Movement()
	{
		
	}
	
	public static boolean canMove(int direction, Rectangle character)
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
		
		for(int xx = 0; xx < Level.tiles.length; xx++)
		{
			for(int yy = 0; yy < Level.tiles[0].length; yy++)
			{
				if(Level.tiles[xx][yy] != null)								
				{
					if(bounds.intersects(Level.tiles[xx][yy]))						
					{
						return false;								
					}
				}
			}
		}
		return true;
	}
	
	public static void moveGhost(Ghost ghost, int dir)
	{
		switch(dir)
		{
			case right: ghost.x+=speed; ghost.currentDir = right; break;
			case left: 	ghost.x-=speed; ghost.currentDir = left;  break;
			case up: 	ghost.y-=speed; ghost.currentDir = up; break;
			case down: 	ghost.y+=speed; ghost.currentDir = down;  break;
		}
	}
	
	public static void movePacman(Pacman pacman, int direction)
	{
		// Check if pacman can move in given direction
		if(canMove(direction, pacman))
		{
			// Make pacman move
			switch(direction)
			{
				case right: pacman.x += speed; pacman.lastDir = right; break;
				case left:  pacman.x -= speed; pacman.lastDir = left;  break;
				case up:    pacman.y -= speed; pacman.lastDir = up;    break;
				case down:  pacman.y += speed; pacman.lastDir = down;  break;
			}
			return;
		}
		
		// Continue moving in last direction until direction change is possible
		switch(direction)
		{
			case right:
				
				if(pacman.lastDir == left && canMove(left, pacman))
				{
					pacman.x -= speed;
				}
				if(pacman.lastDir == up && canMove(up, pacman))
				{
					pacman.y -= speed;
				}
				if(pacman.lastDir == down && canMove(down, pacman))
				{
					pacman.y += speed;
				}
			
				break;
					
			case left: 
				
				if(pacman.lastDir == right && canMove(right, pacman))
				{
					pacman.x += speed;
				}
				if(pacman.lastDir == up && canMove(up, pacman))
				{
					pacman.y -= speed;
				}
				if(pacman.lastDir == down && canMove(down, pacman))
				{
					pacman.y += speed;
				}
					
				break;
					
			case up:
				
				if(pacman.lastDir == left && canMove(left, pacman))
				{
					pacman.x -= speed;
				}
				if(pacman.lastDir == right && canMove(right, pacman))
				{
					pacman.x += speed;
				}
				if(pacman.lastDir == down && canMove(down, pacman))
				{
					pacman.y += speed;
				}
				
				break;
			
			case down:
					
				if(pacman.lastDir == left && canMove(left, pacman))
				{
					pacman.x -= speed;
				}
				if(pacman.lastDir == up && canMove(up, pacman))
				{
					pacman.y -= speed;
				}
				if(pacman.lastDir == right && canMove(right, pacman))
				{
					pacman.x += speed;
				}
				
				break;
		}
	}
}
