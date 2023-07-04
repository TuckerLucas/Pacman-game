/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Pacman.java
* 
* Description: 
* 
* This file contains the implementation for the Pacman 
* character regarding movement around the map, colisions with
* other characters/objects and animations.
* 
/**************************************************************/

package Game;

import GUI.LeaderboardPanel;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	// Pacman movement variables
	private int lastDir;
	public static int dir;
	
	// Counter variable for number of eaten ghosts
	private int nEatenGhosts = 0;
	
	// Direction variables (for ease of reading)
	private final int right = Game.right;
	private final int left  = Game.left;
	private final int up    = Game.up;
	private final int down  = Game.down;
	

	// Constructor
	public Pacman(int x, int y)
	{
		// Check if pacman has crossed left portal
		if(Game.pacmanCrossingLeftPortal == true)
		{
			Game.pacmanCrossingLeftPortal = false;
			
			dir = left;
		}
		// Check if pacman has crossed right portal 
		else if(Game.pacmanCrossingRightPortal == true)
		{
			Game.pacmanCrossingRightPortal = false;
			
			dir = right;
		}
		// Pacman was not crossing a portal, spawn normally
		else
		{
			dir     = right;
			lastDir = right;
		}
		
		setBounds(x,y,32,32);
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
				// Make pacman look right
				g.drawImage(Texture.pacmanLookRight[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case left:
				// Make pacman look left
				g.drawImage(Texture.pacmanLookLeft[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case up:
				// Make pacman look up
				g.drawImage(Texture.pacmanLookUp[Texture.animationPhasePacman], x, y, width, height, null);
				break;
			case down:
				// Make pacman look down
				g.drawImage(Texture.pacmanLookDown[Texture.animationPhasePacman], x, y, width, height, null);
				break;
		}
		
		if(Game.bonusScore != -1)
		{
			if(Game.timesScoreFlashed == 3)
			{
				Game.timesScoreFlashed = 0;
				Game.bonusScore = -1;
			}
			else 
			{
				switch(Game.bonusScore)
				{
					case 200: 
						if(Texture.animationPhase200 >= 10)
						{
							Texture.animationPhase200 = 0;
							Game.timesScoreFlashed++;
						}
						g.drawImage(Texture.bonusScore200[Texture.animationPhase200], Game.xEvent, Game.yEvent, width, height, null);
					
						break;
						
					case 400:
						
						if(Texture.animationPhase400 >= 10)
						{
							Texture.animationPhase400 = 0;
							Game.timesScoreFlashed++;
						}
						g.drawImage(Texture.bonusScore400[Texture.animationPhase400], Game.xEvent, Game.yEvent, width, height, null);
						
						break;
						
					case 800:
						
						if(Texture.animationPhase800 >= 10)
						{
							Texture.animationPhase800 = 0;
							Game.timesScoreFlashed++;
						}
						g.drawImage(Texture.bonusScore800[Texture.animationPhase800], Game.xEvent, Game.yEvent, width, height, null);
					
						break;
						
					case 1600:
						
						if(Texture.animationPhase1600 >= 10)
						{
							Texture.animationPhase1600 = 0;
							Game.timesScoreFlashed++;
						}
						g.drawImage(Texture.bonusScore1600[Texture.animationPhase1600], Game.xEvent, Game.yEvent, width, height, null);
					
						break;
				}
			}
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

	private void resetEatenGhosts()
	{
		Game.blinky.eaten = false;													
		Game.inky.eaten   = false;
		Game.pinky.eaten  = false;
		Game.clyde.eaten  = false;
	}
	
	public void foodColision()
	{
		// No food left
		if(Level.food.size() == 0 && Level.energizers.size() == 0)	
		{
			Game.gameStatus = Game.win;	
			
			Energizer.status = false;				

			resetEatenGhosts();
			
			Energizer.time = 0;	
			return;
		}
		
		// Collision with food
		for(int i = 0; i < Level.food.size(); i++) 		
		{            
			if(this.intersects(Level.food.get(i)))							
			{
				new Sounds(Sounds.pacmanEatingSoundPath);
				
				Level.food.remove(i);		
				Game.score += 10;
				
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
				
				Game.score += 50;							// Add energizer points to the player's score
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				
				Energizer.status = true;					// Energizer status activated
				Energizer.time = 0;							// Reset the energizer timer
				
				// No ghosts eaten
				resetEatenGhosts();
				
				break;
			}
		}
	}
	
	public void ghostColision()
	{
		if(Energizer.status == true)
		{
			if(Energizer.time == Energizer.targetTime)		// Energizer time over
			{
				energizerNotActive();									
			}
			else if(Energizer.time < Energizer.targetTime)	// Energizer time not over yet
			{
				energizerActive();								 			
			}
		}
		else if(!Energizer.status)
		{
			if(Game.blinky.intersects(this) || Game.inky.intersects(this) || 
			   Game.pinky.intersects(this) || Game.clyde.intersects(this))										
			{
				new Sounds(Sounds.pacmanDeathSoundPath);
				
				Game.lives = Game.lives - 1;
			
				if(Game.lives == 0)
				{
					LeaderboardPanel.read_from_file();
					LeaderboardPanel.swap_values();
					LeaderboardPanel.write_to_file();
					
					Game.gameStatus = Game.lose;
				}
				else 
				{
					Game.gameStatus = Game.lifeLost;
				}	
			}
		}
	}
	
	public void energizerNotActive()
	{
		Energizer.status = false;
		
		resetEatenGhosts();
		
		Energizer.time = 0;	

		nEatenGhosts = 0;
	}
	
	public void isGhostEaten(Ghost ghost)
	{
		if(ghost.intersects(this))
		{
			if(ghost.eaten)
			{
				new Sounds(Sounds.pacmanDeathSoundPath);
				
				Game.lives = Game.lives - 1;
				
				if(Game.lives == 0)
				{
					LeaderboardPanel.read_from_file();
					LeaderboardPanel.swap_values();
					LeaderboardPanel.write_to_file();
					Game.gameStatus = Game.lose;
				}
				else 
				{
					Game.gameStatus = Game.lifeLost;
				}
				
				Energizer.status = false;				

				resetEatenGhosts();
				
				Energizer.time = 0;	
				
				Game.bonusScore = -1;
			}
			else
			{
				new Sounds(Sounds.ghostEatenSoundPath);
				
				++nEatenGhosts;
				
				Game.xEvent = x;
				Game.yEvent = y;
				Game.stopBonusScore = false;
				
				switch(nEatenGhosts)
				{
					case 1: Game.score = Game.score + 200; Game.bonusScore = 200; break;
					case 2: Game.score = Game.score + 400; Game.bonusScore = 400; break;
					case 3: Game.score = Game.score + 800; Game.bonusScore = 800; break;
					case 4: Game.score = Game.score + 1600; Game.bonusScore = 1600; break;
				}
			
				switch(ghost.enemyID)
				{
					case Game.blinkyID:
												
						Game.blinky = new Ghost(Game.blinkySpawnX, Game.blinkySpawnY, ghost.enemyID, -1, -1);
						Game.blinky.eaten = true;
						
						break;
						
					case Game.inkyID:
											
						Game.inky = new Ghost(Game.inkySpawnX, Game.inkySpawnY, ghost.enemyID, -1, -1);
						Game.inky.eaten = true;
						
						break;
						
					case Game.pinkyID:	
						
						Game.pinky = new Ghost(Game.pinkySpawnX, Game.pinkySpawnY, ghost.enemyID, -1, -1);
						Game.pinky.eaten = true;
						
						break;
						
					case Game.clydeID:
												
						Game.clyde = new Ghost(Game.clydeSpawnX, Game.clydeSpawnY, ghost.enemyID, -1, -1);
						Game.clyde.eaten = true;
						
						break;
				}
			}
		}
	}
	
	public void checkEatenGhosts()
	{
		isGhostEaten(Game.blinky);
		isGhostEaten(Game.inky);
		isGhostEaten(Game.pinky);
		isGhostEaten(Game.clyde);
	}
	
	public void energizerActive()
	{
		if(Energizer.time >= Energizer.flashTime)
		{
			Energizer.flash = true;
		}
		else if(Energizer.time < Energizer.flashTime)
		{
			Energizer.flash = false;
		}
			
		checkEatenGhosts();
		
		Energizer.time++;	
	}

	
	
	public void positioning()
	{
		// Pacman going through the left portal
		if(x == Game.leftPortalX && y == Game.bothPortalsY)	
		{
			Game.pacmanCrossingLeftPortal = true;
			Game.pacman = new Pacman(Game.rightPortalX, Game.bothPortalsY);		// Spawn pacman on the right side of the map
		}
		
		// Pacman going through the right portal
		if(x == Game.rightPortalX && y == Game.bothPortalsY)			
		{
			Game.pacmanCrossingRightPortal = true;
			Game.pacman = new Pacman(Game.leftPortalX, Game.bothPortalsY);		// Spawn pacman on the left side of the map
		}
	}
	
	
	public void animation()
	{
		Game.pacmanAnimationTime++;
		
		if(Game.pacmanAnimationTime == Game.pacmanAnimationTargetTime)
		{
			Game.pacmanAnimationTime = 0;
			Texture.animationPhasePacman++;	
			
			if(Game.imageIndexFlash == 3)
			{
				Game.imageIndexFlash = 0;
			}
			else
			{
				Game.imageIndexFlash++;
			}
		}
		
		Game.bonusScoreAnimationTime++;
		
		if(Game.bonusScoreAnimationTime == Game.bonusScoreAnimationTargetTime)
		{
			Game.bonusScoreAnimationTime = 0;

			switch(Game.bonusScore)
			{
				case 200:  Texture.animationPhase200++;  break;
				case 400:  Texture.animationPhase400++;  break;
				case 800:  Texture.animationPhase800++;  break;
				case 1600: Texture.animationPhase1600++; break;
			}
		}
	}
	
	public void tick()
	{	
		move(dir);
		positioning();
		foodColision();
		ghostColision();
		animation();
	}
}