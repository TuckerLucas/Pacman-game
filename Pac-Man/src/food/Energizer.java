package food;

import java.awt.Graphics;

import character.Ghost;
import Game.Animation;
import Game.Level;
import main.GamePanel;
import main.Sounds;

public class Energizer extends Food
{
	private static final long serialVersionUID = 1L;
		
	private int points = 50;
	
	public static boolean isActive = false;
	
	public static double elapsedTimeWhileActiveInSeconds = 0.0; 
	private final double activeTargetTimeInSeconds = 8.0;
	
	private double elapsedFrameTimeInSeconds = 0;			
	private double targetTimePerFrameInSeconds = 0.2;
	
	private static int frameIndex = 0;
	private int totalNumberOfFrames = Animation.energizerSprites.length;	
	
	public Energizer(int x, int y, GamePanel gp)
	{
		super(gp);
		
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
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
		if(!isActive)
		{
			return;
		}
		
		if(elapsedTimeWhileActiveInSeconds < activeTargetTimeInSeconds)	
		{
			checkIfEnergizerTimeNearlyOver();
		}
		else if(elapsedTimeWhileActiveInSeconds >= activeTargetTimeInSeconds)		
		{
			deactivate();
		}
	}
	
	public void checkIfEnergizerTimeNearlyOver()
	{
		elapsedTimeWhileActiveInSeconds += gp.secondsPerTick;
		
		if(elapsedTimeWhileActiveInSeconds >= Ghost.timeInstantToBeginFlashingInSeconds)
		{
			Ghost.startFlashing();
		}
	}
	
	public static void activate()
	{	
		Sounds.playSoundEffect(Sounds.eatenEnergizerSoundPath);
		
		Energizer.elapsedTimeWhileActiveInSeconds = 0.0f;
		Energizer.isActive = true;	
		Ghost.stopFlashing();
		
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
		g.drawImage(Animation.energizerSprites[frameIndex], x, y, width, height, null);	
	}
}