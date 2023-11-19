package Game;

import java.awt.Graphics; // remove

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public VulnerableGhost()
	{
		
	}
	
	public static void startFlashing()
	{
		isFlashing = true;
	}
}
