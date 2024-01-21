package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.GamePanel;

public class SpawnBoxDoor extends Rectangle 
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public SpawnBoxDoor(GamePanel gp)
	{
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height/6);
	}
}
