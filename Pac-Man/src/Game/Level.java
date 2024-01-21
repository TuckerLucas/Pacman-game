package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import character.Ghost;
import food.Energizer;
import food.Pellet;
import main.GamePanel;

public class Level
{
	GamePanel gp;
	
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
	
	public Level(GamePanel gp)	
	{
		this.gp = gp;
		
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(mapImagePath));
			
			gameWidth = map.getWidth();		
			gameHeight = map.getHeight();		
			
			int pixels[] = map.getRGB(0, 0, gameWidth, gameHeight, null, 0, gameWidth);
			
			loadElementsByColor(pixels);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void loadElementsByColor(int pixels[])
	{
		gp.wallMatrix = new Wall[gameWidth][gameHeight];
		
		for(int x = 0; x < gameWidth; x++)
		{
			for(int y = 0; y < gameHeight; y++)
			{
				int color = pixels[x + (y * gameWidth)];
				
				switch(color)
				{
					case black:
						
						gp.wallMatrix[x][y] = new Wall(x*objectWidth, y*objectHeight);
						break;
						
					case gray:			
						
						gp.spawnBoxDoor.x = x*objectWidth;
						gp.spawnBoxDoor.y = y*objectHeight;
						break;
						
					case darkYellow:			
						
						gp.pacman.x = x*objectWidth;
						gp.pacman.y = y*objectHeight;
						break;
					
					case red:			
						
						Ghost.ghostArray[0].x = x*objectWidth;
						Ghost.ghostArray[0].y = y*objectHeight;
						break;
						
					case cyan:			
						
						Ghost.ghostArray[1].x = x*objectWidth;
						Ghost.ghostArray[1].y = y*objectHeight;
						break;
						
					case purple:
						
						Ghost.ghostArray[2].x = x*objectWidth;
						Ghost.ghostArray[2].y = y*objectHeight;
						break;
						
					case orange:
						
						Ghost.ghostArray[3].x = x*objectWidth;
						Ghost.ghostArray[3].y = y*objectHeight;
						break;
						
					case white:
						
						gp.foodList.add(new Pellet(x*objectWidth, y*objectHeight, gp));
						break;
						
					case lightYellow:
						
						gp.foodList.add(new Energizer(x*objectWidth, y*objectHeight, gp));
						break;
				}
			}	
		}
	}
	
	public void render(Graphics g)
	{
		for(int x = 0; x < gameWidth; x++)
		{
			for(int y = 0; y < gameHeight; y++)
			{
				if(gp.wallMatrix[x][y] != null)
				{
					gp.wallMatrix[x][y].render(g);
				}
			}
		}
		
		for(int i = 0; i < gp.foodList.size(); i++)
		{	
			gp.foodList.get(i).render(g);
		}
		
		gp.pacman.render(g);
		
		if(gp.gameStatus != gp.lifeLost)
		{
			Ghost.ghostArray[0].render(g);
			Ghost.ghostArray[1].render(g);
			Ghost.ghostArray[2].render(g);
			Ghost.ghostArray[3].render(g);
		}

		gp.spawnBoxDoor.render(g);
		gp.bonusScore.render(g);
	}
}