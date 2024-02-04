package food;

import java.awt.Color;
import java.awt.Graphics;

import main.GamePanel;

public class Food_Pellet extends Food
{	
	private static final long serialVersionUID = 1L;
	
	private int points = 10;

	public Food_Pellet(GamePanel gp)
	{
		super(gp);	
	}
	
	public int getFoodPoints()
	{
		return points;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);				
		g.fillRect(x, y, width, height);	
	}
}
