package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	public static Wall[][] wallMatrix;
	
	public Wall(int x, int y)
	{	
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(0, 0, 170));
		g.fillRect(x, y, width, height);
	}
}
