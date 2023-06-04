/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Food.java
* 
* Description: 
* 
* This file contains the implementation for the Food
* displayed in the game map.
* 
/**************************************************************/

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Food extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public Food(int x, int y)
	{
		setBounds(x+12,y+12,8,8);			
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);				
		g.fillRect(x,y,width,height);	
	}
}
