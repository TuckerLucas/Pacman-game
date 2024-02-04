package food;

import java.awt.Graphics;

import entity.Entity;
import main.GamePanel;

public class Food extends Entity
{	
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Food(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
	}
	
	public int getFoodPoints() 
	{
		return 0;
	}
	
	public void render(Graphics g) 
	{
		
	}
}
