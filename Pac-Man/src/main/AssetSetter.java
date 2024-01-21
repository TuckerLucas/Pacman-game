package main;

import character.Character;
import character.Ghost;

public class AssetSetter 
{
	GamePanel gp;
	
	public AssetSetter(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setGhosts()
	{
		int i = 0;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*10 + 16;
		gp.ghostArray[i].y = gp.tileSize*8 + 16;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*9 + 16;
		gp.ghostArray[i].y = gp.tileSize*10 + 16;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*10 + 16;
		gp.ghostArray[i].y = gp.tileSize*10 + 16;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*11 + 16;
		gp.ghostArray[i].y = gp.tileSize*10 + 16;
	}
}
