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

	public static int energizer_time       = 0;
	public static int energizer_targetTime = 60*8; 
	public static int energizer_flashTime  = 60*5;
	
	public static boolean energizer_flash  = false;
	public static boolean energizer_status = false;
	public static boolean soundOn = false;
	
	private int speed = 2;

	private int time 				  = 0;	//variaveis para temporiza��o da anima��o
	private int targetTime 			  = 8;
	private int imageIndex            = 0;
	public static int imageIndexFlash = 0;
	public static int lives 		  = 3;
	private int ghosts_eaten 		  = 0;
	
	private int time2 			 	= 0;
	private int targetTime2 		= 10;
	private int time4 			 	= 0;
	private int targetTime4 		= 10;
	private int time8 			 	= 0;
	private int targetTime8 		= 10;
	private int time16 			 	= 0;
	private int targetTime16 		= 10;
	public static int imageIndex2 	= 0;	
	public static int imageIndex4 	= 0;
	public static int imageIndex8 	= 0;
	public static int imageIndex16 	= 0;
	
	public static int bonusScore = -1;
	public static int score2 = 200;
	public static int score4 = 400;
	public static int score8 = 800;
	public static int score16 = 1600;
	public static boolean stopBonusScore = false;
	
	private int timesScoreFlashed = 0;
	
	public int current_score;
	
	public Pacman(int x, int y)
	{
		setBounds(x,y,32,32);
	}

	
	public void render(Graphics g)
	{
		if(imageIndex == 3)
		{
			imageIndex = 0;
		}
		
		if(lastDir == right)																	
		{
			g.drawImage(Texture.player[imageIndex], x, y, width, height, null);				//virar jogador para a direita		
		}
		else if(lastDir == left)
		{
			g.drawImage(Texture.player[imageIndex], x+32, y, -width, height, null);			//virar jogador para a esquerda
		}
		else if(lastDir == up)
		{
			g.drawImage(Texture.player1[imageIndex], x, y+32, width, -height, null);		//virar jogador para cima
		}
		else if(lastDir == down)
		{
			g.drawImage(Texture.player1[imageIndex], x, y, width, height, null);			//virar jogador para baixo
		}	
		
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
					{
						x-=speed;
					}
					if(lastDir == up && canMove(up))
					{
						y-=speed;
					}
					if(lastDir == down && canMove(down))
					{
						y+=speed;
					}
				
					break;
					
			case 2: 
					if(lastDir == right && canMove(right))
					{
						x+=speed;
					}
					if(lastDir == up && canMove(up))
					{
						y-=speed;
					}
					if(lastDir == down && canMove(down))
					{
						y+=speed;
					}
						
					break;
					
			case 3:
					if(lastDir == left && canMove(left))
					{
						x-=speed;
					}
					if(lastDir == right && canMove(right))
					{
						x+=speed;
					}
					if(lastDir == down && canMove(down))
					{
						y+=speed;
					}
					
					break;
			
			case 4:
					
					if(lastDir == left && canMove(left))
					{
						x-=speed;
					}
					if(lastDir == up && canMove(up))
					{
						y-=speed;
					}
					if(lastDir == right && canMove(right))
					{
						x+=speed;
					}
					
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
			
			energizer_status = false;				
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			energizer_time = 0;	
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
				if(energizer_status)
					bonusScore = -1;
				*/
				
				energizer_status = true;					// Energizer status activated
				energizer_time = 0;							// Reset the energizer timer
				
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
		if(energizer_status == true)					//Estamos no estado energizer
		{
			//timesScoreFlashed = 0;
			
			if(energizer_time == energizer_targetTime)					//Tempo do energizer terminou
			{
				energizer_time_over();									//Reset tempo energizer
			}
			else if(energizer_time < energizer_targetTime)				//Tempo do energizer n�o terminou
			{	
				energizer_time_not_over();											
			}	
		}
		else if(energizer_status == false)
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
		energizer_flash = true;
	}
	
	public void keepGhostBlue()
	{
		energizer_flash = false;
	}
	
	public void Blinky_eaten()
	{
		if(Game.Blinky.intersects(this))
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
			{
				Game.GAME_STATUS = Game.LIFE_LOST;
			}
			
			energizer_status = false;				
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			energizer_time = 0;	
			
			bonusScore = -1;
		}
	}

	public void Inky_eaten()
	{
		if(Game.Inky.intersects(this))
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
			{
				Game.GAME_STATUS = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			bonusScore = -1;
		}
	}
	
	public void Pinky_eaten()
	{
		if(Game.Pinky.intersects(this))
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
			{
				Game.GAME_STATUS = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			bonusScore = -1;
		}
	}

	public void Clyde_eaten()
	{
		if(Game.Clyde.intersects(this))
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
			{
				Game.GAME_STATUS = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			bonusScore = -1;
		}
	}

	
	public void showBonusScore(int bScore)
	{
		bonusScore = bScore;
	}
	
	public void energizer_time_over()
	{
		energizer_status = false;										//Terminou estado energizer
		
		Game.Blinky.eaten = false;													
		Game.Inky.eaten = false;
		Game.Pinky.eaten = false;
		Game.Clyde.eaten = false;
		
		energizer_time = 0;	

		ghosts_eaten = 0;
	}
	
	public void energizer_time_not_over()
	{
		if(energizer_time >= energizer_flashTime)
		{
			makeGhostFlash();
		}
		else if(energizer_time < energizer_flashTime)
		{
			keepGhostBlue();
		}
		
		if(Game.Blinky.eaten == true || Game.Inky.eaten == true || Game.Pinky.eaten == true || Game.Clyde.eaten == true)		
		{
			if(Game.Blinky.eaten == true)																
			{
				Blinky_eaten();
			}
			if(Game.Inky.eaten == true)																
			{
				Inky_eaten();
			}
			if(Game.Pinky.eaten == true)															
			{
				Pinky_eaten();
			}
			if(Game.Clyde.eaten == true)															
			{
				Clyde_eaten();
			}
		}
		if(Game.Blinky.eaten == false || Game.Inky.eaten == false || Game.Pinky.eaten == false || Game.Clyde.eaten == false)							
		{
			if(Game.Blinky.eaten == false)																
			{
				if(Game.Blinky.intersects(this))									
				{
					String filepath = "res\\Sounds\\Ghost Eaten.wav";
					playSound(filepath);	
					
					ghosts_eaten = ghosts_eaten + 1;
					if(ghosts_eaten == 1)
					{
						Game.score = Game.score + 200;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(200);
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(400);
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(800);
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(1600);
						ghosts_eaten = 0;
					}
					Game.Blinky = new Ghost(320, 320, 0, -1, -1);
					Game.Blinky.eaten = true;
				}
			}
			if(Game.Inky.eaten == false)																
			{
				if(Game.Inky.intersects(this))
				{
					String filepath = "res\\Sounds\\Ghost Eaten.wav";
					playSound(filepath);
					
					ghosts_eaten = ghosts_eaten + 1;
					
					if(ghosts_eaten == 1)
					{
						Game.score = Game.score + 200;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(200);
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(400);
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(800);
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(1600);
						ghosts_eaten = 0;
					}
					Game.Inky = new Ghost(320, 320, 1, -1, -1);
					Game.Inky.eaten = true;
				}
			}	
			if(Game.Pinky.eaten == false)																
			{
				if(Game.Pinky.intersects(this))
				{
					String filepath = "res\\Sounds\\Ghost Eaten.wav";
					playSound(filepath);
					
					ghosts_eaten = ghosts_eaten + 1;
					if(ghosts_eaten == 1)
					{
						Game.score = Game.score + 200;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(200);
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(400);
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(800);
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(1600);
						ghosts_eaten = 0;
					}
					Game.Pinky = new Ghost(320, 320, 2, -1, -1);
					Game.Pinky.eaten = true;
				}
			}
			if(Game.Clyde.eaten == false)																
			{
				if(Game.Clyde.intersects(this))
				{
					String filepath = "res\\Sounds\\Ghost Eaten.wav";
					playSound(filepath);
					
					ghosts_eaten = ghosts_eaten + 1;
					if(ghosts_eaten == 1)
					{
						Game.score = Game.score + 200;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(200);
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(400);
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(800);
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stopBonusScore = false;
						showBonusScore(1600);
						ghosts_eaten = 0;
					}						
					Game.Clyde = new Ghost(320, 320, 3, -1, -1);
					Game.Clyde.eaten = true;
				}
			}
		}
		
		energizer_time ++;	
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
		if(x == 0 && y == 320)			//Portal da esquerda							
		{
			Game.pacman = new Pacman(640, 320);						
		}
		else if(x == 640 && y == 320)	//Portal da direita
		{
			Game.pacman = new Pacman(0, 320);
		}
	}
	
	public void animation()
	{
		time ++;
		
		if(time == targetTime)
		{
			time = 0;
			imageIndex ++;
			
			if(imageIndexFlash == 3)
			{
				imageIndexFlash = 0;
			}
			else
			{
				imageIndexFlash ++;
			}
		}
		
		if(bonusScore == 200)
		{
			time2 ++;
			
			if(time2 == targetTime2)
			{
				time2 = 0;
				imageIndex2++;
			}
		}
		if(bonusScore == 400)
		{
			time4 ++;
			
			if(time4 == targetTime4)
			{
				time4 = 0;
				imageIndex4 ++;
			}
		}
		if(bonusScore == 800)
		{
			time8 ++;
			
			if(time8 == targetTime8)
			{
				time8 = 0;
				imageIndex8 ++;
			}
		}
		if(bonusScore == 1600)
		{
			time16 ++;
			
			if(time16 == targetTime16)
			{
				time16 = 0;
				imageIndex16 ++;
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