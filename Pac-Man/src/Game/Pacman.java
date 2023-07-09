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
	
	// Pacman movement variables
	private int lastDir;
	public static int dir;
	
	// Counter variable for number of eaten ghosts
	public static int nEatenGhosts = 0;
	
	// Direction variables (for ease of reading)
	private final int right = Game.right;
	private final int left  = Game.left;
	private final int up    = Game.up;
	private final int down  = Game.down;
	
	private int intersectedGhost = -1;
	
	// Constructor
	public Pacman(int x, int y)
	{
		// Check if pacman crossing left portal
		if(Game.pacmanCrossingLeftPortal == true)
		{
			Game.pacmanCrossingLeftPortal = false;
			dir = left;
		}
		// Check if pacman crossing right portal
		else if(Game.pacmanCrossingRightPortal == true)
		{
			Game.pacmanCrossingRightPortal = false;
			dir = right;
		}
		// Spawn pacman normally
		else
		{
			dir = right;
		}
		
		setBounds(x,y,32,32);
	}

	// Reset all ghost's eaten state
	public static void resetEatenGhosts()
	{
		for(int i = 0; i < Game.ghostArray.length; i++)
		{
			Game.ghostArray[i].eaten = false;
		}
		
		nEatenGhosts = 0;
	}
	
	// Check for eaten ghosts
	public void checkEatenGhosts()
	{
		for(int i = 0; i < Game.ghostArray.length; i++)
		{
			isGhostEaten(Game.ghostArray[i]);
		}
	}
	
	// Manage pacman collisions with food & energizers
	public void foodCollision()
	{
		// All food and energizers eaten
		if(Level.food.size() == 0 && Level.energizers.size() == 0)	
		{
			Game.gameStatus = Game.win;	
			return;
		}
		
		// Collision with food
		for(int i = 0; i < Level.food.size(); i++) 		
		{            
			if(this.intersects(Level.food.get(i)))							
			{
				new Sounds(Sounds.pacmanEatingSoundPath);
				
				Level.food.remove(i);	
				
				Game.score += Game.foodScore;
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				
				break;
			}
		}
		
		// Collision with energizer
		for(int i = 0; i < Level.energizers.size(); i++) 	
		{			
			if(this.intersects(Level.energizers.get(i)))						
			{	
				new Sounds(Sounds.energizerSoundPath);
				
				Level.energizers.remove(i);
				
				Game.score += Game.energizerScore;							// Add energizer points to the player's score
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				
				Energizer.isActive = true;					// Energizer status activated
				Energizer.activeTime = 0;							// Reset the energizer timer
				
				// No ghosts eaten
				resetEatenGhosts();
				
				break;
			}
		}
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
				intersectedGhost = i;
				return true;
			}
		}
		
		return false;
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
				// Check if ghost ha
				if(Game.ghostArray[intersectedGhost].eaten == true)
				{
					die();
				}
				else if(Game.ghostArray[intersectedGhost].eaten == false)
				{
					eatGhost();
				}
			}
			else if(Energizer.isActive == false)
			{
				die();
			}
		}
	}
	
	private void eatGhost()
	{
		Game.ghostArray[intersectedGhost] = new Ghost(Game.blinkySpawnX, Game.blinkySpawnY, intersectedGhost, -1, -1);
		Game.ghostArray[intersectedGhost].eaten = true;
	}
	
	private void die()
	{
		new Sounds(Sounds.pacmanDeathSoundPath);
		
		Game.lives--;
	
		if(Game.lives == 0)
		{
			Game.gameStatus = Game.lose;
		}
		else 
		{
			Game.gameStatus = Game.lifeLost;
		}
		
		Energizer.isActive = false;				

		resetEatenGhosts();
		
		Energizer.activeTime = 0;	
		
		BonusScore.display = false;
	}
	
	public void isGhostEaten(Ghost ghost)
	{
		if(ghost.intersects(this))
		{
			if(ghost.eaten)
			{
				die();
			}
			else
			{
				new Sounds(Sounds.ghostEatenSoundPath);
				
				nEatenGhosts++;
				
				Game.xEvent = x;
				Game.yEvent = y;
				BonusScore.display = true;
				
				switch(nEatenGhosts)
				{
					case 1: 
							Game.score = Game.score + 200;
							Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine2, 
							32, Texture.spriteSize);
							Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine6, 
							32, Texture.spriteSize);
							break;
					case 2: 
							Game.score = Game.score + 400; 
							Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine3, 
							32, Texture.spriteSize);
							Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine7, 
							32, Texture.spriteSize);
							break;
					case 3: 
							Game.score = Game.score + 800;  
							Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine4, 
							32, Texture.spriteSize);
							Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine8, 
							32, Texture.spriteSize);
							break;
					case 4: 
							Game.score = Game.score + 1600; 
							Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine5, 
							32, Texture.spriteSize);
							Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine9, 
							32, Texture.spriteSize);
							break;
				}
			
				for(int i = 2; i < Texture.bonusScore.length; i++)
				{
					if(i % 2 == 0)
					{
						Texture.bonusScore[i] = Texture.bonusScore[0];
					}
					else
					{
						Texture.bonusScore[i] = Texture.bonusScore[1];
					}
				}
				
				switch(ghost.enemyID)
				{
					case Game.blinkyID:
												
						Game.ghostArray[0] = new Ghost(Game.blinkySpawnX, Game.blinkySpawnY, ghost.enemyID, -1, -1);
						Game.ghostArray[0].eaten = true;
						
						break;
						
					case Game.inkyID:
											
						Game.ghostArray[1] = new Ghost(Game.inkySpawnX, Game.inkySpawnY, ghost.enemyID, -1, -1);
						Game.ghostArray[1].eaten = true;
						
						break;
						
					case Game.pinkyID:	
						
						Game.ghostArray[2] = new Ghost(Game.pinkySpawnX, Game.pinkySpawnY, ghost.enemyID, -1, -1);
						Game.ghostArray[2].eaten = true;
						
						break;
						
					case Game.clydeID:
												
						Game.ghostArray[3] = new Ghost(Game.clydeSpawnX, Game.clydeSpawnY, ghost.enemyID, -1, -1);
						Game.ghostArray[3].eaten = true;
						
						break;
				}
			}
		}
	}
		
	public void portalCrossing()
	{
		// Pacman going through the left portal
		if(x == Game.leftPortalX && y == Game.bothPortalsY)	
		{
			// Spawn pacman on the right side of the map
			Game.pacmanCrossingLeftPortal = true;
			Game.pacman = new Pacman(Game.rightPortalX, Game.bothPortalsY);
		}
		
		// Pacman going through the right portal
		if(x == Game.rightPortalX && y == Game.bothPortalsY)			
		{
			// Spawn pacman on the left side of the map
			Game.pacmanCrossingRightPortal = true;
			Game.pacman = new Pacman(Game.leftPortalX, Game.bothPortalsY);		
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

	public void tick()
	{	
		move(dir);
		portalCrossing();
		foodCollision();
		ghostCollision();
		eatingAnimation();
	}
}
