package Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public abstract class Food extends Rectangle
{	
	private static final long serialVersionUID = 1L;

	public static List<Food> foodList;
	
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
		foodList.remove(food);
	}
	
	abstract int getFoodPoints();
	abstract void render(Graphics g);
}
