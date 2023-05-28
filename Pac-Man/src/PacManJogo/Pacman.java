package PacManJogo;

import PacManGUI.LeaderboardPanel;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	public static int dir     = 1;
	public static int lastDir = 1;
	
	public static int right = 1;
	public static int left  = 2;
	public static int up    = 3;
	public static int down  = 4;

	public static int energizerTime       = 0;
	public static int energizerTargetTime = 60*8; 
	public static int energizerFlashTime  = 60*5;
	
	public static boolean energizerFlash  = false;
	public static boolean energizerStatus = false;
	public static boolean soundOn = false;
	
	private int speed = 2;

	// Animation timing variables
	private int time 				  = 0;	
	private int targetTime 			  = 8;
	private int imageIndex            = 0;
	public static int imageIndexFlash = 0;
	
	private int bonusScoreTime			= 0;
	private int bonusScoreTargetTime 	= 10;
	public static int imageIndex2 	= 0;	
	public static int imageIndex4 	= 0;
	public static int imageIndex8 	= 0;
	public static int imageIndex16 	= 0;
	
	public static int lives 		  = 3;
	private int ghostsEaten 		  = 0;
	
	public static int bonusScore = -1;
	public static int score2 = 200;
	public static int score4 = 400;
	public static int score8 = 800;
	public static int score16 = 1600;
	public static boolean stopBonusScore = false;
	
	private int timesScoreFlashed = 0;
	
	public int currentScore;
	
	public Pacman(int x, int y)
	{
		setBounds(x,y,32,32);
	}

	public void render(Graphics g)
	{
		if(imageIndex == 3)
			imageIndex = 0;
		
		if(lastDir == right)
			g.drawImage(Texture.player[imageIndex], x, y, width, height, null);			// Make pacman look right
		else if(lastDir == left)
			g.drawImage(Texture.player[imageIndex], x+32, y, -width, height, null);		// Make pacman look left
		else if(lastDir == up)
			g.drawImage(Texture.player1[imageIndex], x, y+32, width, -height, null);	// Make pacman look up
		else if(lastDir == down)
			g.drawImage(Texture.player1[imageIndex], x, y, width, height, null);		// Make pacman look down
		
		if(bonusScore == 200 || bonusScore == 400 || bonusScore == 800 || bonusScore == 1600)
		{
			if(timesScoreFlashed == 3)
			{
				timesScoreFlashed = 0;
				bonusScore = -1;
			}
			else 
			{
				if(bonusScore == 200)
				{
					if(imageIndex2 >= 10)
					{
						imageIndex2 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.two_hundred[imageIndex2], Game.x_position, Game.y_position, width, height, null);
				}
				if(bonusScore == 400)
				{
					if(imageIndex4 >= 10)
					{
						imageIndex4 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.four_hundred[imageIndex4], Game.x_position, Game.y_position, width, height, null);
				}
				if(bonusScore == 800)
				{
					if(imageIndex8 >= 10)
					{
						imageIndex8 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.eight_hundred[imageIndex8], Game.x_position, Game.y_position, width, height, null);
				}
				if(bonusScore == 1600)
				{
					if(imageIndex16 >= 10)
					{
						imageIndex16 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.sixteen_hundred[imageIndex16], Game.x_position, Game.y_position, width, height, null);
				}
			}
		}
	}
	

	public static void playSound(String filepath)
	{
		if(soundOn)
			new Sounds(filepath);
	}
	
	
	private boolean canMove(int direction)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case 1: nextx = x+speed; nexty = y; break;
			
			case 2:	nextx = x-speed; nexty = y; break;
			
			case 3: nextx = x; nexty = y-speed; break;
			
			case 4: if(x == 320 && y == 256) {return false;}
			
					nextx = x; nexty = y+speed; break;
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
			case 1: x+=speed; lastDir = right; break;
				
			case 2: x-=speed; lastDir = left; break;
			
			case 3: y-=speed; lastDir = up; break;
			
			case 4: y+=speed; lastDir = down; break;
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
			case 1:
				
				if(lastDir == left && canMove(left))
					x-=speed;
				if(lastDir == up && canMove(up))
					y-=speed;
				if(lastDir == down && canMove(down))
					y+=speed;
			
				break;
					
			case 2: 
				
				if(lastDir == right && canMove(right))
					x+=speed;
				if(lastDir == up && canMove(up))
					y-=speed;
				if(lastDir == down && canMove(down))
					y+=speed;
					
				break;
					
			case 3:
				
				if(lastDir == left && canMove(left))
					x-=speed;
				if(lastDir == right && canMove(right))
					x+=speed;
				if(lastDir == down && canMove(down))
					y+=speed;
				
				break;
			
			case 4:
					
				if(lastDir == left && canMove(left))
					x-=speed;
				if(lastDir == up && canMove(up))
					y-=speed;
				if(lastDir == right && canMove(right))
					x+=speed;
				
				break;
		}
		
		/*
	 	int dirArray[] = {right,left,up,down};
		 
		for(int i = 0; i < 4; i++)
		{
			if(dirArray[i] == dir)
				break;
			else
			{
				if(lastDir == dirArray[i] && canMove(dirArray[i]))
				{
					setDirection(dirArray[i]);
					return;
				}
			}
		}
		*/
	}

	public void colision_with_food()
	{
		if(Level.food.size() == 0 && Level.energizers.size() == 0)	
		{
			Game.GAME_STATUS = Game.WIN;	
			
			energizerStatus = false;				
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			energizerTime = 0;	
			return;
		}
		
		// Collision with food
		for(int i = 0; i < Level.food.size(); i++) 		
		{
			String filepath = "res\\Sounds\\PacMan Eating.wav";
            
			if(this.intersects(Level.food.get(i)))							
			{
				playSound(filepath);
				
				Level.food.remove(i);		
				Game.score+=10;
				
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
			String filepath = "res\\Sounds\\Energizer.wav";
			
			if(this.intersects(Level.energizers.get(i)))						
			{
				playSound(filepath);
				
				Level.energizers.remove(i);
				
				Game.score += 50;							// Add energizer points to the player's score
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				
				/*
				if(energizerStatus)
					bonusScore = -1;
				*/
				
				energizerStatus = true;					// Energizer status activated
				energizerTime = 0;							// Reset the energizer timer
				
				// No ghosts eaten
				Game.Blinky.eaten = false;													
				Game.Inky.eaten = false;
				Game.Pinky.eaten = false;
				Game.Clyde.eaten = false;
				
				break;
			}
		}
	}
	
	public void colision_with_ghost()
	{
		if(energizerStatus)					//Estamos no estado energizer
		{
			//timesScoreFlashed = 0;
			
			if(energizerTime == energizerTargetTime)					// Energizer time over		
				energizerTime_over();									
			else if(energizerTime < energizerTargetTime)				// Energizer time not over yet
				energizerTime_not_over();								 			
		}
		else if(!energizerStatus)
		{
			if(Game.Blinky.intersects(this) || Game.Inky.intersects(this) || Game.Pinky.intersects(this) || Game.Clyde.intersects(this))	//Interce��o com inimigo										
			{
				String filepath = "res\\Sounds\\PacMan Death.wav";
				playSound(filepath);
				
				lives = lives - 1;
			
				if(lives == 0)
				{
					LeaderboardPanel.read_from_file();
					LeaderboardPanel.swap_values();
					LeaderboardPanel.write_to_file();
			    	
					//LeaderboardPanel.update_table();
					
					Game.GAME_STATUS = Game.LOSE;
				}
				else 
				{
					Game.GAME_STATUS = Game.LIFE_LOST;
				}	
			}
		}
	}
		
	
	
	public static void makeGhostFlash()
	{
		energizerFlash = true;
	}
	
	public void keepGhostBlue()
	{
		energizerFlash = false;
	}

	public void showBonusScore(int bScore)
	{
		bonusScore = bScore;
	}
	
	public void energizerTime_over()
	{
		energizerStatus = false;
		
		Game.Blinky.eaten = false;													
		Game.Inky.eaten = false;
		Game.Pinky.eaten = false;
		Game.Clyde.eaten = false;
		
		energizerTime = 0;	

		ghostsEaten = 0;
	}
	
	public void isGhostEaten(Ghost ghost, int ID)
	{
		if(ghost.intersects(this))
		{
			if(ghost.eaten)
			{
				String filepath = "res\\Sounds\\PacMan Death.wav";
				playSound(filepath);
				
				lives = lives - 1;
				
				if(lives == 0)
				{
					LeaderboardPanel.read_from_file();
					LeaderboardPanel.swap_values();
					LeaderboardPanel.write_to_file();
					Game.GAME_STATUS = Game.LOSE;
				}
				else 
					Game.GAME_STATUS = Game.LIFE_LOST;
				
				energizerStatus = false;				
				Game.Blinky.eaten = false;													
				Game.Inky.eaten = false;
				Game.Pinky.eaten = false;
				Game.Clyde.eaten = false;
				
				energizerTime = 0;	
				
				bonusScore = -1;
			}
			else
			{
				switch(ID)
				{
					case Game.blinkyID:
						if(Game.Blinky.intersects(this))
						{
							String filepath = "res\\Sounds\\Ghost Eaten.wav";
							playSound(filepath);
							
							ghostsEaten = ghostsEaten + 1;
							if(ghostsEaten == 1)
							{
								Game.score = Game.score + 200;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(200);
							}
							else if(ghostsEaten == 2)
							{
								Game.score = Game.score + 400;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(400);
							}
							else if(ghostsEaten == 3)
							{
								Game.score = Game.score + 800;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(800);
							}
							else if(ghostsEaten == 4)
							{
								Game.score = Game.score + 1600;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(1600);
								ghostsEaten = 0;
							}						
							Game.Blinky = new Ghost(Game.blinkySpawnX, Game.blinkySpawnY, 0, -1, -1);
							Game.Blinky.eaten = true;
						}
						break;
					case Game.inkyID:
						if(Game.Inky.intersects(this))
						{
							String filepath = "res\\Sounds\\Ghost Eaten.wav";
							playSound(filepath);
							
							ghostsEaten = ghostsEaten + 1;
							if(ghostsEaten == 1)
							{
								Game.score = Game.score + 200;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(200);
							}
							else if(ghostsEaten == 2)
							{
								Game.score = Game.score + 400;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(400);
							}
							else if(ghostsEaten == 3)
							{
								Game.score = Game.score + 800;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(800);
							}
							else if(ghostsEaten == 4)
							{
								Game.score = Game.score + 1600;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(1600);
								ghostsEaten = 0;
							}						
							Game.Inky = new Ghost(Game.inkySpawnX, Game.inkySpawnY, 1, -1, -1);
							Game.Inky.eaten = true;
						}
						break;
					case Game.pinkyID:
						if(Game.Pinky.intersects(this))
						{
							String filepath = "res\\Sounds\\Ghost Eaten.wav";
							playSound(filepath);
							
							ghostsEaten = ghostsEaten + 1;
							if(ghostsEaten == 1)
							{
								Game.score = Game.score + 200;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(200);
							}
							else if(ghostsEaten == 2)
							{
								Game.score = Game.score + 400;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(400);
							}
							else if(ghostsEaten == 3)
							{
								Game.score = Game.score + 800;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(800);
							}
							else if(ghostsEaten == 4)
							{
								Game.score = Game.score + 1600;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(1600);
								ghostsEaten = 0;
							}						
							Game.Pinky = new Ghost(Game.pinkySpawnX, Game.pinkySpawnY, 2, -1, -1);
							Game.Pinky.eaten = true;
						}
						break;
					case Game.clydeID:
						if(Game.Clyde.intersects(this))
						{
							String filepath = "res\\Sounds\\Ghost Eaten.wav";
							playSound(filepath);
							
							ghostsEaten = ghostsEaten + 1;
							if(ghostsEaten == 1)
							{
								Game.score = Game.score + 200;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(200);
							}
							else if(ghostsEaten == 2)
							{
								Game.score = Game.score + 400;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(400);
							}
							else if(ghostsEaten == 3)
							{
								Game.score = Game.score + 800;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(800);
							}
							else if(ghostsEaten == 4)
							{
								Game.score = Game.score + 1600;
								Game.x_position = x;
								Game.y_position = y;
								stopBonusScore = false;
								showBonusScore(1600);
								ghostsEaten = 0;
							}						
							Game.Clyde = new Ghost(Game.clydeSpawnX, Game.clydeSpawnY, 3, -1, -1);
							Game.Clyde.eaten = true;
						}
						break;
				}
			}
		}
	}
	
	public void GhostNotEaten(Ghost ghost)
	{			
		if(ghost.intersects(this))
		{
			String filepath = "res\\Sounds\\Ghost Eaten.wav";
			playSound(filepath);
			
			ghostsEaten = ghostsEaten++;
			
			Game.x_position = x;
			Game.y_position = y;
			stopBonusScore = false;
			
			switch(ghostsEaten)
			{
				case 1: Game.score = Game.score + 200; showBonusScore(200); break;
				case 2: Game.score = Game.score + 400; showBonusScore(400); break;
				case 3: Game.score = Game.score + 800; showBonusScore(800); break;
				case 4: Game.score = Game.score + 1600; showBonusScore(1600); break;
			}	
			
			switch(ghost.enemyID)
			{
				case Game.blinkyID:
					ghost = new Ghost(Game.blinkySpawnX, Game.blinkySpawnY, Game.blinkyID, -1, -1);
					ghost.eaten = true;
					break;
				case Game.inkyID:
					ghost = new Ghost(Game.inkySpawnX, Game.inkySpawnY, Game.inkyID, -1, -1);
					ghost.eaten = true;
					break;
				case Game.pinkyID:
					ghost = new Ghost(Game.pinkySpawnX, Game.pinkySpawnY, Game.pinkyID, -1, -1);
					ghost.eaten = true;
					break;
				case Game.clydeID:
					ghost = new Ghost(Game.clydeSpawnX, Game.clydeSpawnY, Game.clydeID, -1, -1);
					ghost.eaten = true;
					break;
			}
		}
	}
	
	public void checkEatenGhosts()
	{
		isGhostEaten(Game.Blinky, Game.blinkyID);
		isGhostEaten(Game.Inky, Game.inkyID);
		isGhostEaten(Game.Pinky, Game.pinkyID);
		isGhostEaten(Game.Clyde, Game.clydeID);
	}
	
	public void energizerTime_not_over()
	{
		if(energizerTime >= energizerFlashTime)
			makeGhostFlash();
		else if(energizerTime < energizerFlashTime)
			keepGhostBlue();
		
		checkEatenGhosts();
		
		energizerTime ++;	
	}

	
	
	public void movement()
	{
		move(dir);
	}
	
	public void colisions()
	{
		colision_with_food();
		colision_with_ghost();
	}
	
	public void positioning()
	{
		if(x == 0 && y == 320)						// Pacman going through the left portal	
			Game.pacman = new Pacman(640, 320);		// Spawn pacman on the right side of the map
		else if(x == 640 && y == 320)				// Pacman going through the right portal	
			Game.pacman = new Pacman(0, 320);		// Spawn pacman on the left side of the map
	}
	
	public void animation()
	{
		time ++;
		
		if(time == targetTime)
		{
			time = 0;
			imageIndex ++;
			
			if(imageIndexFlash == 3)
				imageIndexFlash = 0;
			else
				imageIndexFlash ++;
		}
		
		bonusScoreTime ++;
		
		if(bonusScoreTime == bonusScoreTargetTime)
		{
			bonusScoreTime = 0;
			
			switch(bonusScore)
			{
				case 200:imageIndex2++; break;
				case 400:imageIndex4++; break;
				case 800:imageIndex8++; break;
				case 1600:imageIndex16++; break;
			}
		}
	}
	
	public void tick()
	{	
		movement();
		colisions();
		positioning();
		animation();
	}
}