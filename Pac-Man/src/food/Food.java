package food;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Food extends Rectangle
{	
	Game game;
	
	private static final long serialVersionUID = 1L;
	
	public Food(Game game)
	{
		this.game = game;
	}
	
	public int getFoodPoints() {return -1;};
	public void render(Graphics g) {};
}
