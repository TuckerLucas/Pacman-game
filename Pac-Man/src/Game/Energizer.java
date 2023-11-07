package Game;

import java.awt.Graphics;

public class Energizer extends Food
{
	private static final long serialVersionUID = 1L;
		
	private int points = 50;
	
	public static boolean isActive = false;
	
	public static double timeActiveInSeconds = 0.0; 
	private final double timeActiveTargetInSeconds = 8.0;
	
	private static int frameIndex = 0;
	private int totalNumberOfFrames = Texture.energizer.length;
	
	private double currentFrameTimeInSeconds = 0;			
	private double frameTargetTimeInSeconds = 0.2;	
	
	public static Energizer energizer;
	
	public Energizer(int x, int y)
	{
		setBounds(x, y, Texture.objectWidth, Texture.objectHeight);
	}
	
	public void tick()
	{	
		runAnimation();
		checkStatus();
	}
	
	public void runAnimation()
	{
		currentFrameTimeInSeconds += Game.secondsPerTick;
		
		if(currentFrameTimeInSeconds >= frameTargetTimeInSeconds)
		{
			currentFrameTimeInSeconds = 0;	
			frameIndex++;
		}
		
		if(frameIndex >= totalNumberOfFrames)
		{
			frameIndex = 0;
		}
	}
	
	public void checkStatus()
	{
		if(!isActive)
		{
			return;
		}
		
		if(timeActiveInSeconds < timeActiveTargetInSeconds)	
		{
			checkActiveStatus();
		}
		else if(timeActiveInSeconds >= timeActiveTargetInSeconds)		
		{
			deactivate();
		}
	}
	
	public void checkActiveStatus()
	{
		timeActiveInSeconds += Game.secondsPerTick;
		
		if(timeActiveInSeconds >= VulnerableGhost.timeInstantToBeginFlashingInSeconds)
		{
			VulnerableGhost.startFlashing();
		}
	}
	
	public static void activate()
	{
		Sounds.playSoundEffect(Sounds.eatenEnergizerSoundPath);
		Energizer.timeActiveInSeconds = 0.0f;
		Energizer.isActive = true;	
		VulnerableGhost.isFlashing = false;
		
		Ghost.turnAllVulnerable();
	}
	
	public static void deactivate()
	{		
		Ghost.turnAllHostile();
	}
	
	public int getFoodPoints()
	{
		return points;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Texture.energizer[frameIndex], x, y, width, height, null);	
	}
}