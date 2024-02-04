package food;

import java.awt.Color;
import java.awt.Graphics;

import main.GamePanel;
import main.Sounds;

public class Food_Pellet extends Food
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	private int points = 10;

	public Food_Pellet(int x, int y, GamePanel gp)
	{
		super(gp);
		/*
		this.gp = gp;
		this.x = x+12;
		this.y = y+12;
		this.width = 8;
		this.height = 8;*/
		setBounds(x+12, y+12, 8, 8);
	}
	
	public static void getEaten()
	{
		Sounds.playSoundEffect(Sounds.pacmanEatingSoundPath);
	}
	
	public int getFoodPoints()
	{
		return points;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);				
		g.fillRect(x, y, width, height);	
	}
}
