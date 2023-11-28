package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SpawnBoxDoor extends Rectangle 
{
	private static final long serialVersionUID = 1L;

	public static SpawnBoxDoor spawnBoxDoor;
	
	public SpawnBoxDoor(int x, int y)
	{
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height/6);
	}
}
