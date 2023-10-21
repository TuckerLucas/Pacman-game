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

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public abstract class Food extends Rectangle
{	
	private static final long serialVersionUID = 1L;

	public static List<Food> food;
	
	public Food()
	{
		
	}
	
	public static void eat(Food food)
	{	
		if(food instanceof Energizer)
		{
			Energizer.activate();
		}

		Game.score += food.getFoodPoints();
		Food.food.remove(food);
	}
	
	abstract int getFoodPoints();
	abstract void render(Graphics g);
}
