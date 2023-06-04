/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Tile.java
* 
* Description: 
* 
* This file contains the implementation for the Tiles
* displayed in the game map.
* 
/**************************************************************/

package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	public Tile(int x, int y)
	{
		setBounds(x,y,32,32);
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(50,0,255));
		g.fillRect(x, y, width, height);
	}
}
