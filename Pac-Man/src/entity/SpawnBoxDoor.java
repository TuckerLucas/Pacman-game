package entity;

import java.awt.Color;
import java.awt.Graphics;

import main.GamePanel;

public class SpawnBoxDoor extends Entity 
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public SpawnBoxDoor(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height/6);
	}
}
