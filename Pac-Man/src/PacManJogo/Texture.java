package PacManJogo;

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
		
		died = new BufferedImage[30];
		
		two_hundred = new BufferedImage[10];
		four_hundred = new BufferedImage[10];
		eight_hundred = new BufferedImage[10];
		sixteen_hundred = new BufferedImage[10];
		
		
		player[0] = getSprite(0,0);		
		player[1] = getSprite(16,0);	
		player[2] = getSprite(32,0);	
		player1[0] = getSprite(0,0);
		player1[1] = getSprite(112,0);
		player1[2] = getSprite(128,0);
		
		blinkyR[0] = getSprite(0, 16);
		blinkyR[1] = getSprite(64, 16);
		blinkyL[0] = getSprite(16, 16);
		blinkyL[1] = getSprite(80, 16);
		blinkyU[0] = getSprite(32, 16);
		blinkyU[1] = getSprite(96, 16);
		blinkyD[0] = getSprite(48, 16);
		blinkyD[1] = getSprite(112, 16);
		inkyR[0] = getSprite(0, 32);
		inkyR[1] = getSprite(64, 32);
		inkyL[0] = getSprite(16, 32);
		inkyL[1] = getSprite(80, 32);
		inkyU[0] = getSprite(32, 32);
		inkyU[1] = getSprite(96, 32);
		inkyD[0] = getSprite(48, 32);
		inkyD[1] = getSprite(112, 32);
		pinkyR[0] = getSprite(0, 48);
		pinkyR[1] = getSprite(64, 48);
		pinkyL[0] = getSprite(16, 48);
		pinkyL[1] = getSprite(80, 48);
		pinkyU[0] = getSprite(32, 48);
		pinkyU[1] = getSprite(96, 48);
		pinkyD[0] = getSprite(48, 48);
		pinkyD[1] = getSprite(112, 48);
		clydeR[0] = getSprite(0, 64);
		clydeR[1] = getSprite(64, 64);
		clydeL[0] = getSprite(16, 64);
		clydeL[1] = getSprite(80, 64);
		clydeU[0] = getSprite(32, 64);
		clydeU[1] = getSprite(96, 64);
		clydeD[0] = getSprite(48, 64);
		clydeD[1] = getSprite(112, 64);
		
		blueghost[0] = getSprite(0, 80);
		blueghost[1] = getSprite(16, 80);
		
		whiteghost[0] = getSprite(32, 80);
		whiteghost[1] = getSprite(48, 80);
		
		flash[0] = blueghost[0];
		flash[1] = blueghost[1];
		flash[2] = whiteghost[0];
		flash[3] = whiteghost[1];
		
		energizer[0] = getSprite(0, 96);
		energizer[1] = getSprite(16, 96);
		
		died[1] = died[2] = getSprite(0,0);
		
		two_hundred[0] = getScore(128,16);
		two_hundred[1] = two_hundred[0];
		two_hundred[4] = two_hundred[0];
		two_hundred[5] = two_hundred[0];
		two_hundred[8] = two_hundred[0];
		two_hundred[9] = two_hundred[0];
		two_hundred[2] = getScore(128,80);
		two_hundred[3] = two_hundred[2];
		two_hundred[8] = two_hundred[2];
		two_hundred[9] = two_hundred[2];
		
		four_hundred[0] = getScore(128,32);
		four_hundred[1] = four_hundred[0];
		four_hundred[4] = four_hundred[0];
		four_hundred[5] = four_hundred[0];
		four_hundred[8] = four_hundred[0];
		four_hundred[9] = four_hundred[0];
		four_hundred[2] = getScore(128,96);
		four_hundred[3] = four_hundred[2];
		four_hundred[8] = four_hundred[2];
		four_hundred[9] = four_hundred[2];
		
		eight_hundred[0] = getScore(128,48);
		eight_hundred[1] = eight_hundred[0];
		eight_hundred[4] = eight_hundred[0];
		eight_hundred[5] = eight_hundred[0];
		eight_hundred[8] = eight_hundred[0];
		eight_hundred[9] = eight_hundred[0];
		eight_hundred[2] = getScore(128,112);
		eight_hundred[3] = eight_hundred[2];
		eight_hundred[8] = eight_hundred[2];
		eight_hundred[9] = eight_hundred[2];
		
		sixteen_hundred[0] = getScore(128,64);
		sixteen_hundred[1] = sixteen_hundred[0];
		sixteen_hundred[4] = sixteen_hundred[0];
		sixteen_hundred[5] = sixteen_hundred[0];
		sixteen_hundred[8] = sixteen_hundred[0];
		sixteen_hundred[9] = sixteen_hundred[0];
		sixteen_hundred[2] = getScore(128,128);
		sixteen_hundred[3] = sixteen_hundred[2];
		sixteen_hundred[8] = sixteen_hundred[2];
		sixteen_hundred[9] = sixteen_hundred[2];
	}
	
	public static BufferedImage getSprite(int xx, int yy)
	{
		return spritesheet.getSubimage(xx, yy, 16, 16);
	}
	
	public static BufferedImage getScore(int xx, int yy)
	{
		return spritesheet.getSubimage(xx, yy, 32, 16);
	}
}

