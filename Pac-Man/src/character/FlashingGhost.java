package character;

import main.GamePanel;

public class FlashingGhost extends VulnerableGhost
{
	private static final long serialVersionUID = 1L;

	public FlashingGhost(int ID, int cD, int x, int y, int movementStatus, boolean vulnerabilityStatus, GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, vulnerabilityStatus, gp);
	}
}
