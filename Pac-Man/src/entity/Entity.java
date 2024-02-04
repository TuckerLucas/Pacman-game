package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Entity extends Rectangle
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Entity(GamePanel gp)
	{
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
}