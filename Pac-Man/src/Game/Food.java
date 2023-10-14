/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Food.java
* 
* Description: 
* 
* This file contains the implementation for the Food present
* within the game map.
* 
/**************************************************************/

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Food extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public Food()
	{
		
	}
	
	public static void eat(int food)
	{	
		if(Level.food.get(food) instanceof Pellet)
		{
			Game.score += Pellet.pelletScore;
		}
		else if(Level.food.get(food) instanceof Energizer)
		{
			Game.score += Energizer.energizerScore;
			Energizer.activate();
		}
		
		Level.food.remove(food);
	}
	
	// Render object
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);				
		g.fillRect(x,y,width,height);	
	}
}
