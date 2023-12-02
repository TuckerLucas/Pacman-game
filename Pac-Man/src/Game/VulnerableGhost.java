package Game;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	public static int frameIndexVulnerable = 0;
	private static double elapsedFrameTimeInSecondsVulnerable = 0;		
	private static double targetTimePerFrameInSecondsVulnerable = 0.2;
	private static int totalNumberOfFramesVulnerable = Animation.vulnerableGhostSprites.length;
	
	public static void manageAnimationTiming()
	{
		elapsedFrameTimeInSecondsVulnerable += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsVulnerable >= targetTimePerFrameInSecondsVulnerable)
		{
			elapsedFrameTimeInSecondsVulnerable = 0;
			frameIndexVulnerable++;
			
			if(frameIndexVulnerable == totalNumberOfFramesVulnerable)
			{
				frameIndexVulnerable = 0;
			}
		}
	}
}
