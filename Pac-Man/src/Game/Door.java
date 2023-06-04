/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Door.java
* 
* Description: 
* 
* This file contains the implementation for the Door present in 
* the game map.
* 
/**************************************************************/

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Door extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	public Door(int x, int y)
	{
		setBounds(x,y,32,32);
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(255,0,8));
		g.fillRect(x, y, width, height/6);
	}
}
