package main;

import entity.Ghost_Hostile;
import entity.Pacman_Alive;
import entity.SpawnBoxDoor;

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
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "right";
		gp.ghostArray[i].x = 320;
		gp.ghostArray[i].y = 256;
		gp.ghostArray[i].movementType = "random";
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "right";
		gp.ghostArray[i].x = 288;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "up";
		gp.ghostArray[i].x = 320;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "left";
		gp.ghostArray[i].x = 352;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
	}
	
	public void setPacman()
	{
		gp.pacman = new Pacman_Alive(gp);
		gp.pacman.x = 320;
		gp.pacman.y = 512;
		gp.pacman.currentDir = "right";
		gp.pacman.nextDir = "stopped";
		gp.pacman.solidArea.x = gp.pacman.x + 12;
		gp.pacman.solidArea.y = gp.pacman.y + 12;
		gp.pacman.solidArea.width = 10;
		gp.pacman.solidArea.height = 10;
	}
	
	public void setDoor()
	{
		gp.spawnBoxDoor = new SpawnBoxDoor(gp);
		gp.spawnBoxDoor.x = 320;
		gp.spawnBoxDoor.y = 288;
	}
}
