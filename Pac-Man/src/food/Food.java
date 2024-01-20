package food;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.GamePanel;

public class Food extends Rectangle
{	
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Food(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public int getFoodPoints() {return -1;};
	public void render(Graphics g) {};
}
