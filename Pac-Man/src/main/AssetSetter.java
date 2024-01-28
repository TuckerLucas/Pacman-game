package main;

import Game.SpawnBoxDoor;
import character.AlivePacman;
import character.Character;
import character.Ghost;
import character.HostileGhost;

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
		
		gp.ghostArray[i] = new HostileGhost(i, Character.right, 320, 256, Ghost.randomMovement, false, gp); i++;
		gp.ghostArray[i] = new HostileGhost(i, Character.right, 288, 320, Ghost.randomMovement, false, gp); i++;
		gp.ghostArray[i] = new HostileGhost(i, Character.upwards, 320, 320, Ghost.randomMovement, false, gp); i++;
		gp.ghostArray[i] = new HostileGhost(i, Character.left, 352, 320, Ghost.randomMovement, false, gp);
	}
	
	public void setPacman()
	{
		gp.pacman = new AlivePacman(Character.right, Character.right, 320, 512, gp);
	}
	
	public void setDoor()
	{
		gp.spawnBoxDoor = new SpawnBoxDoor(gp, 320, 288);
	}
}
