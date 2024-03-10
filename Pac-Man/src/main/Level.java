package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Wall;
import food.Food_Energizer;
import food.Food_Pellet;

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
		loadMap("/Images/map.png");
		//loadMap("/Images/mapPortalCrossTest.png");
	}
	
	private void loadMap(String filePath)
	{
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(filePath));
			
			gameWidth = map.getWidth();		
			gameHeight = map.getHeight();		
			
			int pixels[] = map.getRGB(0, 0, gameWidth, gameHeight, null, 0, gameWidth);
			
			gp.pelletList = new ArrayList<>();
			gp.energizerList = new ArrayList<>();
			gp.wallMatrix = new Wall[gameWidth][gameHeight];
			
			for(int x = 0; x < gameWidth; x++)
			{
				for(int y = 0; y < gameHeight; y++)
				{
					int color = pixels[x + (y * gameWidth)];
					
					switch(color)
					{
						case black:
							
							gp.wallMatrix[x][y] = new Wall(gp);
							gp.wallMatrix[x][y].x = x*gp.tileSize;
							gp.wallMatrix[x][y].y = y*gp.tileSize;
							break;

						case white:
							
							gp.pelletList.add(new Food_Pellet(gp));
							gp.pelletList.get(gp.pelletList.size()-1).x = (x*gp.tileSize)+12;
							gp.pelletList.get(gp.pelletList.size()-1).y = (y*gp.tileSize)+12;
							gp.pelletList.get(gp.pelletList.size()-1).width = 8;
							gp.pelletList.get(gp.pelletList.size()-1).height = 8;
							break;
							
						case lightYellow:
							
							gp.energizerList.add(new Food_Energizer(gp));
							gp.energizerList.get(gp.energizerList.size()-1).x = x*gp.tileSize;
							gp.energizerList.get(gp.energizerList.size()-1).y = y*gp.tileSize;
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
		
		for(int i = 0; i < gp.pelletList.size(); i++)
		{	
			gp.pelletList.get(i).render(g);
		}
		
		for(int i = 0; i < gp.energizerList.size(); i++)
		{	
			gp.energizerList.get(i).render(g);
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