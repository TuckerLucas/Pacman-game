package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Texture
{
	public static BufferedImage spritesheet;
	
	public static int spriteSize = 16;
	
	public static int objectWidth  = 32;
	public static int objectHeight = 32;
	
	public static BufferedImage[][] pacmanLook;
	
	public static BufferedImage[] pacmanDie;
	
	public static BufferedImage[][][] ghostLook;
	
	public static BufferedImage[] blueGhost;
	public static BufferedImage[] whiteGhost;
	public static BufferedImage[] flashGhost;
	
	public static BufferedImage[] energizer;
	
	public static BufferedImage[] bonusScore;

	public static int spriteColumn1 = 0;
	public static int spriteColumn2 = spriteSize*1;
	public static int spriteColumn3 = spriteSize*2;
	public static int spriteColumn4 = spriteSize*3;
	public static int spriteColumn5 = spriteSize*4;
	public static int spriteColumn6 = spriteSize*5;
	public static int spriteColumn7 = spriteSize*6;
	public static int spriteColumn8 = spriteSize*7;
	
	public static int spriteLine1 = 0;
	public static int spriteLine2 = spriteSize*1;
	public static int spriteLine3 = spriteSize*2;
	public static int spriteLine4 = spriteSize*3;
	public static int spriteLine5 = spriteSize*4;
	public static int spriteLine6 = spriteSize*5;
	public static int spriteLine7 = spriteSize*6;
	public static int spriteLine8 = spriteSize*7;
	public static int spriteLine9 = spriteSize*8;
	
	private static String spritesheetImagePath = "/Images/spritesheet.png";
	
	public static Texture texture;
	
	public Texture()
	{
		getSpritesheet(spritesheetImagePath);
		loadAnimationArrays();
	}
	
	private void getSpritesheet(String path)
	{
		try
		{
			spritesheet = ImageIO.read(getClass().getResource(path));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadAnimationArrays()
	{
		pacmanLook = new BufferedImage[4][3];
		pacmanDie = new BufferedImage[21];
		ghostLook = new BufferedImage[4][4][2];
		blueGhost = new BufferedImage[2];
		whiteGhost = new BufferedImage[2];
		flashGhost = new BufferedImage[4];
		energizer = new BufferedImage[2];
		bonusScore = new BufferedImage[2];
		
		pacmanLook[0][0] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);		
		pacmanLook[0][1] = getSprite(spriteColumn6, spriteLine5, spriteSize, spriteSize);	
		pacmanLook[0][2] = getSprite(spriteColumn7, spriteLine5, spriteSize, spriteSize);	
		pacmanLook[1][0] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanLook[1][1] = getSprite(spriteColumn8, spriteLine5, spriteSize, spriteSize);
		pacmanLook[1][2] = getSprite(spriteColumn1, spriteLine6, spriteSize, spriteSize);
		pacmanLook[2][0] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanLook[2][1] = getSprite(spriteColumn2, spriteLine6, spriteSize, spriteSize);
		pacmanLook[2][2] = getSprite(spriteColumn3, spriteLine6, spriteSize, spriteSize);
		pacmanLook[3][0] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanLook[3][1] = getSprite(spriteColumn4, spriteLine6, spriteSize, spriteSize);
		pacmanLook[3][2] = getSprite(spriteColumn5, spriteLine6, spriteSize, spriteSize);
		
		pacmanDie[0] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanDie[1] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanDie[2] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanDie[3] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanDie[4] = getSprite(spriteColumn5, spriteLine5, spriteSize, spriteSize);
		pacmanDie[5] = getSprite(spriteColumn2, spriteLine6, spriteSize, spriteSize);
		pacmanDie[6] = getSprite(spriteColumn3, spriteLine6, spriteSize, spriteSize);
		pacmanDie[7] = getSprite(spriteColumn6, spriteLine6, spriteSize, spriteSize);
		pacmanDie[8] = getSprite(spriteColumn7, spriteLine6, spriteSize, spriteSize);
		pacmanDie[9] = getSprite(spriteColumn8, spriteLine6, spriteSize, spriteSize);
		pacmanDie[10] = getSprite(spriteColumn1, spriteLine7, spriteSize, spriteSize);
		pacmanDie[11] = getSprite(spriteColumn2, spriteLine7, spriteSize, spriteSize);
		pacmanDie[12] = getSprite(spriteColumn3, spriteLine7, spriteSize, spriteSize);
		pacmanDie[13] = getSprite(spriteColumn4, spriteLine7, spriteSize, spriteSize);
		pacmanDie[14] = getSprite(spriteColumn5, spriteLine7, spriteSize, spriteSize);
		pacmanDie[15] = getSprite(spriteColumn6, spriteLine7, spriteSize, spriteSize);
		pacmanDie[16] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
		pacmanDie[17] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
		pacmanDie[18] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
		pacmanDie[19] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
		pacmanDie[20] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
		
		ghostLook[0][0][0] = getSprite(spriteColumn1, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][0][1] = getSprite(spriteColumn5, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][1][0] = getSprite(spriteColumn2, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][1][1] = getSprite(spriteColumn6, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][2][0] = getSprite(spriteColumn3, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][2][1] = getSprite(spriteColumn7, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][3][0] = getSprite(spriteColumn4, spriteLine1, spriteSize, spriteSize);
		ghostLook[0][3][1] = getSprite(spriteColumn8, spriteLine1, spriteSize, spriteSize);
		
		ghostLook[1][0][0] = getSprite(spriteColumn1, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][0][1] = getSprite(spriteColumn5, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][1][0] = getSprite(spriteColumn2, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][1][1] = getSprite(spriteColumn6, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][2][0] = getSprite(spriteColumn3, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][2][1] = getSprite(spriteColumn7, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][3][0] = getSprite(spriteColumn4, spriteLine2, spriteSize, spriteSize);
		ghostLook[1][3][1] = getSprite(spriteColumn8, spriteLine2, spriteSize, spriteSize);
		
		ghostLook[2][0][0] = getSprite(spriteColumn1, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][0][1] = getSprite(spriteColumn5, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][1][0] = getSprite(spriteColumn2, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][1][1] = getSprite(spriteColumn6, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][2][0] = getSprite(spriteColumn3, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][2][1] = getSprite(spriteColumn7, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][3][0] = getSprite(spriteColumn4, spriteLine3, spriteSize, spriteSize);
		ghostLook[2][3][1] = getSprite(spriteColumn8, spriteLine3, spriteSize, spriteSize);
		
		ghostLook[3][0][0] = getSprite(spriteColumn1, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][0][1] = getSprite(spriteColumn5, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][1][0] = getSprite(spriteColumn2, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][1][1] = getSprite(spriteColumn6, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][2][0] = getSprite(spriteColumn3, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][2][1] = getSprite(spriteColumn7, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][3][0] = getSprite(spriteColumn4, spriteLine4, spriteSize, spriteSize);
		ghostLook[3][3][1] = getSprite(spriteColumn8, spriteLine4, spriteSize, spriteSize);
		
		blueGhost[0] = getSprite(spriteColumn1, spriteLine5, spriteSize, spriteSize);
		blueGhost[1] = getSprite(spriteColumn2, spriteLine5, spriteSize, spriteSize);
		whiteGhost[0] = getSprite(spriteColumn3, spriteLine5, spriteSize, spriteSize);
		whiteGhost[1] = getSprite(spriteColumn4, spriteLine5, spriteSize, spriteSize);
		flashGhost[0] = blueGhost[0];
		flashGhost[1] = blueGhost[1];
		flashGhost[2] = whiteGhost[0];
		flashGhost[3] = whiteGhost[1];
		
		energizer[0] = getSprite(spriteColumn7, spriteLine7, spriteSize, spriteSize);
		energizer[1] = getSprite(spriteColumn8, spriteLine7, spriteSize, spriteSize);
	}
	
	public static BufferedImage getSprite(int spriteX, int spriteY, int spriteWidth, int spriteHeight)
	{
		return spritesheet.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight);
	}
	
	public void tick()
	{
		switch(Ghost.numberOfEatenGhosts)
		{
			case 1: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn1, Texture.spriteLine8, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn1, Texture.spriteLine9, 
				32, Texture.spriteSize);
				break;
			case 2: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn3, Texture.spriteLine8, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn3, Texture.spriteLine9, 
				32, Texture.spriteSize);
				break;
			case 3: 
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn5, Texture.spriteLine8, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn5, Texture.spriteLine9, 
				32, Texture.spriteSize);
				break;
			case 4:
				Texture.bonusScore[0] = Texture.getSprite(Texture.spriteColumn7, Texture.spriteLine8, 
				32, Texture.spriteSize);
				Texture.bonusScore[1] = Texture.getSprite(Texture.spriteColumn7, Texture.spriteLine9, 
				32, Texture.spriteSize);
				break;
		}
	}
}

