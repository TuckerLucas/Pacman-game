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
	
	public static List<Food> food;					// Food list
	public static List<Energizer> energizers; 		// Energizer list
	
	// Vertical coordinate for data presentation
	public static int INFO_Y = 740;
	
	// Variables for player score data presentation
	public static int SCORE = 15;
	public static int SCORE_SCORE = 95;
	public static int HIGHSCORE = 180;
	public static int HIGHSCORE_HIGHSCORE = 320;
	
	// Variables for player's life data presentation
	public static int LIVES = 450;
	public static int LIFE1 = 530;
	public static int LIFE2 = 567;
	public static int LIFE3 = 604;
	
	// Variables for object loading via color identification
	public static final int BLACK 	= 0xFF000000;
	public static final int PINK 	= 0xFFFF00FF;
	public static final int BLUE	= 0xFF0000FF;
	public static final int RED		= 0xFFFF0000;
	public static final int CYAN	= 0xFF00FFFF;
	public static final int ORANGE	= 0xFFFF8726;
	public static final int PURPLE	= 0xFFE7CCFF;
	public static final int WHITE	= 0xFFFFFFFF;
	public static final int YELLOW	= 0xFFFFFF00;
	
	public Level(String path)	
	{
		food = new ArrayList<>();
		energizers = new ArrayList<>();
		
		try 
		{
			// Get map sketch image via the passed path
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			Level.width = map.getWidth();
			Level.height = map.getHeight();
			
			tiles = new Tile[width][height];
			
			int pixels[] = new int[width * height];	
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)
			{
				for(int yy = 0; yy < height; yy++)
				{
					int color = pixels[xx + (yy*width)];
					
					// Color identification for object loading
					switch(color)
					{
						case BLACK:
							
							tiles[xx][yy] = new Tile(xx*32, yy*32);
							
							break;
							
						case PINK:			
							
							Game.door.x = xx*32;
							Game.door.y = yy*32;
							
							break;
							
						case BLUE:			
							
							Game.pacman.x = xx*32;
							Game.pacman.y = yy*32;
							
							break;
							
						case RED:			
							
							Game.Blinky.x = xx*32;
							Game.Blinky.y = yy*32;
							
							break;
							
						case CYAN:			
							
							Game.Inky.x = xx*32;
							Game.Inky.y = yy*32;
							
							break;
							
						case PURPLE:
							
							Game.Pinky.x = xx*32;
							Game.Pinky.y = yy*32;
							
							break;
							
						case ORANGE:
							
							Game.Clyde.x = xx*32;
							Game.Clyde.y = yy*32;
							
							break;
							
						case YELLOW:
							
							energizers.add(new Energizer(xx*32, yy*32));
							
							break;
							
						case WHITE:
							
							food.add(new Food(xx*32, yy*32));
							
							break;
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
		g.setColor(Color.white);
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 23));
		
		g.drawString("SCORE: ", SCORE, INFO_Y);	
		g.drawString(String.valueOf(Game.score), SCORE_SCORE, INFO_Y);
		
		g.drawString("HIGHSCORE: ", HIGHSCORE, INFO_Y);	
		g.drawString(String.valueOf(Game.highscore), HIGHSCORE_HIGHSCORE, INFO_Y);
		
		g.drawString("LIVES:", LIVES, INFO_Y);
		
		switch(Pacman.lives) 
		{
			case 3:
				
				g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
				g.drawImage(Texture.getSprite(32, 0), LIFE2, 710, 32, 32, null);
				g.drawImage(Texture.getSprite(32, 0), LIFE3, 710, 32, 32, null);
				
				break;
				
			case 2:
				
				g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
				g.drawImage(Texture.getSprite(32, 0), LIFE2, 710, 32, 32, null);
				
				break;
				
			case 1:
				
				g.drawImage(Texture.getSprite(32, 0), LIFE1, 710, 32, 32, null);
				
				break;	
		}
	}
	
	public static void render(Graphics g)
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(tiles[x][y] != null)
					tiles[x][y].render(g);
			}
		}
		
		for(int i = 0; i < food.size(); i++) 
				food.get(i).render(g);
		
		for(int i = 0; i < energizers.size(); i++) 
			energizers.get(i).render(g);
		
		Game.pacman.render(g);
		Game.Blinky.render(g);
		Game.Inky.render(g);
		Game.Pinky.render(g);
		Game.Clyde.render(g);
		Game.door.render(g);
		draw_data(g);
	}
}
