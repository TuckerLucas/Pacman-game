package PacManJogo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture
{
	public static BufferedImage[] player;
	public static BufferedImage[] player1;
	
	public static BufferedImage[] ghostr;
	public static BufferedImage[] ghostl;
	public static BufferedImage[] ghostu;
	public static BufferedImage[] ghostd;
	
	public static BufferedImage[] ghost1r;
	public static BufferedImage[] ghost1l;
	public static BufferedImage[] ghost1u;
	public static BufferedImage[] ghost1d;
	
	public static BufferedImage[] ghost2r;
	public static BufferedImage[] ghost2l;
	public static BufferedImage[] ghost2u;
	public static BufferedImage[] ghost2d;
	
	public static BufferedImage[] ghost3r;
	public static BufferedImage[] ghost3l;
	public static BufferedImage[] ghost3u;
	public static BufferedImage[] ghost3d;
	
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
		
		ghostr = new BufferedImage[2];
		ghostl = new BufferedImage[2];
		ghostu = new BufferedImage[2];
		ghostd = new BufferedImage[2];
		
		ghost1r = new BufferedImage[2];
		ghost1l = new BufferedImage[2];
		ghost1u = new BufferedImage[2];
		ghost1d = new BufferedImage[2];
		
		ghost2r = new BufferedImage[2];
		ghost2l = new BufferedImage[2];
		ghost2u = new BufferedImage[2];
		ghost2d = new BufferedImage[2];
		
		ghost3r = new BufferedImage[2];
		ghost3l = new BufferedImage[2];
		ghost3u = new BufferedImage[2];
		ghost3d = new BufferedImage[2];
		
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
		
		ghostr[0] = getSprite(0, 16);
		ghostr[1] = getSprite(64, 16);
		ghostl[0] = getSprite(16, 16);
		ghostl[1] = getSprite(80, 16);
		ghostu[0] = getSprite(32, 16);
		ghostu[1] = getSprite(96, 16);
		ghostd[0] = getSprite(48, 16);
		ghostd[1] = getSprite(112, 16);
		ghost1r[0] = getSprite(0, 32);
		ghost1r[1] = getSprite(64, 32);
		ghost1l[0] = getSprite(16, 32);
		ghost1l[1] = getSprite(80, 32);
		ghost1u[0] = getSprite(32, 32);
		ghost1u[1] = getSprite(96, 32);
		ghost1d[0] = getSprite(48, 32);
		ghost1d[1] = getSprite(112, 32);
		ghost2r[0] = getSprite(0, 48);
		ghost2r[1] = getSprite(64, 48);
		ghost2l[0] = getSprite(16, 48);
		ghost2l[1] = getSprite(80, 48);
		ghost2u[0] = getSprite(32, 48);
		ghost2u[1] = getSprite(96, 48);
		ghost2d[0] = getSprite(48, 48);
		ghost2d[1] = getSprite(112, 48);
		ghost3r[0] = getSprite(0, 64);
		ghost3r[1] = getSprite(64, 64);
		ghost3l[0] = getSprite(16, 64);
		ghost3l[1] = getSprite(80, 64);
		ghost3u[0] = getSprite(32, 64);
		ghost3u[1] = getSprite(96, 64);
		ghost3d[0] = getSprite(48, 64);
		ghost3d[1] = getSprite(112, 64);
		
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

