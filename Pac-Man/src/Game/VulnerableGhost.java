package Game;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public VulnerableGhost()
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public static void startFlashing()
	{
		isFlashing = true;
	}
	
	public void render()
	{
		
	}
}