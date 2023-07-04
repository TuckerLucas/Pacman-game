/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Energizer.java
* 
* Description: 
* 
* This file contains the implementation for the 4 Energizers
* displayed in the game map.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Energizer extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	// Energizer animation variables
	private int frameTime = 0;			// Time current frame has been displayed
	private int frameTargetTime = 10;	// Target time for each frame to be displayed
	private static int spriteFrame = 0;	// Array index of frame being displayed
	private int spriteTargetFrame = 2;	// Array index of last frame of the animation 
	
	public static int time	      = 0; 
	public static int flashTime  = 60*5;
	public static int targetTime = 60*8;
	
	public static boolean flash = false;
	public static boolean status = false;
	
	// Constructor
	public Energizer(int x, int y)
	{
		setBounds(x+2,y+2,28,28);
	}
	
	// Manage animation time
	public void tick()
	{
		frameTime++;
		
		if(frameTime == frameTargetTime)
		{
			frameTime = 0;
			spriteFrame++;
		}
	}
	
	// Render object
	public void render(Graphics g)
	{
		if(spriteFrame >= spriteTargetFrame)
		{
			spriteFrame = 0;
		}
		
		g.drawImage(Texture.energizer[spriteFrame], x, y, width, height, null);	
	}
}
