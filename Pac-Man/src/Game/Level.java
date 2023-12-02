package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level
{
	private static int gameWidth;
	private static int gameHeight;
		
	public static int objectWidth = 32;
	public static int objectHeight = 32;
	
	private final int black = 0xFF000000;
	private final int gray = 0xFF808080;
	private final int darkYellow = 0xFFFFD800;
	private final int red = 0xFFFF0000;
	private final int cyan = 0xFF00FFFF;
	private final int orange = 0xFFFF8726;
	private final int purple = 0xFFE7CCFF;
	private final int white = 0xFFFFFFFF;
	private final int lightYellow = 0xFFFFFF00;
	
	private String mapImagePath = "/Images/map.png";
	
	public static Level level;
	
	public Level()	
	{
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(mapImagePath));
			
			gameWidth = map.getWidth();		
			gameHeight = map.getHeight();		
			
			int pixels[] = map.getRGB(0, 0, gameWidth, gameHeight, null, 0, gameWidth);
			
			Wall.wallMatrix = new Wall[gameWidth][gameHeight];
			
			for(int x = 0; x < gameWidth; x++)
			{
				for(int y = 0; y < gameHeight; y++)
				{
					int color = pixels[x + (y * gameWidth)];
					
					switch(color)
					{
						case black:
							
							Wall.wallMatrix[x][y] = new Wall(x*32, y*32);
							break;
							
						case gray:			
							
							SpawnBoxDoor.spawnBoxDoor.x = x*32;
							SpawnBoxDoor.spawnBoxDoor.y = y*32;
							break;
							
						case darkYellow:			
							
							Pacman.pacman.x = x*32;
							Pacman.pacman.y = y*32;
							break;
						
						case red:			
							
							Ghost.ghostArray[0].x = x*32;
							Ghost.ghostArray[0].y = y*32;
							break;
							
						case cyan:			
							
							Ghost.ghostArray[1].x = x*32;
							Ghost.ghostArray[1].y = y*32;
							break;
							
						case purple:
							
							Ghost.ghostArray[2].x = x*32;
							Ghost.ghostArray[2].y = y*32;
							break;
							
						case orange:
							
							Ghost.ghostArray[3].x = x*32;
							Ghost.ghostArray[3].y = y*32;
							break;
							
						case white:
							
							Food.foodList.add(new Pellet(x*32, y*32));
							break;
							
						case lightYellow:
							
							Food.foodList.add(new Energizer(x*32, y*32));
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
	
	private static void drawData(Graphics g)
	{							
		Game.setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 23);
		
		g.drawString("SCORE: ", 20, 745);	
		g.drawString(String.valueOf(Game.score), 105, 745);
		g.drawString("HIGHSCORE: ", 210, 745);	
		g.drawString(String.valueOf(Game.highscore), 350, 745);
		g.drawString("LIVES:", 470, 745);
		
		for(int i = 0; i < Pacman.numberOfLives; i++)
		{
			g.drawImage(Animation.alivePacmanSprites[0][2], 555 + (35 * i), 720, 32, 32, null);
		}
	}
	
	public void render(Graphics g)
	{
		for(int x = 0; x < gameWidth; x++)
		{
			for(int y = 0; y < gameHeight; y++)
			{
				if(Wall.wallMatrix[x][y] != null)
				{
					Wall.wallMatrix[x][y].render(g);
				}
			}
		}
		
		for(int i = 0; i < Food.foodList.size(); i++)
		{	
			Food.foodList.get(i).render(g);
		}
		
		Pacman.pacman.render(g);
		
		if(Game.gameStatus != Game.lifeLost)
		{
			Ghost.ghostArray[0].render(g);
			Ghost.ghostArray[1].render(g);
			Ghost.ghostArray[2].render(g);
			Ghost.ghostArray[3].render(g);
		}

		SpawnBoxDoor.spawnBoxDoor.render(g);
		BonusScore.bonusScore.render(g);
		
		drawData(g);
	}
	
	public void tick()
	{
		if(Food.foodList.size() == 0)
		{
			Game.gameStatus = Game.win;
		}
	}
}