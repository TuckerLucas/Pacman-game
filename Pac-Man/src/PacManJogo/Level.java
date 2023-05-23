package PacManJogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Level 
{
	public static int width;
	public static int height;
	
	public static Tile[][] tiles;
	
	public static List<Apple> apples;			// Apple list (Pacman food)
	public static List<Energizer> energizers; 	// Energizer list
	
	public static int INFO_Y = 745;
	public static int SCORE = 15;
	public static int SCORE_SCORE = 95;
	public static int HIGHSCORE = 180;
	public static int HIGHSCORE_HIGHSCORE = 320;
	
	public static int LIVES = 450;
	public static int LIFE1 = 530;
	public static int LIFE2 = 567;
	public static int LIFE3 = 604;
	
	public Level(String path)	//path para a imagem do mapa criado
	{
		apples = new ArrayList<>();
		energizers = new ArrayList<>();
		
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			Level.width = map.getWidth();
			Level.height = map.getHeight();
			
			tiles = new Tile[width][height];
			
			int[] pixels = new int[width * height];	
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)
			{
				for(int yy = 0; yy < height; yy++)
				{
					int val = pixels[xx + (yy*width)];
					
					//Identifica��o de cores
					if(val == 0xFF000000)		//cor preta						
					{
						tiles[xx][yy] = new Tile(xx*32, yy*32);
					}
					else if(val == 0xFFFF00FF)	//cor rosa
					{
						Game.door.x = xx*32;
						Game.door.y = yy*32;
					}
					else if(val == 0xFF0000FF)	//cor azul					
					{
						Game.pacman.x = xx*32;
						Game.pacman.y = yy*32;
					}
					else if(val == 0xFFFF0000)	//cor vermelha			
					{
						Game.Blinky.x = xx*32;
						Game.Blinky.y = yy*32;
					}
					else if(val == 0xFF00FFFF)	//cor ciano
					{
						Game.Inky.x = xx*32;
						Game.Inky.y = yy*32;
					}
					else if(val == 0xFFFF8726) //cor laranja
					{
						Game.Pinky.x = xx*32;
						Game.Pinky.y = yy*32;
					}
					else if(val == 0xFFE7CCFF) //cor roxa
					{
						Game.Clyde.x = xx*32;
						Game.Clyde.y = yy*32;
					}
					else if(val == 0xFFFFFFFF) //cor branca					
					{
						apples.add(new Apple(xx*32, yy*32));
					}
					else if(val == 0xFFFFFF00) //cor amarela			
					{
						energizers.add(new Energizer(xx*32, yy*32));
					}
				}	
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static void draw_data(Graphics g)
	{
		int boxWidth  = 672;						
		int boxHeight = 76;		
		int xx = 0;
		int yy = 708;
		g.setColor(new Color(0,0,0));
		g.fillRect(xx, yy, boxWidth, boxHeight);
		
		g.setColor(Color.white);
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 23));
		
		g.drawString("SCORE: ", SCORE, INFO_Y);	
		g.drawString(String.valueOf(Game.score), SCORE_SCORE, INFO_Y);
		
		g.drawString("HIGHSCORE: ", HIGHSCORE, INFO_Y);	
		g.drawString(String.valueOf(Game.highscore), HIGHSCORE_HIGHSCORE, INFO_Y);
		
		g.drawString("LIVES:", LIVES, INFO_Y);
		
		if(Pacman.lives == 3)
		{
			g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
			g.drawImage(Texture.getSprite(32, 0), LIFE2, 710, 32, 32, null);
			g.drawImage(Texture.getSprite(32, 0), LIFE3, 710, 32, 32, null);
		}
		else if(Pacman.lives == 2)
		{
			g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
			g.drawImage(Texture.getSprite(32, 0), LIFE2, 710, 32, 32, null);
		}
		else if(Pacman.lives == 1)
		{
			g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
		}
	}
	
	public static void render(Graphics g)
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(tiles[x][y] != null)
				{
					tiles[x][y].render(g);
				}				
			}
		}
		for(int i = 0; i < apples.size(); i++) 
		{
				apples.get(i).render(g);
		}
		for(int i = 0; i < energizers.size(); i++) 
		{
			energizers.get(i).render(g);
		}
		
		Game.pacman.render(g);
		Game.Blinky.render(g);
		Game.Inky.render(g);
		Game.Pinky.render(g);
		Game.Clyde.render(g);
		Game.door.render(g);
		draw_data(g);
	}
}
