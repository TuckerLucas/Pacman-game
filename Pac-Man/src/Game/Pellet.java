package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Pellet extends Food
{
	private static final long serialVersionUID = 1L;
	
	public static int points = 10;

	public Pellet(int x, int y)
	{
		setBounds(x+12,y+12,8,8);
	}
	
	public int getFoodPoints()
	{
		return points;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);				
		g.fillRect(x,y,width,height);	
	}
}
