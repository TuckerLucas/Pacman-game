package main;

import entity.Ghost_Flashing;
import entity.Ghost_Hostile;
import entity.Ghost_Vulnerable;

public class CollisionChecker 
{
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void tick()
	{
		pacmanAndPellet();
		pacmanAndEnergizer();
		pacmanAndHostileGhost();
		pacmanAndVulnerableGhost();
	}
	
	private void pacmanAndHostileGhost()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i] instanceof Ghost_Hostile)
			{
				if(gp.ghostArray[i].solidArea.intersects(gp.pacman.solidArea))
				{	
					gp.eHandler.die(gp.pacman);
				}
			}
		}
	}
	
	private void pacmanAndVulnerableGhost()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i] instanceof Ghost_Vulnerable
						|| gp.ghostArray[i] instanceof Ghost_Flashing)
			{
				if(gp.pacman.intersects(gp.ghostArray[i].solidArea))
				{
					gp.eHandler.eatGhost(i);
				}
			}
		}
	}
	
	private void pacmanAndPellet()
	{
		for(int i = 0; i < gp.pelletList.size(); i++) 		
		{    
			if(gp.pacman.solidArea.intersects(gp.pelletList.get(i)))							
			{
				gp.eHandler.eatFood(gp.pelletList.get(i));
				return;
			}
		}
	}
	
	private void pacmanAndEnergizer()
	{
		for(int i = 0; i < gp.energizerList.size(); i++) 		
		{    
			if(gp.pacman.solidArea.intersects(gp.energizerList.get(i)))							
			{
				gp.eHandler.eatFood(gp.energizerList.get(i));
				return;
			}
		}
	}
}
