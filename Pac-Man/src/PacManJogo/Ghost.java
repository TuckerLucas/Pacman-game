package PacManJogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ghost extends Rectangle
{
	private static final long serialVersionUID = 1L;

	private int spd = 2;
	
	private Random randomGen;
	
	private int random = 0;
	private int smart  = 1;
	private int find_path = 2;
	private int state = random; 
	
	private int right = 0; 
	private int left  = 1;
	private int up 	  = 2;
	private int down  = 3;
	
	private int dir 	= -1;
	private int lastDir = -1;
	
	private int smart_time			= 0;
	private int smart_targetTime; 
	private boolean coolDown		= false;
	private int coolDownTime    	= 0;
	private int coolDownTargetTime	= 60*3; 
	private int timeImage 			= 0;		
	private int targetTimeImage 	= 4;
	
	private boolean zone1 	= false;
	private boolean zone2 	= false;
	private boolean zone3 	= false;
	private boolean zone4 	= false;
	private boolean zone5 	= false;
	private boolean zone6 	= false;
	private boolean zone7 	= false;
	private boolean zone8 	= false;
	private boolean zone9 	= false;
	private boolean zone10 	= false;
	private boolean zone11 	= false;
	private boolean zone12 	= false;
	private boolean zone13 	= false;
	private boolean zone14 	= false;
	private boolean zone15 	= false;
	private boolean zone16 	= false;
	
	private boolean left4 	= false;
	private boolean right4 	= false;
	private boolean left13 	= false;
	private boolean right13 = false;
	private boolean up8 	= false;
	private boolean down8 	= false;
	private boolean up9 	= false;
	private boolean down9 	= false;
	
	private int difx;
	private int dify;
	private int radius;
	
	private int crossmap = -1;
	private int imageIndexEnemy = 0;
	
	public boolean eaten;
	private int enemyID;
	
	public Ghost(int x, int y, int ID, int ld, int cm)
	{	
		setBounds(x,y,32,32);
		
		lastDir = ld;
		crossmap = cm;
		
		switch(Game.difficulty)
		{
			case 1:
				radius = 80;
				smart_targetTime = 60 * 5;
				break;
			case 2:
				radius = 100;
				smart_targetTime = 60 * 8;
				break;
			case 3:
				radius = 120;
				smart_targetTime = 60 * 12;
				break;
			case 4:
				radius = 150;
				smart_targetTime = 60 * 20;
				break;
		}
		
		if(lastDir == left)
		{
			move(left);
		}
		else if(lastDir == right)
		{
			move(right);
		}

		randomGen = new Random();
		dir = randomGen.nextInt(4);
			
		enemyID = ID;
	}
	
	private void setDirection(int dir)
	{
		switch(dir)
		{
			case 0: x+=spd; lastDir = right; break;
				
			case 1: x-=spd; lastDir = left; break;
			
			case 2: y-=spd; lastDir = up; break;
			
			case 3: y+=spd; lastDir = down; break;
		}
	}
	
	private void move(int dir)
	{
		if(canMove(dir))
			setDirection(dir);
	}
	
	private boolean canMove(int dir)
	{
		int nextx = 0, nexty = 0;
		
		switch(dir)
		{
			case 0:	nextx = x+spd; nexty = y; break;
						
			case 1:	nextx = x-spd; nexty = y; break;
						
			case 2:	nextx = x; nexty = y-spd; break;
			
			case 3:	if(x == 320 && y == 256) {return false;}			// Prevent ghosts from reentering spawn box
						
					nextx = x; nexty = y+spd; break;
		}
		
		Rectangle bounds = new Rectangle(nextx, nexty, width, height);
		
		for(int xx = 0; xx < Level.tiles.length; xx++)
		{
			for(int yy = 0; yy < Level.tiles[0].length; yy++)
			{
				if(Level.tiles[xx][yy] != null)								//se != null significa que nos deparamos com uma parede
				{
					if(bounds.intersects(Level.tiles[xx][yy]))				//intercetamos uma parede, logo j� n�o nos podemos deslocar nesta dire��o
					{
						return false;								
					}
				}
			}
		}	
		return true;
	}
	
	private void update_zone(int current_zone)
	{
		reset_zones();
		
		switch(current_zone)
		{
			case 1:
				zone1 = true;
				break;
			case 2:
				zone2 = true;
				break;
			case 3:
				zone3 = true;
				break;
			case 4:
				zone4 = true;
				break;
			case 5:
				zone5 = true;
				break;
			case 6:
				zone6 = true;
				break;
			case 7:
				zone7 = true;
				break;
			case 8:
				zone8 = true;
				break;
			case 9:
				zone9 = true;
				break;
			case 10:
				zone10 = true;
				break;
			case 11:
				zone11 = true;
				break;
			case 12:
				zone12 = true;
				break;
			case 13:
				zone13 = true;
				break;
			case 14:
				zone14 = true;
				break;
			case 15:
				zone15 = true;
				break;
			case 16:
				zone16 = true;
				break;
		}
	}
	
	private void reset_zones()
	{
		zone1 	= false;
		zone2 	= false;
		zone3 	= false;
		zone4 	= false;
		zone5 	= false;
		zone6 	= false;
		zone7 	= false;
		zone8 	= false;
		zone9 	= false;
		zone10 	= false;
		zone11 	= false;
		zone12 	= false;
		zone13 	= false;
		zone14 	= false;
		zone15 	= false;
		zone16 	= false;
	}

	
	private void get_to_zone(int dir1, int dir2)
	{
		if(canMove(dir1))
		{
			move(dir1);
		}
		else if(canMove(dir2))
		{
			move(dir2);
		}
		else
		{
			state = find_path;
		}
	}
	
	
	private void move_to_zone(int zone)
	{
		update_zone(zone);
		
		switch(zone)
		{
			case 1: get_to_zone(left, up);
					break;
					
			case 2: get_to_zone(up, left);
					break;
				
			case 3: get_to_zone(up, left);
					break;
					
			case 4: 
				if(canMove(up))
				{
					move(up);
				}
				else if(canMove(left))
				{
					left4 = true;
					state = find_path;
				}
				else if(canMove(right))
				{
					right4 = true;
					state = find_path;
				}
				break;
				
			case 5: get_to_zone(up, right);
					break;

			case 6: get_to_zone(up, right);
					break;
					
			case 7: get_to_zone(right, up);
					break;
					
			case 8: 
				if(canMove(left))
				{
					move(left);
				}
				else if(canMove(up))
				{
					up8 = true;
					state = find_path;
				}
				else if(canMove(down))
				{
					down8 = true;
					state = find_path;
				}
				break;
				
			case 9: 
				if(canMove(right))
				{
					move(right);
				}
				else if(canMove(up))
				{
					up9 = true;
					state = find_path;
				}
				else if(canMove(down))
				{
					down9 = true;
					state = find_path;
				}
				break;
				
			case 10: get_to_zone(left, down);
					 break;

			case 11: get_to_zone(down, left);
					 break;
					 
			case 12: get_to_zone(down, left);
					 break;
					 
			case 13: 
				if(canMove(down))
				{
					move(down);
				}
				else if(canMove(left))
				{
					left13 = true;
					state = find_path;
				}
				else if(canMove(right))
				{
					
					right13 = true;
					state = find_path;
				}
				break;
				
			case 14: get_to_zone(down, right);
					 break;
					
			case 15: get_to_zone(down, right);
					 break;
					 
			case 16: get_to_zone(right, down);
					 break;
		}
	}

	private void move_randomly()
	{
		difx = x - Game.pacman.x;
		dify = y - Game.pacman.y;
		
		if(coolDown == true)
		{
			coolDownTime++;
			
			if(coolDownTime == coolDownTargetTime)
			{
				coolDown = false;
				coolDownTime = 0;
			}
		}
		else if(coolDown == false)
		{
			if((difx < radius && difx > -radius) && (dify < radius && dify > -radius))
			{
				if(Pacman.energizer_status == false && !inBox())
				{
					state = smart;
				}
			}
		}
		if(crossmap == left)
		{
			lastDir = left;
			
			x -= spd;
			
			if(x == 480 && y == 320 || eaten == true)
			{
				crossmap = -1;
			}
		}
		else if(crossmap == right)
		{
			lastDir = right;
			
			x += spd;
			
			if(x == 160 && y == 320 || eaten == true)
			{
				crossmap = -1;
			}
		}
		else if(crossmap == -1)
		{
			if(dir == right)
			{
				if(canMove(right))			
				{
					move(right);
					dir = randomGen.nextInt(4);	//gerar dire��o aleat�ria
				}
				else							
				{
					if(lastDir == left && canMove(left))
					{
						move(left);
					}
					else if(lastDir == up && canMove(up))
					{
						move(up);
					}
					else if(lastDir == down && canMove(down))
					{
						move(down);
					}
					else 
					{
						dir = randomGen.nextInt(4); 
					}
				}
			}
			else if(dir == left)
			{
				if(canMove(left))			
				{
					move(left);
					dir = randomGen.nextInt(4);
				}
				else							
				{
					if(lastDir == right && canMove(right))
					{
						x+=spd;
					}
					else if(lastDir == up && canMove(up))
					{
						y-=spd;
					}
					else if(lastDir == down && canMove(down))
					{
						y+=spd;
					}
					else 
					{
						dir = randomGen.nextInt(4); 
					}
				}
			}
			else if(dir == up)
			{
				if(canMove(up))			
				{
					move(up);
					dir = randomGen.nextInt(4);
				}
				else							
				{
					if(lastDir == left && canMove(left))
					{
						x-=spd;
					}
					else if(lastDir == right && canMove(right))
					{
						x+=spd;
					}
					else if(lastDir == down && canMove(down))
					{
						y+=spd;
					}
					else 
					{
						dir = randomGen.nextInt(4); //escolher outra dire��o aleatoria
					}
				}
			}
			else if(dir == down)
			{
				if(x == 320 && y == 256)
				{
					if(lastDir == left && canMove(left))
					{
						x-=spd;
					}
					else if(lastDir == up && canMove(up))
					{
						y-=spd;
					}
					else if(lastDir == right && canMove(right))
					{
						x+=spd;
					}
					else 
					{
						dir = randomGen.nextInt(4); 
					}
				}
				else
				{
					if(canMove(down))			
					{
						move(down);
						dir = randomGen.nextInt(4);
					}
					else							
					{
						if(lastDir == 1 && canMove(left))
						{
							x-=spd;
						}
						else if(lastDir == 2 && canMove(up))
						{
							y-=spd;
						}
						else if(lastDir == 0 && canMove(right))
						{
							x+=spd;
						}
						else 
						{
							dir = randomGen.nextInt(4); 
						}
					}
				}	
			}
		}
	}
	
	private void move_smartly()
	{
		difx = x - Game.pacman.x;
		dify = y - Game.pacman.y;

		if(Pacman.energizer_status == true || inBox())
		{
			state = random;
		}
		
		if(crossmap == left)
		{
			lastDir = left;
			x-=spd;
			
			if(x == 480 && y == 320 || eaten == true)
			{
				crossmap = -1;
			}
		}
		else if(crossmap == right)
		{
			lastDir = right;
			x+=spd;
			
			if(x == 160 && y == 320 || eaten == true)
			{
				crossmap = -1;
			}
		}
		else if(crossmap == -1)
		{
			// Ghost in right portal moving right
			if((x == 490 && y == 320) && lastDir == right)
			{
				// Ensure ghost crosses portal to the other side of the map
				crossmap = right;
			}
			// Ghost in left portal moving left
			else if((x == 150 && y == 320) && lastDir == left)
			{
				// Ensure ghost crosses portal to the other side of the map
				crossmap = left;
			}
			else
			{
				if(difx > 0 && dify > 0 && difx > dify)			//Zona 1
				{
					move_to_zone(1);
				}
				else if(difx > 0 && dify > 0 && difx == dify)	//Zona 2				
				{
					move_to_zone(2);
				}
				else if(difx > 0 && dify > 0 && dify > difx)	//Zona 3					
				{
					move_to_zone(3);
				}
				else if(difx == 0 && dify > 0)					//Zona 4				
				{
					move_to_zone(4);
				}
				else if(difx < 0 && dify > 0 && dify > -difx)	//Zona 5					
				{
					move_to_zone(5);
				}
				else if(difx < 0 && dify > 0 && -difx == dify)	//Zona 6					
				{
					move_to_zone(6);
				}
				else if(difx < 0 && dify > 0 && -difx > dify)	//Zona 7					
				{
					move_to_zone(7);
				}
				else if(difx > 0 && dify == 0)					//Zona 8					
				{
					move_to_zone(8);
				}
				else if(difx < 0 && dify == 0)					//Zona 9					
				{
					move_to_zone(9);
				}
				else if(difx > 0 && dify < 0 && difx > -dify)	//Zona 10 					
				{
					move_to_zone(10);
				}
				else if(difx > 0 && dify < 0 && difx == -dify)	//Zona 11 					
				{
					move_to_zone(11);
				}
				else if(difx > 0 && dify < 0 && -dify > difx)	//Zona 12					
				{
					move_to_zone(12);
				}
				else if(difx == 0 && dify < 0)					//Zona 13					
				{
					move_to_zone(13);
				}
				else if(difx < 0 && dify < 0 && -dify > -difx)	//Zona 14					
				{
					move_to_zone(14);
				}
				else if(difx < 0 && dify < 0 && -dify == -difx)	//Zona 15					
				{
					move_to_zone(15);
				}
				else if(difx < 0 && dify < 0 && -difx > -dify)	//Zona 16					
				{
					move_to_zone(16);
				}
			}
			
		}
		smart_time++;								
		
		if(smart_time == smart_targetTime) 				
		{
			coolDown = true;
			state = random;							
			smart_time = 0;						
		}
	}

	private void finding_path()
	{
		if(zone1)
		{
			move_until(down, left);
		}
		else if(zone2)
		{
			move_until(right, up);
		}
		else if(zone3)
		{
			move_until(right, up);
		}
		else if(zone4)
		{
			if(left4 == true)
			{
				if(canMove(left))
				{
					move_until(left, up);
				}
				else
				{
					left4 = false;
					zone4 = false;
					move_until(-1, -1);
				}
			}
			else if(right4 == true)
			{
				if(canMove(right))
				{
					move_until(right, up);
				}
				else
				{
					right4 = false;
					zone4 = false;
					move_until(-1, -1);
				}
			}
		}
		else if(zone5)
		{
			move_until(left, up);
		}
		else if(zone6)
		{
			move_until(left, up);
		}
		else if(zone7)
		{
			move_until(down, right);
		}
		else if(zone8)
		{
			if(up8 == true)
			{
				if(canMove(up))
				{
					move_until(up, left);
				}
				else
				{
					up8 = false;
					zone8 = false;
					move_until(-1,-1);
				}
			}
			else if(down8 == true)
			{
				if(canMove(down))
				{
					move_until(down, left);
				}
				else
				{
					down8 = false;
					zone8 = false;
					move_until(-1,-1);
				}
			}
		}
		else if(zone9)
		{
			if(up9 == true)
			{
				if(canMove(up))
				{
					move_until(up, right);
				}
				else
				{
					up9 = false;
					zone9 = false;
					move_until(-1,-1);
				}
			}
			else if(down9 == true)
			{
				if(canMove(down))
				{
					move_until(down, right);
				}
				else
				{
					down9 = false;
					zone9 = false;
					move_until(-1,-1);
				}
			}
		}
		else if(zone10)
		{
			move_until(up, left);
		}
		else if(zone11)
		{
			move_until(right, down);
		}
		else if(zone12)
		{
			move_until(right, down);
		}
		else if(zone13)
		{
			if(left13 == true)
			{
				if(canMove(left))
				{
					move_until(left, down);
				}
				else
				{
					zone13 = false;
					left13 = false;
					move_until(-1,-1);
				}
			}
			else if(right13 == true)
			{
				if(canMove(right))
				{
					move_until(right, down);
				}
				else
				{
					zone13 = false;
					right13 = false;
					move_until(-1,-1);
				}
			}
		}
		else if(zone14)
		{
			move_until(left, down);
		}
		else if(zone15)
		{
			move_until(left, down);
		}
		else if(zone16)
		{
			move_until(up, right);
		}
	}

	private void move_until(int allowed_direction, int desired_direction)
	{
		if(allowed_direction == right)
		{
			move(right);
			
			if(desired_direction == up && canMove(up))
			{
				right4 = false;
				state = smart;
			}
			else if(desired_direction == down && canMove(down))
			{
				right13 = false;
				state = smart;
			}
		}
		else if(allowed_direction == left)
		{
				move(left);
				
				if(desired_direction == up && canMove(up))
				{
					left4 = false;
					state = smart;
				}
				else if(desired_direction == down && canMove(down))
				{
					
					left13 = false;
					state = smart;
				}
		}
		else if(allowed_direction == down)
		{
			move(down);
			
			if(desired_direction == right && canMove(right))
			{
				down9 = false;
				state = smart;
			}
			else if(desired_direction == left && canMove(left))
			{
				down8 = false;
				state = smart;
			}
		}
		else if(allowed_direction == up)
		{
			move(up);
			
			if(desired_direction == right && canMove(right))
			{
				up9 = false;
				state = smart;
			}
			else if(desired_direction == left && canMove(left))
			{
				up8 = false;
				state = smart;
			}
		}
		else if(allowed_direction == -1)
		{
			state = smart;
		}
	}
	
	
	private boolean inBox()
	{
		if((x < 385 && x > 255) && (y < 385 && y > 255))
		{
			return true;
		}
		
		return false;
	}
	
	
	private void flash(Graphics g)
	{
		g.drawImage(Texture.flash[Pacman.imageIndexFlash], x, y, width, height, null);
	}
	
	private void stay_blue(Graphics g)
	{
		g.drawImage(Texture.blueghost[imageIndexEnemy], x, y, width, height, null);
	}
	

	private void look_right(Graphics g)
	{
		switch(enemyID)
		{
			case 0:
				g.drawImage(Texture.ghostr[imageIndexEnemy], x, y, width, height, null);
				break;
			case 1: 
				g.drawImage(Texture.ghost1r[imageIndexEnemy], x, y, width, height, null);
				break;
			case 2:
				g.drawImage(Texture.ghost2r[imageIndexEnemy], x, y, width, height, null);
				break;
			case 3:
				g.drawImage(Texture.ghost3r[imageIndexEnemy], x, y, width, height, null);
				break;
		}
		
	}
	
	private void look_left(Graphics g)
	{
		switch(enemyID)
		{
			case 0:
				g.drawImage(Texture.ghostl[imageIndexEnemy], x, y, width, height, null);
				break;
			case 1: 
				g.drawImage(Texture.ghost1l[imageIndexEnemy], x, y, width, height, null);
				break;
			case 2:
				g.drawImage(Texture.ghost2l[imageIndexEnemy], x, y, width, height, null);
				break;
			case 3:
				g.drawImage(Texture.ghost3l[imageIndexEnemy], x, y, width, height, null);
				break;
		}
	}
	
	private void look_up(Graphics g)
	{
		switch(enemyID)
		{
			case 0:
				g.drawImage(Texture.ghostu[imageIndexEnemy], x, y, width, height, null);
				break;
			case 1: 
				g.drawImage(Texture.ghost1u[imageIndexEnemy], x, y, width, height, null);
				break;
			case 2:
				g.drawImage(Texture.ghost2u[imageIndexEnemy], x, y, width, height, null);
				break;
			case 3:
				g.drawImage(Texture.ghost3u[imageIndexEnemy], x, y, width, height, null);
				break;
		}
	}
	
	private void look_down(Graphics g)
	{
		switch(enemyID)
		{
			case 0:
				g.drawImage(Texture.ghostd[imageIndexEnemy], x, y, width, height, null);
				break;
			case 1: 
				g.drawImage(Texture.ghost1d[imageIndexEnemy], x, y, width, height, null);
				break;
			case 2:
				g.drawImage(Texture.ghost2d[imageIndexEnemy], x, y, width, height, null);
				break;
			case 3:
				g.drawImage(Texture.ghost3d[imageIndexEnemy], x, y, width, height, null);
				break;
		}
	}
	
	
	private void enemy_movement()
	{
		if(state == random)							
		{
			move_randomly();		//andar aleatoriamente
		}
		else if(state == smart)
		{
			move_smartly();			//seguir Pacman
		}
		else if(state == find_path)
		{
			finding_path();			//mover-se quando im�vel
		}
	}
	
	private void positioning()
	{
		if(x == 0 && y == 320)								
		{
			lastDir = left;
			crossmap = left;
			
			switch(enemyID)
			{
				case 0:
					Game.Blinky = new Ghost(640, 320, 0, lastDir, crossmap);
					break;
				case 1:
					Game.Inky = new Ghost(640, 320, 1, lastDir, crossmap);
					break;
				case 2:
					Game.Pinky = new Ghost(640, 320, 2, lastDir, crossmap);
					break;
				case 3:
					Game.Clyde = new Ghost(640, 320, 3, lastDir, crossmap);
					break;
			}
		}
		else if(x == 640 && y == 320)
		{
			lastDir = right;
			crossmap = right;

			switch(enemyID)
			{
				case 0:
					Game.Blinky = new Ghost(0, 320, 0, lastDir, crossmap);
					break;
				case 1:
					Game.Inky = new Ghost(0, 320, 1, lastDir, crossmap);
					break;
				case 2:
					Game.Pinky = new Ghost(0, 320, 2, lastDir, crossmap);
					break;
				case 3:
					Game.Clyde = new Ghost(0, 320, 3, lastDir, crossmap);
					break;
			}
		}
	}
	
	private void animation()
	{
		timeImage ++;
		
		if(timeImage == targetTimeImage)
		{
			timeImage = 0;
			imageIndexEnemy ++;
		}
	}
	
	
	public void render(Graphics g)
	{
		if(imageIndexEnemy == 2)
		{
			imageIndexEnemy = 0;
		}

		if(Pacman.energizer_status == true)											//Estamos no estado energizer
		{
			if(eaten == true)													//Inimigo comido
			{
				if(lastDir == right)
				{
					look_right(g);		//sprite do inimigo (fantasma)
				}
				else if(lastDir == left)
				{
					look_left(g);		//sprite do inimigo (fantasma)
				}
				else if(lastDir == up)
				{
					look_up(g);		//sprite do inimigo (fantasma)
				}
				else if(lastDir == down)
				{
					look_down(g);		//sprite do inimigo (fantasma)
				}
			}
			else if(eaten == false)												//Inimigo n�o comido
			{
				if(Pacman.energizer_flash == false)
				{
					stay_blue(g);
				}
				else if(Pacman.energizer_flash == true)
				{
					flash(g);
				}
			}
		}
		else if(Pacman.energizer_status == false)									//N�o estamos no estado energizer
		{
			if(lastDir == right)
			{
				look_right(g);		//sprite do inimigo (fantasma)
			}
			else if(lastDir == left)
			{
				look_left(g);		//sprite do inimigo (fantasma)
			}
			else if(lastDir == up)
			{
				look_up(g);		//sprite do inimigo (fantasma)
			}
			else if(lastDir == down)
			{
				look_down(g);		//sprite do inimigo (fantasma)
			}	
		}	
	}
	
	public void tick()
	{			
		enemy_movement();
		positioning();
		animation();
	}
}
 