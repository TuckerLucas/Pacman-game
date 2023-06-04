/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Energizer.java
* 
* Description: 
* 
* This file contains the implementation for the Energizers
* displayed in the game map.
* 
/**************************************************************/

package PacManJogo;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Energizer extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	private int time 			 = 0;
	private int targetTime 		 = 10;
	public static int imageIndex = 0;					
	
	public Energizer(int x, int y)
	{
		setBounds(x+2,y+2,28,28);
	}
	
	public void tick()
	{
		time ++;
		
		if(time == targetTime)
		{
			time = 0;
			imageIndex ++;
		}
	}
	
	public void render(Graphics g)
	{
		if(imageIndex >= 2)
		{
			imageIndex = 0;
		}
		
		g.drawImage(Texture.energizer[imageIndex], x, y, width, height, null);	
	}
}
