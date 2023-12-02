package Game;

public class FlashingGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static int frameIndex = 0;
	private static double elapsedFrameTimeInSeconds = 0;
	private static double targetTimePerFrameInSeconds = 0.2;
	private static int totalNumberOfFrames = Animation.flashingGhostSprites.length;
	
	public static void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			
			if(frameIndex == totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
	}
}
