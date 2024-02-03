package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import food.Energizer;
import food.Pellet;
import main.GamePanel;

public class Level
{
	GamePanel gp;
	
	private int gameWidth;
	private int gameHeight;
	
	private final int black = 0xFF000000;
	private final int white = 0xFFFFFFFF;
	private final int lightYellow = 0xFFFFFF00;
	
	public Level(GamePanel gp)	
	{
		this.gp = gp;
		//loadMap("/Images/map.png");
		loadMap("/Images/mapPortalCrossTest.png");
	}
	
	private void loadMap(String filePath)
	{
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(filePath));
			
			gameWidth = map.getWidth();		
			gameHeight = map.getHeight();		
			
			int pixels[] = map.getRGB(0, 0, gameWidth, gameHeight, null, 0, gameWidth);
			
			gp.wallMatrix = new Wall[gameWidth][gameHeight];
			
			for(int x = 0; x < gameWidth; x++)
			{
				for(int y = 0; y < gameHeight; y++)
				{
					int color = pixels[x + (y * gameWidth)];
					
					switch(color)
					{
						case black:
							
							gp.wallMatrix[x][y] = new Wall(x*gp.tileSize, y*gp.tileSize, gp);
							break;

						case white:
							
							gp.foodList.add(new Pellet(x*gp.tileSize, y*gp.tileSize, gp));
							break;
							
						case lightYellow:
							
							gp.foodList.add(new Energizer(x*gp.tileSize, y*gp.tileSize, gp));
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
		
		if(gp.gameState != gp.lifeLostState)
		{
			gp.ghostArray[0].render(g);
			gp.ghostArray[1].render(g);
			gp.ghostArray[2].render(g);
			gp.ghostArray[3].render(g);
		}

		gp.spawnBoxDoor.render(g);
		gp.bonusScore.render(g);
	}
}