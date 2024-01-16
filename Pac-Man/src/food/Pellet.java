package food;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import main.Sounds;

public class Pellet extends Food
{
	Game game;
	
	private static final long serialVersionUID = 1L;
	
	private int points = 10;

	public Pellet(int x, int y, Game game)
	{
		super(game);
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
