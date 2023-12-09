package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Animation
{
	private int spriteWidthInPixels = 16;
	private int spriteHeightInPixels = 16;
	
	private int spriteColumn1 = 0;
	private int spriteColumn2 = spriteWidthInPixels*1;
	private int spriteColumn3 = spriteWidthInPixels*2;
	private int spriteColumn4 = spriteWidthInPixels*3;
	private int spriteColumn5 = spriteWidthInPixels*4;
	private int spriteColumn6 = spriteWidthInPixels*5;
	private int spriteColumn7 = spriteWidthInPixels*6;
	private int spriteColumn8 = spriteWidthInPixels*7;
	
	private int spriteLine1 = 0;
	private int spriteLine2 = spriteHeightInPixels*1;
	private int spriteLine3 = spriteHeightInPixels*2;
	private int spriteLine4 = spriteHeightInPixels*3;
	private int spriteLine5 = spriteHeightInPixels*4;
	private int spriteLine6 = spriteHeightInPixels*5;
	private int spriteLine7 = spriteHeightInPixels*6;
	private int spriteLine8 = spriteHeightInPixels*7;
	private int spriteLine9 = spriteHeightInPixels*8;
	
	public static BufferedImage[][] alivePacmanSprites;
	public static BufferedImage[] deadPacmanSprites;
	public static BufferedImage[][][] hostileGhostSprites;
	public static BufferedImage[] vulnerableGhostSprites;
	public static BufferedImage[] flashingGhostSprites;
	public static BufferedImage[] energizerSprites;
	public static BufferedImage[] bonusScore200Sprites;
	public static BufferedImage[] bonusScore400Sprites;
	public static BufferedImage[] bonusScore800Sprites;
	public static BufferedImage[] bonusScore1600Sprites;
	
	public static int frameIndex = 0;
	public static int frameIndexFlashingGhosts = 0;
	protected static double elapsedFrameTimeInSeconds = 0;		
	protected static double targetTimePerFrameInSeconds = 0.05;
	private static int totalNumberOfFrames = 2;
	
	private BufferedImage spritesheet;
	
	private String spritesheetImagePath = "/Images/spritesheet.png";
	
	public static Animation animation;
	
	public Animation()
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
		alivePacmanSprites = new BufferedImage[4][3];
		deadPacmanSprites = new BufferedImage[21];
		hostileGhostSprites = new BufferedImage[4][4][2];
		vulnerableGhostSprites = new BufferedImage[2];
		flashingGhostSprites = new BufferedImage[4];
		energizerSprites = new BufferedImage[2];
		bonusScore200Sprites = new BufferedImage[2];
		bonusScore400Sprites = new BufferedImage[2];
		bonusScore800Sprites = new BufferedImage[2];
		bonusScore1600Sprites = new BufferedImage[2];
		
		
		alivePacmanSprites[0][0] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);		
		alivePacmanSprites[0][1] = getSprite(spriteColumn6, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);	
		alivePacmanSprites[0][2] = getSprite(spriteColumn7, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);	
		alivePacmanSprites[1][0] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[1][1] = getSprite(spriteColumn8, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[1][2] = getSprite(spriteColumn1, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[2][0] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[2][1] = getSprite(spriteColumn2, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[2][2] = getSprite(spriteColumn3, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[3][0] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[3][1] = getSprite(spriteColumn4, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		alivePacmanSprites[3][2] = getSprite(spriteColumn5, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		
		deadPacmanSprites[0] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[1] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[2] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[3] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[4] = getSprite(spriteColumn5, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[5] = getSprite(spriteColumn2, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[6] = getSprite(spriteColumn3, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[7] = getSprite(spriteColumn6, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[8] = getSprite(spriteColumn7, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[9] = getSprite(spriteColumn8, spriteLine6, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[10] = getSprite(spriteColumn1, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[11] = getSprite(spriteColumn2, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[12] = getSprite(spriteColumn3, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[13] = getSprite(spriteColumn4, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[14] = getSprite(spriteColumn5, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[15] = getSprite(spriteColumn6, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[16] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[17] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[18] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[19] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		deadPacmanSprites[20] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		
		hostileGhostSprites[0][0][0] = getSprite(spriteColumn1, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][0][1] = getSprite(spriteColumn5, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][1][0] = getSprite(spriteColumn2, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][1][1] = getSprite(spriteColumn6, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][2][0] = getSprite(spriteColumn3, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][2][1] = getSprite(spriteColumn7, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][3][0] = getSprite(spriteColumn4, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[0][3][1] = getSprite(spriteColumn8, spriteLine1, spriteWidthInPixels, spriteHeightInPixels);
		
		hostileGhostSprites[1][0][0] = getSprite(spriteColumn1, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][0][1] = getSprite(spriteColumn5, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][1][0] = getSprite(spriteColumn2, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][1][1] = getSprite(spriteColumn6, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][2][0] = getSprite(spriteColumn3, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][2][1] = getSprite(spriteColumn7, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][3][0] = getSprite(spriteColumn4, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[1][3][1] = getSprite(spriteColumn8, spriteLine2, spriteWidthInPixels, spriteHeightInPixels);
		
		hostileGhostSprites[2][0][0] = getSprite(spriteColumn1, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][0][1] = getSprite(spriteColumn5, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][1][0] = getSprite(spriteColumn2, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][1][1] = getSprite(spriteColumn6, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][2][0] = getSprite(spriteColumn3, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][2][1] = getSprite(spriteColumn7, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][3][0] = getSprite(spriteColumn4, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[2][3][1] = getSprite(spriteColumn8, spriteLine3, spriteWidthInPixels, spriteHeightInPixels);
		
		hostileGhostSprites[3][0][0] = getSprite(spriteColumn1, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][0][1] = getSprite(spriteColumn5, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][1][0] = getSprite(spriteColumn2, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][1][1] = getSprite(spriteColumn6, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][2][0] = getSprite(spriteColumn3, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][2][1] = getSprite(spriteColumn7, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][3][0] = getSprite(spriteColumn4, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		hostileGhostSprites[3][3][1] = getSprite(spriteColumn8, spriteLine4, spriteWidthInPixels, spriteHeightInPixels);
		
		vulnerableGhostSprites[0] = getSprite(spriteColumn1, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		vulnerableGhostSprites[1] = getSprite(spriteColumn2, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		
		flashingGhostSprites[0] = getSprite(spriteColumn1, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		flashingGhostSprites[1] = getSprite(spriteColumn2, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		flashingGhostSprites[2] = getSprite(spriteColumn3, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		flashingGhostSprites[3] = getSprite(spriteColumn4, spriteLine5, spriteWidthInPixels, spriteHeightInPixels);
		
		energizerSprites[0] = getSprite(spriteColumn7, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		energizerSprites[1] = getSprite(spriteColumn8, spriteLine7, spriteWidthInPixels, spriteHeightInPixels);
		
		bonusScore200Sprites[0] = getSprite(spriteColumn1, spriteLine8, spriteWidthInPixels*2, spriteHeightInPixels);
		bonusScore200Sprites[1] = getSprite(spriteColumn1, spriteLine9, spriteWidthInPixels*2, spriteHeightInPixels);
		
		bonusScore400Sprites[0] = getSprite(spriteColumn3, spriteLine8, spriteWidthInPixels*2, spriteHeightInPixels);
		bonusScore400Sprites[1] = getSprite(spriteColumn3, spriteLine9, spriteWidthInPixels*2, spriteHeightInPixels);
		
		bonusScore800Sprites[0] = getSprite(spriteColumn5, spriteLine8, spriteWidthInPixels*2, spriteHeightInPixels);
		bonusScore800Sprites[1] = getSprite(spriteColumn5, spriteLine9, spriteWidthInPixels*2, spriteHeightInPixels);
		
		bonusScore1600Sprites[0] = getSprite(spriteColumn7, spriteLine8, spriteWidthInPixels*2, spriteHeightInPixels);
		bonusScore1600Sprites[1] = getSprite(spriteColumn7, spriteLine9, spriteWidthInPixels*2, spriteHeightInPixels);
	}
	
	public void tick()
	{
		manageAnimationTiming();
	}
	
	private void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			frameIndexFlashingGhosts++;		 
			
			if(frameIndex == totalNumberOfFrames)
			{
				frameIndex = 0;
			}
			
			// Flashing ghosts require more frames than other animations
			// Hence they have their own frame index variable
			if(frameIndexFlashingGhosts == 4)
			{
				frameIndexFlashingGhosts = 0;
			}
		}
	}
	
	private BufferedImage getSprite(int x, int y, int width, int height)
	{
		return spritesheet.getSubimage(x, y, width, height);
	}
}

