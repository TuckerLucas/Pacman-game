/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Texture.java
* 
* Description: 
* 
* This file contains the arrays for the game sprite animations
* and the method to load the arrays with the correct sprites
* for each animation.
* 
/**************************************************************/

package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Texture
{
	// Sheet containing all game sprites
	public static BufferedImage spritesheet;
	
	// Size of one sprite (16x16)
	public static int spriteSize = 16;
	
	// Pacman arrays for sprite animations 
	public static BufferedImage[] pacmanLookRight;						
	public static BufferedImage[] pacmanLookLeft;
	public static BufferedImage[] pacmanLookUp;	
	public static BufferedImage[] pacmanLookDown;
	
	// Blinky ghost arrays for sprite animations
	public static BufferedImage[] blinkyLookRight;
	public static BufferedImage[] blinkyLookLeft;
	public static BufferedImage[] blinkyLookUp;
	public static BufferedImage[] blinkyLookDown;
	
	// Inky ghost arrays for sprite animations
	public static BufferedImage[] inkyLookRight;
	public static BufferedImage[] inkyLookLeft;
	public static BufferedImage[] inkyLookUp;
	public static BufferedImage[] inkyLookDown;
	
	// Pinky ghost arrays for sprite animations
	public static BufferedImage[] pinkyLookRight;
	public static BufferedImage[] pinkyLookLeft;
	public static BufferedImage[] pinkyLookUp;
	public static BufferedImage[] pinkyLookDown;
	
	// Clyde ghost arrays for sprite animations
	public static BufferedImage[] clydeLookRight;
	public static BufferedImage[] clydeLookLeft;
	public static BufferedImage[] clydeLookUp;
	public static BufferedImage[] clydeLookDown;
	
	// Vulnerable ghost arrays for sprite animations
	public static BufferedImage[] blueGhost;
	public static BufferedImage[] whiteGhost;
	public static BufferedImage[] flashGhost;
	
	// Energizer array for sprite animations
	public static BufferedImage[] energizer;
	
	// Bonus score array for sprite animations
	public static BufferedImage[] bonusScore;
	
	public static int animationPhasePacman = 0;

	public static int animationPhaseBonusScore = 0;
	
	public static int animationPhaseFlash = 0;
	
	// Spritesheet columns
	public static int spriteColumn1 = 0;
	public static int spriteColumn2 = spriteSize*1;
	public static int spriteColumn3 = spriteSize*2;
	public static int spriteColumn4 = spriteSize*3;
	public static int spriteColumn5 = spriteSize*4;
	public static int spriteColumn6 = spriteSize*5;
	public static int spriteColumn7 = spriteSize*6;
	public static int spriteColumn8 = spriteSize*7;
	public static int spriteColumn9 = spriteSize*8;
	
	// Spritesheet lines
	public static int spriteLine1 = 0;
	public static int spriteLine2 = spriteSize*1;
	public static int spriteLine3 = spriteSize*2;
	public static int spriteLine4 = spriteSize*3;
	public static int spriteLine5 = spriteSize*4;
	public static int spriteLine6 = spriteSize*5;
	public static int spriteLine7 = spriteSize*6;
	public static int spriteLine8 = spriteSize*7;
	public static int spriteLine9 = spriteSize*8;
	
	// Constructor
	public Texture()
	{
		try
		{
			spritesheet = ImageIO.read(getClass().getResource("/Images/spritesheet.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
				
		// Pacman sprite animation arrays
		pacmanLookRight = new BufferedImage[3];
		pacmanLookLeft 	= new BufferedImage[3];
		pacmanLookUp 	= new BufferedImage[3];
		pacmanLookDown 	= new BufferedImage[3];
		
		// Load pacman sprite animation arrays
		pacmanLookRight[0] 	= getSprite(spriteColumn1, spriteLine1, spriteSize, spriteSize);		
		pacmanLookRight[1] 	= getSprite(spriteColumn2, spriteLine1, spriteSize, spriteSize);	
		pacmanLookRight[2] 	= getSprite(spriteColumn3, spriteLine1, spriteSize, spriteSize);	
		pacmanLookLeft[0] 	= getSprite(spriteColumn1, spriteLine1, spriteSize, spriteSize);
		pacmanLookLeft[1] 	= getSprite(spriteColumn4, spriteLine1, spriteSize, spriteSize);
		pacmanLookLeft[2] 	= getSprite(spriteColumn5, spriteLine1, spriteSize, spriteSize);
		pacmanLookUp[0] 	= getSprite(spriteColumn1, spriteLine1, spriteSize, spriteSize);
		pacmanLookUp[1] 	= getSprite(spriteColumn6, spriteLine1, spriteSize, spriteSize);
		pacmanLookUp[2] 	= getSprite(spriteColumn7, spriteLine1, spriteSize, spriteSize);
		pacmanLookDown[0] 	= getSprite(spriteColumn1, spriteLine1, spriteSize, spriteSize);
		pacmanLookDown[1] 	= getSprite(spriteColumn8, spriteLine1, spriteSize, spriteSize);
		pacmanLookDown[2] 	= getSprite(spriteColumn9, spriteLine1, spriteSize, spriteSize);
		
		// Blinky sprite sprite animation arrays
		blinkyLookRight	= new BufferedImage[2];
		blinkyLookLeft 	= new BufferedImage[2];
		blinkyLookUp 	= new BufferedImage[2];
		blinkyLookDown 	= new BufferedImage[2];
		
		// Load blinky sprite animation arrays
		blinkyLookRight[0] 	= getSprite(spriteColumn1, spriteLine2, spriteSize, spriteSize);
		blinkyLookRight[1] 	= getSprite(spriteColumn5, spriteLine2, spriteSize, spriteSize);
		blinkyLookLeft[0]	= getSprite(spriteColumn2, spriteLine2, spriteSize, spriteSize);
		blinkyLookLeft[1] 	= getSprite(spriteColumn6, spriteLine2, spriteSize, spriteSize);
		blinkyLookUp[0] 	= getSprite(spriteColumn3, spriteLine2, spriteSize, spriteSize);
		blinkyLookUp[1] 	= getSprite(spriteColumn7, spriteLine2, spriteSize, spriteSize);
		blinkyLookDown[0] 	= getSprite(spriteColumn4, spriteLine2, spriteSize, spriteSize);
		blinkyLookDown[1] 	= getSprite(spriteColumn8, spriteLine2, spriteSize, spriteSize);
		
		// Inky sprite sprite animation arrays
		inkyLookRight 	= new BufferedImage[2];
		inkyLookLeft 	= new BufferedImage[2];
		inkyLookUp 		= new BufferedImage[2];
		inkyLookDown 	= new BufferedImage[2];
		
		// Load inky sprite animation arrays
		inkyLookRight[0] 	= getSprite(spriteColumn1, spriteLine3, spriteSize, spriteSize);
		inkyLookRight[1] 	= getSprite(spriteColumn5, spriteLine3, spriteSize, spriteSize);
		inkyLookLeft[0]  	= getSprite(spriteColumn2, spriteLine3, spriteSize, spriteSize);
		inkyLookLeft[1]  	= getSprite(spriteColumn6, spriteLine3, spriteSize, spriteSize);
		inkyLookUp[0]    	= getSprite(spriteColumn3, spriteLine3, spriteSize, spriteSize);
		inkyLookUp[1]    	= getSprite(spriteColumn7, spriteLine3, spriteSize, spriteSize);
		inkyLookDown[0]  	= getSprite(spriteColumn4, spriteLine3, spriteSize, spriteSize);
		inkyLookDown[1]  	= getSprite(spriteColumn8, spriteLine3, spriteSize, spriteSize);
		
		// Pinky sprite animation arrays
		pinkyLookRight 	= new BufferedImage[2];
		pinkyLookLeft 	= new BufferedImage[2];
		pinkyLookUp 	= new BufferedImage[2];
		pinkyLookDown 	= new BufferedImage[2];
		
		// Load pinky sprite animation arrays
		pinkyLookRight[0] 	= getSprite(spriteColumn1, spriteLine4, spriteSize, spriteSize);
		pinkyLookRight[1] 	= getSprite(spriteColumn5, spriteLine4, spriteSize, spriteSize);
		pinkyLookLeft[0] 	= getSprite(spriteColumn2, spriteLine4, spriteSize, spriteSize);
		pinkyLookLeft[1] 	= getSprite(spriteColumn6, spriteLine4, spriteSize, spriteSize);
		pinkyLookUp[0] 		= getSprite(spriteColumn3, spriteLine4, spriteSize, spriteSize);
		pinkyLookUp[1] 		= getSprite(spriteColumn7, spriteLine4, spriteSize, spriteSize);
		pinkyLookDown[0] 	= getSprite(spriteColumn4, spriteLine4, spriteSize, spriteSize);
		pinkyLookDown[1] 	= getSprite(spriteColumn8, spriteLine4, spriteSize, spriteSize);
		
		// Clyde sprite animation arrays
		clydeLookRight 	= new BufferedImage[2];
		clydeLookLeft 	= new BufferedImage[2];
		clydeLookUp 	= new BufferedImage[2];
		clydeLookDown 	= new BufferedImage[2];
		
		// Load clyde sprite animation arrays
		clydeLookRight[0] 	= getSprite(spriteColumn1, spriteLine5, spriteSize, spriteSize);
		clydeLookRight[1] 	= getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		clydeLookLeft[0] 	= getSprite(spriteColumn2, spriteLine5, spriteSize, spriteSize);
		clydeLookLeft[1] 	= getSprite(spriteColumn6, spriteLine5, spriteSize, spriteSize);
		clydeLookUp[0] 		= getSprite(spriteColumn3, spriteLine5, spriteSize, spriteSize);
		clydeLookUp[1] 		= getSprite(spriteColumn7, spriteLine5, spriteSize, spriteSize);
		clydeLookDown[0] 	= getSprite(spriteColumn4, spriteLine5, spriteSize, spriteSize);
		clydeLookDown[1] 	= getSprite(spriteColumn8, spriteLine5, spriteSize, spriteSize);
		
		// Vulnerable ghost animation arrays
		blueGhost 	= new BufferedImage[2];
		whiteGhost 	= new BufferedImage[2];
		flashGhost 	= new BufferedImage[4];
		
		// Load vulnerable ghost animation arrays
		blueGhost[0] 	= getSprite(spriteColumn1, spriteLine6, spriteSize, spriteSize);
		blueGhost[1] 	= getSprite(spriteColumn2, spriteLine6, spriteSize, spriteSize);
		whiteGhost[0] 	= getSprite(spriteColumn3, spriteLine6, spriteSize, spriteSize);
		whiteGhost[1] 	= getSprite(spriteColumn4, spriteLine6, spriteSize, spriteSize);
		flashGhost[0] 	= blueGhost[0];
		flashGhost[1] 	= blueGhost[1];
		flashGhost[2] 	= whiteGhost[0];
		flashGhost[3] 	= whiteGhost[1];
		
		// Energizer sprite animation array
		energizer = new BufferedImage[2];
		
		// Load energizer sprite animation array
		energizer[0] = getSprite(spriteColumn1, spriteLine7, spriteSize, spriteSize);
		energizer[1] = getSprite(spriteColumn2, spriteLine7, spriteSize, spriteSize);
		
		// Bonus score sprite animation array
		bonusScore = new BufferedImage[10];
	}
	
	// Get sprite from the spritesheet
	public static BufferedImage getSprite(int spriteX, int spriteY, int spriteWidth, int spriteHeight)
	{
		return spritesheet.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight);
	}
	
	public void tick()
	{
		switch(Pacman.nEatenGhosts)
		{
			case 1: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine2, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine6, 
				32, Texture.spriteSize);
				break;
			case 2: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine3, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine7, 
				32, Texture.spriteSize);
				break;
			case 3: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine4, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine8, 
				32, Texture.spriteSize);
				break;
			case 4:
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine5, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn9, Texture.spriteLine9, 
				32, Texture.spriteSize);
				break;
		}
		
		for(int i = 2; i < Texture.bonusScore.length; i++)
		{
			if(i % 2 == 0)
			{
				Texture.bonusScore[i] = Texture.bonusScore[0];
			}
			else
			{
				Texture.bonusScore[i] = Texture.bonusScore[1];
			}
		}
	}
}

