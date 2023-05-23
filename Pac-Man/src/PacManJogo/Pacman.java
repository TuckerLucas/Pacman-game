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
	public static boolean sounds = false;
	
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
	
	public static boolean score2 		= false;
	public static boolean score4 		= false;
	public static boolean score8 		= false;
	public static boolean score16 		= false;
	public static boolean stop_score 	= false;
	
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
		
		if(score2 == true || score4 == true || score8 == true || score16 == true)
		{
			if(timesScoreFlashed == 3)
			{
				timesScoreFlashed = 0;
				score2  = false;
				score4  = false;
				score8  = false; 
				score16 = false;
			}
			else 
			{
				if(score2 == true)
				{
					if(imageIndex2 >= 10)
					{
						imageIndex2 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.two_hundred[imageIndex2], Game.x_position, Game.y_position, width, height, null);
				}
				if(score4 == true)
				{
					if(imageIndex4 >= 10)
					{
						imageIndex4 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.four_hundred[imageIndex4], Game.x_position, Game.y_position, width, height, null);
				}
				if(score8 == true)
				{
					if(imageIndex8 >= 10)
					{
						imageIndex8 = 0;
						timesScoreFlashed++;
					}
					g.drawImage(Texture.eight_hundred[imageIndex8], Game.x_position, Game.y_position, width, height, null);
				}
				if(score16 == true)
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
		if(sounds == true)
		{
			new Sounds(filepath);
		}
	}
	
	
	private boolean canMove(int nextx, int nexty)
	{
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
	
	public void move_right()
	{
		if(canMove(x+speed, y))
		{
			x+=speed;
			lastDir = right;
		}
		else
		{
			if(lastDir == left && canMove(x-speed, y))
			{
				x-=speed;
			}
			if(lastDir == up && canMove(x, y-speed))
			{
				y-=speed;
			}
			if(lastDir == down && canMove(x, y+speed))
			{
				y+=speed;
			}
		}
	}
	
	public void move_left()
	{
		if(canMove(x-speed, y))
		{
			x-=speed;
			lastDir = left;
		}
		else
		{
			if(lastDir == right && canMove(x+speed, y))
			{
				x+=speed;
			}
			if(lastDir == up && canMove(x, y-speed))
			{
				y-=speed;
			}
			if(lastDir == down && canMove(x, y+speed))
			{
				y+=speed;
			}
		}
	}
	
	public void move_up()
	{
		if(canMove(x, y-speed))
		{
			y-=speed;
			lastDir = up;
		}
		else
		{
			if(lastDir == left && canMove(x-speed, y))
			{
				x-=speed;
			}
			if(lastDir == 1 && canMove(x+speed, y))
			{
				x+=speed;
			}
			if(lastDir == 4 && canMove(x, y+speed))
			{
				y+=speed;
			}
		}
	}

	public void move_down()
	{
		if(x == 320 && y == 256)					
		{
			if(lastDir == left && canMove(x-speed, y))
			{
				x-=speed;
			}
			if(lastDir == up && canMove(x, y-speed))
			{
				y-=speed;
			}
			if(lastDir == 1 && canMove(x+speed, y))
			{
				x+=speed;
			}
		}
		else
		{
			if(canMove(x, y+speed))
			{
				y+=speed;
				lastDir = 4;
			}
			else
			{
				if(lastDir == left && canMove(x-speed, y))
				{
					x-=speed;
				}
				if(lastDir == up && canMove(x, y-speed))
				{
					y-=speed;
				}
				if(lastDir == right && canMove(x+speed, y))
				{
					x+=speed;
				}
			}
		}
	}

	
	
	public void colision_with_food()
	{
		if(Level.apples.size() == 0 && Level.energizers.size() == 0)	
		{
			Game.STATE = Game.WIN;	
			
			energizer_status = false;				
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			energizer_time = 0;	
			return;
		}
		
		for(int i = 0; i < Level.apples.size(); i++) 		//Interce��o com comida 
		{
			String filepath = "res\\Sounds\\PacMan Eating.wav";
            
			if(this.intersects(Level.apples.get(i)))							
			{
				playSound(filepath);
				
				Level.apples.remove(i);		
				Game.score+=10;
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				break;
			}
			
		}
		
		for(int i = 0; i < Level.energizers.size(); i++) 	//Interce��o com energizer
		{
			String filepath = "res\\Sounds\\Energizer.wav";
			if(this.intersects(Level.energizers.get(i)))						
			{
				playSound(filepath);
				
				Level.energizers.remove(i);
				
				Game.score+=50;
				
				if(Game.score >= Game.highscore)
				{
					Game.highscore = Game.score;
				}
				
				energizer_status = true;					//Estado energizer ativado
				energizer_time = 0;							//Reset tempo energizer
				
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
					
					Game.STATE = Game.LOSE;
				}
				else 
				{
					Game.STATE = Game.LIFE_LOST;
				}	
			}
		}
	}
		
	
	
	public static void make_Blinky_flash()
	{
		energizer_flash = true;
	}
	
	public void keep_Blinky_blue()
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
				Game.STATE = Game.LOSE;
			}
			else 
			{
				Game.STATE = Game.LIFE_LOST;
			}
			
			energizer_status = false;				
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			energizer_time = 0;	
			
			score2 = false;
			score4 = false;
			score8 = false;
			score16 = false;
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
				Game.STATE = Game.LOSE;
			}
			else 
			{
				Game.STATE = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			score2 = false;
			score4 = false;
			score8 = false;
			score16 = false;
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
				Game.STATE = Game.LOSE;
			}
			else 
			{
				Game.STATE = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			score2 = false;
			score4 = false;
			score8 = false;
			score16 = false;
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
				Game.STATE = Game.LOSE;
			}
			else 
			{
				Game.STATE = Game.LIFE_LOST;
			}
			
			energizer_status = false;										
			
			Game.Blinky.eaten = false;													
			Game.Inky.eaten = false;
			Game.Pinky.eaten = false;
			Game.Clyde.eaten = false;
			
			score2 = false;
			score4 = false;
			score8 = false;
			score16 = false;
		}
	}

	
	
	public void make_200_appear()
	{
		score2 = true;
		score16 = false;
		score8 = false;
		score4 = false;
	}
	
	public void make_400_appear()
	{
		score4 = true;
		score2 = false;
		score16 = false;
		score8 = false;
	}
	
	public void make_800_appear()
	{
		score8 = true;
		score4 = false;
		score2 = false;
		score16 = false;
	}
	
	public void make_1600_appear()
	{
		score16 = true;
		score8 = false;
		score4 = false;
		score2 = false;
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
			make_Blinky_flash();
		}
		else if(energizer_time < energizer_flashTime)
		{
			keep_Blinky_blue();
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
						stop_score = false;
						make_200_appear();
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_400_appear();
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_800_appear();
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_1600_appear();
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
						stop_score = false;
						make_200_appear();
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_400_appear();
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_800_appear();
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_1600_appear();
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
						stop_score = false;
						make_200_appear();
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_400_appear();
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_800_appear();
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_1600_appear();
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
						stop_score = false;
						make_200_appear();
					}
					else if(ghosts_eaten == 2)
					{
						Game.score = Game.score + 400;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_400_appear();
					}
					else if(ghosts_eaten == 3)
					{
						Game.score = Game.score + 800;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_800_appear();
					}
					else if(ghosts_eaten == 4)
					{
						Game.score = Game.score + 1600;
						Game.x_position = x;
						Game.y_position = y;
						stop_score = false;
						make_1600_appear();
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
		if(dir == right)
		{
			move_right();
		}
		else if(dir == left)
		{
			move_left();
		}
		else if(dir == up)
		{
			move_up();
		}
		else if(dir == down)
		{
			move_down();
		}
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
		
		if(score2 == true)
		{
			time2 ++;
			
			if(time2 == targetTime2)
			{
				time2 = 0;
				imageIndex2++;
			}
		}
		if(score4 == true)
		{
			time4 ++;
			
			if(time4 == targetTime4)
			{
				time4 = 0;
				imageIndex4 ++;
			}
		}
		if(score8 == true)
		{
			time8 ++;
			
			if(time8 == targetTime8)
			{
				time8 = 0;
				imageIndex8 ++;
			}
		}
		if(score16 == true)
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
