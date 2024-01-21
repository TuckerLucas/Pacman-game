package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.GamePanel;

public class Wall extends Rectangle 
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Wall(int x, int y, GamePanel gp)
	{	
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(0, 0, 170));
		g.fillRect(x, y, width, height);
	}
}
