package main;

import Game.SpawnBoxDoor;
import character.AlivePacman;
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
		gp.ghostArray[i].x = gp.tileSize*10;
		gp.ghostArray[i].y = gp.tileSize*8;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*9;
		gp.ghostArray[i].y = gp.tileSize*10;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*10;
		gp.ghostArray[i].y = gp.tileSize*10;
		i++;
		
		gp.ghostArray[i] = new Ghost(i, Ghost.randomMovement, Character.notCrossingPortal, false, gp);
		gp.ghostArray[i].x = gp.tileSize*11;
		gp.ghostArray[i].y = gp.tileSize*10;
	}
	
	public void setPacman()
	{
		gp.pacman = new AlivePacman(Character.right, Character.right, 320, 512, gp);
		gp.pacman.x = gp.tileSize*10;
		gp.pacman.y = gp.tileSize*16;
	}
	
	public void setDoor()
	{
		gp.spawnBoxDoor = new SpawnBoxDoor(gp);
		gp.spawnBoxDoor.x = gp.tileSize*10;
		gp.spawnBoxDoor.y = gp.tileSize*9;
	}
}
