/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Texture.java
* 
* Description: 
* 
* This file contains the arrays for the game sprite animations
* and the methods 
* 
/**************************************************************/

package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture
{
	public static BufferedImage[] player;
	public static BufferedImage[] player1;
	
	public static BufferedImage[] blinkyR;
	public static BufferedImage[] blinkyL;
	public static BufferedImage[] blinkyU;
	public static BufferedImage[] blinkyD;
	
	public static BufferedImage[] inkyR;
	public static BufferedImage[] inkyL;
	public static BufferedImage[] inkyU;
	public static BufferedImage[] inkyD;
	
	public static BufferedImage[] pinkyR;
	public static BufferedImage[] pinkyL;
	public static BufferedImage[] pinkyU;
	public static BufferedImage[] pinkyD;
	
	public static BufferedImage[] clydeR;
	public static BufferedImage[] clydeL;
	public static BufferedImage[] clydeU;
	public static BufferedImage[] clydeD;
	
	public static BufferedImage[] blueghost;
	public static BufferedImage[] whiteghost;
	public static BufferedImage[] flash;
	
	public static BufferedImage[] energizer;
	
	public static BufferedImage[] died;
	
	public static BufferedImage[] two_hundred;
	public static BufferedImage[] four_hundred;
	public static BufferedImage[] eight_hundred;
	public static BufferedImage[] sixteen_hundred;

	public static BufferedImage spritesheet;
	
	public Texture()
	{
		try
		{
			spritesheet = ImageIO.read(getClass().getResource("/sprites/spritesheet.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		player = new BufferedImage[3];
		player1 = new BufferedImage[3];
		
		blinkyR = new BufferedImage[2];
		blinkyL = new BufferedImage[2];
		blinkyU = new BufferedImage[2];
		blinkyD = new BufferedImage[2];
		
		inkyR = new BufferedImage[2];
		inkyL = new BufferedImage[2];
		inkyU = new BufferedImage[2];
		inkyD = new BufferedImage[2];
		
		pinkyR = new BufferedImage[2];
		pinkyL = new BufferedImage[2];
		pinkyU = new BufferedImage[2];
		pinkyD = new BufferedImage[2];
		
		clydeR = new BufferedImage[2];
		clydeL = new BufferedImage[2];
		clydeU = new BufferedImage[2];
		clydeD = new BufferedImage[2];
		
		blueghost = new BufferedImage[2];
		whiteghost = new BufferedImage[2];
		flash = new BufferedImage[4];
		
		energizer = new BufferedImage[2];
		
		two_hundred = new BufferedImage[10];
		four_hundred = new BufferedImage[10];
		eight_hundred = new BufferedImage[10];
		sixteen_hundred = new BufferedImage[10];
		
		// Pacman sprite animation arrays
		player[0] = getSprite(0,0, 16, 16);		
		player[1] = getSprite(16,0, 16, 16);	
		player[2] = getSprite(32,0, 16, 16);	
		player1[0] = getSprite(0,0, 16, 16);
		player1[1] = getSprite(112,0, 16, 16);
		player1[2] = getSprite(128,0, 16, 16);
		
		// Blinky sprite animation arrays
		blinkyR[0] = getSprite(0, 16, 16, 16);
		blinkyR[1] = getSprite(64, 16, 16, 16);
		blinkyL[0] = getSprite(16, 16, 16, 16);
		blinkyL[1] = getSprite(80, 16, 16, 16);
		blinkyU[0] = getSprite(32, 16, 16, 16);
		blinkyU[1] = getSprite(96, 16, 16, 16);
		blinkyD[0] = getSprite(48, 16, 16, 16);
		blinkyD[1] = getSprite(112, 16, 16, 16);
		
		// Inky sprite animation arrays
		inkyR[0] = getSprite(0, 32, 16, 16);
		inkyR[1] = getSprite(64, 32, 16, 16);
		inkyL[0] = getSprite(16, 32, 16, 16);
		inkyL[1] = getSprite(80, 32, 16, 16);
		inkyU[0] = getSprite(32, 32, 16, 16);
		inkyU[1] = getSprite(96, 32, 16, 16);
		inkyD[0] = getSprite(48, 32, 16, 16);
		inkyD[1] = getSprite(112, 32, 16, 16);
		
		// Pinky sprite animation arrays
		pinkyR[0] = getSprite(0, 48, 16, 16);
		pinkyR[1] = getSprite(64, 48, 16, 16);
		pinkyL[0] = getSprite(16, 48, 16, 16);
		pinkyL[1] = getSprite(80, 48, 16, 16);
		pinkyU[0] = getSprite(32, 48, 16, 16);
		pinkyU[1] = getSprite(96, 48, 16, 16);
		pinkyD[0] = getSprite(48, 48, 16, 16);
		pinkyD[1] = getSprite(112, 48, 16, 16);
		
		// Clyde sprite animation arrays
		clydeR[0] = getSprite(0, 64, 16, 16);
		clydeR[1] = getSprite(64, 64, 16, 16);
		clydeL[0] = getSprite(16, 64, 16, 16);
		clydeL[1] = getSprite(80, 64, 16, 16);
		clydeU[0] = getSprite(32, 64, 16, 16);
		clydeU[1] = getSprite(96, 64, 16, 16);
		clydeD[0] = getSprite(48, 64, 16, 16);
		clydeD[1] = getSprite(112, 64, 16, 16);
		
		blueghost[0] = getSprite(0, 80, 16, 16);
		blueghost[1] = getSprite(16, 80, 16, 16);
		
		whiteghost[0] = getSprite(32, 80, 16, 16);
		whiteghost[1] = getSprite(48, 80, 16, 16);
		
		flash[0] = blueghost[0];
		flash[1] = blueghost[1];
		flash[2] = whiteghost[0];
		flash[3] = whiteghost[1];
		
		// Energizer sprite animation array
		energizer[0] = getSprite(0, 96, 16, 16);
		energizer[1] = getSprite(16, 96, 16, 16);
		
		// Bonus score sprite animation arrays
		two_hundred[0] = getSprite(128, 16, 32, 16);
		two_hundred[1] = getSprite(128, 80, 32, 16);
		four_hundred[0] = getSprite(128, 32, 32, 16);
		four_hundred[1] = getSprite(128, 96, 32, 16);
		eight_hundred[0] = getSprite(128, 48, 32, 16);
		eight_hundred[1] = getSprite(128, 112, 32, 16);
		sixteen_hundred[0] = getSprite(128, 64, 32, 16);
		sixteen_hundred[1] = getSprite(128, 128, 32, 16);
		
		for(int i = 2; i < 10; i++)
		{
			if(i % 2 == 0)
			{
				two_hundred[i] = two_hundred[0];
				four_hundred[i] = four_hundred[0];
				eight_hundred[i] = eight_hundred[0];
				sixteen_hundred[i] = sixteen_hundred[0];
			}
			else
			{
				two_hundred[i] = two_hundred[1];
				four_hundred[i] = four_hundred[1];
				eight_hundred[i] = eight_hundred[1];
				sixteen_hundred[i] = sixteen_hundred[1];
			}
		}
	}
	
	public static BufferedImage getSprite(int x, int y, int w, int h)
	{
		return spritesheet.getSubimage(x, y, w, h);
	}
}

