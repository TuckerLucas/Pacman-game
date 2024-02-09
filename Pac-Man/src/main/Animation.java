package main;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.BonusScore;
import entity.Entity;
import entity.Pacman_Dead;

import java.io.IOException;

public class Animation
{	
	GamePanel gp;
	
	public BufferedImage[][] alivePacmanSprites = new BufferedImage[4][3];
	public BufferedImage[] deadPacmanSprites = new BufferedImage[21];
	public BufferedImage[][][] hostileGhostSprites = new BufferedImage[4][4][2];
	public BufferedImage[] vulnerableGhostSprites = new BufferedImage[2];
	public BufferedImage[] flashingGhostSprites = new BufferedImage[4];
	public BufferedImage[] energizerSprites = new BufferedImage[2];
	public BufferedImage[] bonusScore200Sprites = new BufferedImage[2];
	public BufferedImage[] bonusScore400Sprites = new BufferedImage[2];
	public BufferedImage[] bonusScore800Sprites = new BufferedImage[2];
	public BufferedImage[] bonusScore1600Sprites = new BufferedImage[2];
	
	private BufferedImage spritesheet;
	
	public Animation(GamePanel gp)
	{
		this.gp = gp; 
		
		getSpritesheet("/Images/spritesheet.png");
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
		alivePacmanSprites[0][0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);		
		alivePacmanSprites[0][1] = getSprite(gp.originalTileSize*5, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);	
		alivePacmanSprites[0][2] = getSprite(gp.originalTileSize*6, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);	
		alivePacmanSprites[1][0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[1][1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[1][2] = getSprite(gp.originalTileSize*0, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[2][0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[2][1] = getSprite(gp.originalTileSize*1, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[2][2] = getSprite(gp.originalTileSize*2, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[3][0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[3][1] = getSprite(gp.originalTileSize*3, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		alivePacmanSprites[3][2] = getSprite(gp.originalTileSize*4, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		
		deadPacmanSprites[0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[2] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[3] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[4] = getSprite(gp.originalTileSize*4, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[5] = getSprite(gp.originalTileSize*1, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[6] = getSprite(gp.originalTileSize*2, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[7] = getSprite(gp.originalTileSize*5, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[8] = getSprite(gp.originalTileSize*6, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[9] = getSprite(gp.originalTileSize*7, gp.originalTileSize*5, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[10] = getSprite(gp.originalTileSize*0, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[11] = getSprite(gp.originalTileSize*1, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[12] = getSprite(gp.originalTileSize*2, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[13] = getSprite(gp.originalTileSize*3, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[14] = getSprite(gp.originalTileSize*4, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[15] = getSprite(gp.originalTileSize*5, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[16] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[17] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[18] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[19] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		deadPacmanSprites[20] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		
		hostileGhostSprites[0][0][0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][0][1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][1][0] = getSprite(gp.originalTileSize*1, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][1][1] = getSprite(gp.originalTileSize*5, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][2][0] = getSprite(gp.originalTileSize*2, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][2][1] = getSprite(gp.originalTileSize*6, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][3][0] = getSprite(gp.originalTileSize*3, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[0][3][1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*0, gp.originalTileSize, gp.originalTileSize);
		
		hostileGhostSprites[1][0][0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][0][1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][1][0] = getSprite(gp.originalTileSize*1, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][1][1] = getSprite(gp.originalTileSize*5, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][2][0] = getSprite(gp.originalTileSize*2, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][2][1] = getSprite(gp.originalTileSize*6, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][3][0] = getSprite(gp.originalTileSize*3, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[1][3][1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*1, gp.originalTileSize, gp.originalTileSize);
		
		hostileGhostSprites[2][0][0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][0][1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][1][0] = getSprite(gp.originalTileSize*1, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][1][1] = getSprite(gp.originalTileSize*5, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][2][0] = getSprite(gp.originalTileSize*2, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][2][1] = getSprite(gp.originalTileSize*6, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][3][0] = getSprite(gp.originalTileSize*3, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[2][3][1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*2, gp.originalTileSize, gp.originalTileSize);
		
		hostileGhostSprites[3][0][0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][0][1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][1][0] = getSprite(gp.originalTileSize*1, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][1][1] = getSprite(gp.originalTileSize*5, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][2][0] = getSprite(gp.originalTileSize*2, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][2][1] = getSprite(gp.originalTileSize*6, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][3][0] = getSprite(gp.originalTileSize*3, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		hostileGhostSprites[3][3][1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*3, gp.originalTileSize, gp.originalTileSize);
		
		vulnerableGhostSprites[0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		vulnerableGhostSprites[1] = getSprite(gp.originalTileSize*1, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		
		flashingGhostSprites[0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		flashingGhostSprites[1] = getSprite(gp.originalTileSize*1, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		flashingGhostSprites[2] = getSprite(gp.originalTileSize*2, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		flashingGhostSprites[3] = getSprite(gp.originalTileSize*3, gp.originalTileSize*4, gp.originalTileSize, gp.originalTileSize);
		
		energizerSprites[0] = getSprite(gp.originalTileSize*6, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		energizerSprites[1] = getSprite(gp.originalTileSize*7, gp.originalTileSize*6, gp.originalTileSize, gp.originalTileSize);
		
		bonusScore200Sprites[0] = getSprite(gp.originalTileSize*0, gp.originalTileSize*7, gp.originalTileSize*2, gp.originalTileSize);
		bonusScore200Sprites[1] = getSprite(gp.originalTileSize*0, gp.originalTileSize*8, gp.originalTileSize*2, gp.originalTileSize);
		
		bonusScore400Sprites[0] = getSprite(gp.originalTileSize*2, gp.originalTileSize*7, gp.originalTileSize*2, gp.originalTileSize);
		bonusScore400Sprites[1] = getSprite(gp.originalTileSize*2, gp.originalTileSize*8, gp.originalTileSize*2, gp.originalTileSize);
		
		bonusScore800Sprites[0] = getSprite(gp.originalTileSize*4, gp.originalTileSize*7, gp.originalTileSize*2, gp.originalTileSize);
		bonusScore800Sprites[1] = getSprite(gp.originalTileSize*4, gp.originalTileSize*8, gp.originalTileSize*2, gp.originalTileSize);
		
		bonusScore1600Sprites[0] = getSprite(gp.originalTileSize*6, gp.originalTileSize*7, gp.originalTileSize*2, gp.originalTileSize);
		bonusScore1600Sprites[1] = getSprite(gp.originalTileSize*6, gp.originalTileSize*8, gp.originalTileSize*2, gp.originalTileSize);
	}
	
	private BufferedImage getSprite(int x, int y, int width, int height)
	{
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	public void manageAnimationTiming(Entity entity)
	{
		if(entity instanceof BonusScore)
		{
			if(!gp.bonusScore.isBeingDisplayed)
			{
				return;
			}
			
			gp.bonusScore.elapsedAnimationTimeInSeconds += gp.secondsPerTick;
			
			if(gp.bonusScore.elapsedAnimationTimeInSeconds >= gp.bonusScore.targetTimeForAnimationInSeconds)
			{
				gp.bonusScore.isBeingDisplayed = false;
			}
		}
		
		entity.elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(entity.elapsedFrameTimeInSeconds >= entity.targetTimePerFrameInSeconds)
		{
			entity.elapsedFrameTimeInSeconds = 0;
			entity.frameIndex++;
			
			if(entity.frameIndex == entity.totalNumberOfFrames)
			{
				entity.frameIndex = 0;
				
				if(entity instanceof Pacman_Dead)
				{
					if(gp.lives == 0)
					{
						gp.gameState = gp.gameOverState;
					}
					else
					{
						gp.respawnCharacters();
						gp.gameState = gp.playState;
					}
				}
			}
		}
	}
}

