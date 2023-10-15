/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Level.java
* 
* Description: 
* 
* This file is responsible for loading the game objects in the 
* map as well as relevant game data regarding score, highscore
* and lives.
* 
/**************************************************************/

package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level
{
	// Variables for game size
	private static int gameWidth;
	private static int gameHeight;
	
	// Game tiles (walls) matrix 
	public static Wall[][] walls;
		
	// Variables for object loading via colour identification
	private final int black 	= 0xFF000000;
	private final int gray		= 0xFF808080;
	private final int darkYellow = 0xFFFFD800;
	private final int red 		= 0xFFFF0000;
	private final int cyan 		= 0xFF00FFFF;
	private final int orange 	= 0xFFFF8726;
	private final int purple 	= 0xFFE7CCFF;
	private final int white 	= 0xFFFFFFFF;
	private final int lightYellow 	= 0xFFFFFF00;
	
	// Constructor
	public Level()	
	{
		Food.food = new ArrayList<>();	
		
		try 
		{
			// Get map sketch image via the passed path
			BufferedImage map = ImageIO.read(getClass().getResource("/Images/map.png"));
			
			Level.gameWidth = map.getWidth();		
			Level.gameHeight = map.getHeight();		
			
			walls = new Wall[gameWidth][gameHeight];
			
			// Get RGB array of the whole map and store it
			int pixels[] = new int[gameWidth * gameHeight];	
			map.getRGB(0, 0, gameWidth, gameHeight, pixels, 0, gameWidth);
			
			// Analyse RGB array line by line
			for(int x = 0; x < gameWidth; x++)
			{
				for(int y = 0; y < gameHeight; y++)
				{
					int color = pixels[x + (y * gameWidth)];
					
					// Load objects based on map sketch color
					switch(color)
					{
						case black:
							
							walls[x][y] = new Wall(x*32, y*32);
							
							break;
							
						case gray:			
							
							Game.spawnBoxDoor.x = x*32;
							Game.spawnBoxDoor.y = y*32;
							
							break;
							
						case darkYellow:			
							
							Game.pacman.x = x*32;
							Game.pacman.y = y*32;
							
							break;
							
						case red:			
							
							Game.ghostArray[0].x = x*32;
							Game.ghostArray[0].y = y*32;
							
							break;
							
						case cyan:			
							
							Game.ghostArray[1].x = x*32;
							Game.ghostArray[1].y = y*32;
							
							break;
							
						case purple:
							
							Game.ghostArray[2].x = x*32;
							Game.ghostArray[2].y = y*32;
							
							break;
							
						case orange:
							
							Game.ghostArray[3].x = x*32;
							Game.ghostArray[3].y = y*32;
							
							break;
							
						case white:
							
							Food.food.add(new Pellet(x*32, y*32));
							
							break;
							
						case lightYellow:
							
							Food.food.add(new Energizer(x*32, y*32));
							
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

	// Draw user score and lives data at the bottom of the game screen
	private static void drawData(Graphics g)
	{	
		// Variable for vertical data positioning in game window
		int gameDataY = 745;
		
		// Variables for horizontal score data positioning in game window
		int scoreStrX = 20;			
		int scoreValX = 105;		
		int highscoreStrX = 210;	
		int highscoreValX = 350;	
		
		// Set letter color size and font
		Game.setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 23);
		
		// Draw game score and highscore data
		/*******************************************************/
		g.drawString("SCORE: ", scoreStrX, gameDataY);	
		g.drawString(String.valueOf(Game.score), scoreValX, gameDataY);
		g.drawString("HIGHSCORE: ", highscoreStrX, gameDataY);	
		g.drawString(String.valueOf(Game.highscore), highscoreValX, gameDataY);
		/*******************************************************/
		
		// Variables for life data positioning in game window
		int livesStrX = 470;
		int livesSymbolsY = 720;
		int life1SymbolX = 555;
		int life2SymbolX = 590;
		int life3SymbolX = 625;
		int livesSymbolsX[] = {life1SymbolX, life2SymbolX, life3SymbolX};
		
		// Draw game lives data
		/*******************************************************/
		g.drawString("LIVES:", livesStrX, gameDataY);
		
		// Draw life symbols depending on number of user lives left
		for(int i = 0; i < Game.lives; i++)
		{
			g.drawImage(Texture.getSprite(Texture.spriteColumn7, Texture.spriteLine5, Texture.spriteSize, Texture.spriteSize), livesSymbolsX[i], 
					livesSymbolsY, 32, 32, null);
		}
		/*******************************************************/
	}
	
	public void render(Graphics g)
	{
		for(int x = 0; x < gameWidth; x++)
		{
			for(int y = 0; y < gameHeight; y++)
			{
				if(walls[x][y] != null)
				{
					walls[x][y].render(g);
				}
			}
		}
		
		for(int i = 0; i < Food.food.size(); i++)
		{	
			Food.food.get(i).render(g);
		}
		
		Game.pacman.render(g);
		
		if(Game.gameStatus != Game.lifeLost)
		{
			Game.ghostArray[0].render(g);
			Game.ghostArray[1].render(g);
			Game.ghostArray[2].render(g);
			Game.ghostArray[3].render(g);
		}

		Game.spawnBoxDoor.render(g);
		Game.bonusScore.render(g);
		
		drawData(g);
	}
	
	public void tick()
	{
		if(Food.food.size() == 0)
		{
			Game.gameStatus = Game.win;
		}
	}
}