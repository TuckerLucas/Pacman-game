package food;

import java.awt.Graphics;

import main.GamePanel;

public class Food_Energizer extends Food
{
	private static final long serialVersionUID = 1L;
		
	private int points = 50;
	
	public Food_Energizer(GamePanel gp)
	{
		super(gp);
		
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;		
		targetTimePerFrameInSeconds = 0.2;
		totalNumberOfFrames = gp.animation.energizerSprites.length;
	}
	
	public void tick()
	{	
		manageAnimationTiming(this);
	}
	
	public int getFoodPoints()
	{
		return points;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.energizerSprites[frameIndex], x, y, width, height, null);	
	}
}