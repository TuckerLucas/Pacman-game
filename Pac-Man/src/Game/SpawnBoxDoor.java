/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Door.java
* 
* Description: 
* 
* This file contains the implementation for the Door present on 
* the exit of the game map's ghost spawn box.
* 
/**************************************************************/

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SpawnBoxDoor extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	public static SpawnBoxDoor spawnBoxDoor;
	
	public SpawnBoxDoor(int x, int y)
	{
		setBounds(x,y,32,32);
	}
	
	// Render object
	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height/6);
	}
}
