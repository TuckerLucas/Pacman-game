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
	
	public static int activeTime = 0; 
	public static int activeTargetTime = 60*8;
	
	public static boolean isActive   = false;
	
	// Constructor
	public Energizer(int x, int y)
	{
		setBounds(x+2,y+2,28,28);
	}
	
	public static void activate(int energizer)
	{
		new Sounds(Sounds.energizerSoundPath);
		
		Energizer.isActive = true;	
		Energizer.activeTime = 0;
		
		Level.energizers.remove(energizer);
		
		Game.score += Game.energizerScore;
		
		// Make ghosts vulnerable
		Pacman.makeGhostsVulnerable();
	}
	
	public static void deactivate()
	{
		Energizer.activeTime = 0;
		Energizer.isActive = false;
		
		// Make ghosts normal
		Pacman.makeGhostsNormal();
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
		
		// Energizer is active
		if(Energizer.isActive == true)
		{
			// Energizer time over
			if(Energizer.activeTime == Energizer.activeTargetTime)		
			{
				deactivate();
			}
			// Energizer time not over yet
			else if(Energizer.activeTime < Energizer.activeTargetTime)	
			{
				if(activeTime >= Ghost.flashTime)
				{
					Ghost.isFlashing = true;
				}
				else if(activeTime < Ghost.flashTime)
				{
					Ghost.isFlashing = false;
				}
				
				activeTime++;
			}
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