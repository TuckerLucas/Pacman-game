package food;

import java.awt.Graphics;

import main.GamePanel;

public class Food_Energizer extends Food
{
	private static final long serialVersionUID = 1L;
		
	private int points = 50;
	
	public static double elapsedTimeWhileActiveInSeconds = 0.0; 
	private final double activeTargetTimeInSeconds = 8.0;
	
	private double elapsedFrameTimeInSeconds = 0;			
	private double targetTimePerFrameInSeconds = 0.2;
	
	private static int frameIndex = 0;
	private int totalNumberOfFrames = gp.animation.energizerSprites.length;	
	
	public Food_Energizer(GamePanel gp)
	{
		super(gp);
	}
	
	public void tick()
	{	
		manageAnimationTiming();
		checkEnergizerActivity();
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;	
			frameIndex++;
		}
		
		if(frameIndex >= totalNumberOfFrames)
		{
			frameIndex = 0;
		}
	}
	
	public void checkEnergizerActivity()
	{
		if(gp.isActive == false)
		{
			return;
		}
		
		if(elapsedTimeWhileActiveInSeconds < activeTargetTimeInSeconds)	
		{
			checkIfEnergizerTimeNearlyOver();
		}
		else if(elapsedTimeWhileActiveInSeconds >= activeTargetTimeInSeconds)		
		{
			gp.flashingToHostile();
		}
	}
	
	public void checkIfEnergizerTimeNearlyOver()
	{
		elapsedTimeWhileActiveInSeconds += gp.secondsPerTick;
		
		if(elapsedTimeWhileActiveInSeconds >= gp.timeInstantToBeginFlashingInSeconds)
		{
			gp.vulnerableToFlashing();
		}
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