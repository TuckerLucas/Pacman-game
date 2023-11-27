package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Texture
{
	private BufferedImage spritesheet;
	
	private int spriteSize = 16;
	
	public static int objectWidth  = 32;
	public static int objectHeight = 32;
	
	private int spriteColumn1 = 0;
	private int spriteColumn2 = spriteSize*1;
	private int spriteColumn3 = spriteSize*2;
	private int spriteColumn4 = spriteSize*3;
	private int spriteColumn5 = spriteSize*4;
	private int spriteColumn6 = spriteSize*5;
	private int spriteColumn7 = spriteSize*6;
	private int spriteColumn8 = spriteSize*7;
	
	private int spriteLine1 = 0;
	private int spriteLine2 = spriteSize*1;
	private int spriteLine3 = spriteSize*2;
	private int spriteLine4 = spriteSize*3;
	private int spriteLine5 = spriteSize*4;
	private int spriteLine6 = spriteSize*5;
	private int spriteLine7 = spriteSize*6;
	private int spriteLine8 = spriteSize*7;
	private int spriteLine9 = spriteSize*8;
	
	private String spritesheetImagePath = "/Images/spritesheet.png";
	
	public static BufferedImage[][] pacmanLook;
	public static BufferedImage[] pacmanDie;
	public static BufferedImage[][][] ghostLook;
	public static BufferedImage[] blueGhost;
	public static BufferedImage[] whiteGhost;
	public static BufferedImage[] flashGhost;
	public static BufferedImage[] energizer;
	public static BufferedImage[] bonusScore200;
	public static BufferedImage[] bonusScore400;
	public static BufferedImage[] bonusScore800;
	public static BufferedImage[] bonusScore1600;
	
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
		bonusScore200 = new BufferedImage[2];
		bonusScore400 = new BufferedImage[2];
		bonusScore800 = new BufferedImage[2];
		bonusScore1600 = new BufferedImage[2];
		
		
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
		
		bonusScore200[0] = getSprite(spriteColumn1, spriteLine8, spriteSize*2, spriteSize);
		bonusScore200[1] = getSprite(spriteColumn1, spriteLine9, spriteSize*2, spriteSize);
		
		bonusScore400[0] = getSprite(spriteColumn3, spriteLine8, spriteSize*2, spriteSize);
		bonusScore400[1] = getSprite(spriteColumn3, spriteLine9, spriteSize*2, spriteSize);
		
		bonusScore800[0] = getSprite(spriteColumn5, spriteLine8, spriteSize*2, spriteSize);
		bonusScore800[1] = getSprite(spriteColumn5, spriteLine9, spriteSize*2, spriteSize);
		
		bonusScore1600[0] = getSprite(spriteColumn7, spriteLine8, spriteSize*2, spriteSize);
		bonusScore1600[1] = getSprite(spriteColumn7, spriteLine9, spriteSize*2, spriteSize);
	}
	
	
	private BufferedImage getSprite(int spriteX, int spriteY, int spriteWidth, int spriteHeight)
	{
		return spritesheet.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight);
	}
}

