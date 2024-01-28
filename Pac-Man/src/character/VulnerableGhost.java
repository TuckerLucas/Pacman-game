package character;

import main.GamePanel;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public VulnerableGhost(int ID, int cD, int x, int y, int movementStatus, boolean vulnerabilityStatus,
			GamePanel gp) 
	{
		super(ID, cD, x, y, movementStatus, vulnerabilityStatus, gp);
	}

}
