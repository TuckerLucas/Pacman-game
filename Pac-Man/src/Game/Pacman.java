/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Pacman.java
* 
* Description: 
* 
* This file contains the implementation for the Pacman 
* character regarding movement around the map, collisions with
* other characters/objects and animations.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	// Movement variables
	private int lastDir;
	public static int dir;
	
	// Counter variable for number of eaten ghosts
	public static int nEatenGhosts = 0;
	
	// Direction variables (for ease of reading)
	private final int right = Game.right;
	private final int left  = Game.left;
	private final int up    = Game.up;
	private final int down  = Game.down;
	
	// Intersected ghost variable
	private int intersectedGhost = -1;
	
	// Spawn variables
	public static final int spawnNormal = 0;
	private static final int spawnRight = 1;
	private static final int spawnLeft 	= 2;
	
	// Constructor
	public Pacman(int spawnPoint)
	{
		// Check where to spawn pacman
		switch(spawnPoint)
		{
			// Normal spawn
			case spawnNormal:
				dir = right;
				setBounds(192,512,32,32);
				break;
			// Spawn in the right portal
			case spawnRight:
				dir = left;
				setBounds(640,320,32,32);
				break;
			// Spawn in the left portal
			case spawnLeft:
				dir = right;
				setBounds(0,320,32,32);
				break;
		}
	}

	
	// Manage pacman collisions with food
	private void foodCollision()
	{	
		for(int i = 0; i < Level.food.size(); i++) 		
		{    
			// Check for collision with food
			if(this.intersects(Level.food.get(i)))							
			{
				// Eat the food
				Food.eat(i);
				
				break;
			}
		}
	}
	
	// Manage pacman collisions with energizers
	private void energizerCollision()
	{			
		for(int i = 0; i < Level.energizers.size(); i++) 	
		{		
			// Check for collision with energizer
			if(this.intersects(Level.energizers.get(i)))						
			{	
				// Activate the energizer
				Energizer.activate(i);							
				
				break;
			}
		}
	}
	
	
	// Turn all ghosts vulnerable
	public static void makeGhostsVulnerable()
	{
		for(int i = 0; i < Game.ghostArray.length; i++)
		{
			Game.ghostArray[i].isVulnerable = true;
		}
		
		nEatenGhosts = 0;
	}

	// Check if pacman and a ghost have intersected
	public boolean intersectedWithGhost()
	{
		// Iterate through the ghost array
		for(int i = 0; i < Game.ghostArray.length; i++)
		{
			// Check for intersection between ghost and pacman
			if(Game.ghostArray[i].intersects(this))
			{	
				// Identify the intersected ghost
				intersectedGhost = i;
				
				return true;
			}
		}
		
		return false;
	}

	// Make pacman eat intersected ghost
	private void eatGhost()
	{
		// Ghost eaten sound effect
		new Sounds(Sounds.ghostEatenSoundPath);
		
		// Respawn eaten ghost
		Game.ghostArray[intersectedGhost] = new Ghost(intersectedGhost, -1, false);
		
		// Capture the event coordinates
		Game.xEvent = x; 
		Game.yEvent = y;
		
		// Display bonus score 
		BonusScore.display = true;
		
		// Increment the number of eaten ghosts
		nEatenGhosts++;
		
		// Add the bonus score to the game score
		Game.score += BonusScore.bonusScore;
	}
	
	// Manage pacman death
	private void die()
	{
		// Pacman death sound effect
		new Sounds(Sounds.pacmanDeathSoundPath);
		
		// Decrement amount of lives
		Game.lives--;
	
		// Check number of lives left
		if(Game.lives == 0)
		{
			Game.gameStatus = Game.lose;
		}
		else 
		{
			Game.gameStatus = Game.lifeLost;
		}		
	}
	
	// Manage pacman collisions with ghosts
	public void ghostCollision()
	{
		// Check for collision between pacman and ghost
		if(intersectedWithGhost() == true)
		{
			// Check if energizer is active
			if(Energizer.isActive == true)
			{
				// Check if ghost hasn't been eaten yet
				if(Game.ghostArray[intersectedGhost].isVulnerable == true)
				{
					// Eat the ghost
					eatGhost();
					
					return;
				}
			}
			
			die();
		}
	}

	
	// Manage pacman crossing map portals
	public void portalCrossing()
	{
		// Pacman going through the left portal
		if(x == Game.leftPortalX && y == Game.bothPortalsY)	
		{
			// Spawn pacman on the right side of the map
			Game.pacman = new Pacman(spawnRight);
		}
		
		// Pacman going through the right portal
		if(x == Game.rightPortalX && y == Game.bothPortalsY)			
		{
			// Spawn pacman on the left side of the map
			Game.pacman = new Pacman(spawnLeft);		
		}
	}
	
	
	// Manage pacman animation
	public void eatingAnimation()
	{
		// Increase current animation phase time
		Game.pacmanAnimationTime++;
		
		// Check if time for animation phase is complete
		if(Game.pacmanAnimationTime == Game.pacmanAnimationTargetTime)
		{
			// Reset timer for animation phase
			Game.pacmanAnimationTime = 0;
			
			// Move to the next animation phase
			Texture.animationPhasePacman++;
		}
	}
	
	// Render object
	public void render(Graphics g)
	{
		if(Texture.animationPhasePacman == 3)
		{
			Texture.animationPhasePacman = 0;
		}
		
		switch(lastDir)
		{
			case right:
				// Look right
				g.drawImage(Texture.pacmanLookRight[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case left:
				// Look left
				g.drawImage(Texture.pacmanLookLeft[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case up:
				// Look up
				g.drawImage(Texture.pacmanLookUp[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case down:
				// Look down
				g.drawImage(Texture.pacmanLookDown[Texture.animationPhasePacman], x, y, width, height, null);
				break;
		}
	}

	
	public void tick()
	{	
		move(dir);
		portalCrossing();
		foodCollision();
		energizerCollision();
		ghostCollision();
		eatingAnimation();
	}
	
	private void setDirection(int dir)
	{
		switch(dir)
		{
			case right: x += Game.speed; lastDir = right; break;
			case left:  x -= Game.speed; lastDir = left;  break;
			case up:    y -= Game.speed; lastDir = up;    break;
			case down:  y += Game.speed; lastDir = down;  break;
		}
	}
	
	public void move(int dir)
	{
		if(canMove(dir))
		{
			setDirection(dir);
			return;
		}
		
		switch(dir)
		{
			case right:
				
				if(lastDir == left && canMove(left))
				{
					x -= Game.speed;
				}
				if(lastDir == up && canMove(up))
				{
					y -= Game.speed;
				}
				if(lastDir == down && canMove(down))
				{
					y += Game.speed;
				}
			
				break;
					
			case left: 
				
				if(lastDir == right && canMove(right))
				{
					x += Game.speed;
				}
				if(lastDir == up && canMove(up))
				{
					y -= Game.speed;
				}
				if(lastDir == down && canMove(down))
				{
					y += Game.speed;
				}
					
				break;
					
			case up:
				
				if(lastDir == left && canMove(left))
				{
					x -= Game.speed;
				}
				if(lastDir == right && canMove(right))
				{
					x += Game.speed;
				}
				if(lastDir == down && canMove(down))
				{
					y += Game.speed;
				}
				
				break;
			
			case down:
					
				if(lastDir == left && canMove(left))
				{
					x -= Game.speed;
				}
				if(lastDir == up && canMove(up))
				{
					y -= Game.speed;
				}
				if(lastDir == right && canMove(right))
				{
					x += Game.speed;
				}
				
				break;
		}
	}
	
	private boolean canMove(int direction)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case right: nextx = x + Game.speed; nexty = y; break;
			case left:	nextx = x - Game.speed; nexty = y; break;
			case up: 	nextx = x; nexty = y - Game.speed; break;
			case down: 	if(x == 320 && y == 256) {return false;}
						nextx = x; nexty = y + Game.speed; break;
		}
		
		Rectangle bounds = new Rectangle(nextx, nexty, width, height);

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
}
