package entity;

import java.awt.Color;
import java.awt.Graphics;

import main.GamePanel;

public class Wall extends Entity 
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Wall(GamePanel gp)
	{	
		super(gp);
		this.gp = gp;
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(0, 0, 170));
		g.fillRect(x, y, width, height);
	}
}
