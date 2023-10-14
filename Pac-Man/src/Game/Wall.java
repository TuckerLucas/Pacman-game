package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	// Constructor
	public Wall(int x, int y)
	{
		setBounds(x,y,32,32);
	}
	
	// Render object
	public void render(Graphics g)
	{
		g.setColor(new Color(0,0,255));
		g.fillRect(x, y, width, height);
	}
}
