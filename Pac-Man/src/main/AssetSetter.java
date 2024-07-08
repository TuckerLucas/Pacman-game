package main;

import entity.Ghost_Hostile;
import entity.Ghost_Vulnerable;
import entity.Pacman_Alive;
import entity.SpawnBoxDoor;
import food.Food_Energizer;
import food.Food_Pellet;

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
		gp.ghostArray[i].solidArea.x = gp.ghostArray[i].x + 12;
		gp.ghostArray[i].solidArea.y = gp.ghostArray[i].y + 12;
		gp.ghostArray[i].solidArea.width = 10;
		gp.ghostArray[i].solidArea.height = 10;
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "right";
		gp.ghostArray[i].x = 288;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
		gp.ghostArray[i].solidArea.x = gp.ghostArray[i].x + 12;
		gp.ghostArray[i].solidArea.y = gp.ghostArray[i].y + 12;
		gp.ghostArray[i].solidArea.width = 10;
		gp.ghostArray[i].solidArea.height = 10;
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "up";
		gp.ghostArray[i].x = 320;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
		gp.ghostArray[i].solidArea.x = gp.ghostArray[i].x + 12;
		gp.ghostArray[i].solidArea.y = gp.ghostArray[i].y + 12;
		gp.ghostArray[i].solidArea.width = 10;
		gp.ghostArray[i].solidArea.height = 10;
		
		i++;
		
		gp.ghostArray[i] = new Ghost_Hostile(gp, i); 
		gp.ghostArray[i].currentDir = "left";
		gp.ghostArray[i].x = 352;
		gp.ghostArray[i].y = 320;
		gp.ghostArray[i].movementType = "random";
		gp.ghostArray[i].solidArea.x = gp.ghostArray[i].x + 12;
		gp.ghostArray[i].solidArea.y = gp.ghostArray[i].y + 12;
		gp.ghostArray[i].solidArea.width = 10;
		gp.ghostArray[i].solidArea.height = 10;
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
	
	public void setIntroScreenEntities()
	{
		int screenX = gp.tileSize*4;
		int screenY = gp.tileSize*6;
		
		gp.introPacman = new Pacman_Alive(gp);
		gp.introPacman.currentDir = "right";
		gp.introPacman.nextDir = "stopped";
		gp.introPacman.x = screenX;
		gp.introPacman.y = screenY;
		
		screenY += gp.tileSize*2;
		
		for(int i = 0; i < gp.introGhostArray.length; i++)
		{
			gp.introGhostArray[i] = new Ghost_Hostile(gp, i); 
			gp.introGhostArray[i].currentDir = "right";
			gp.introGhostArray[i].x = screenX;
			gp.introGhostArray[i].y = screenY;
			gp.introGhostArray[i].ghostID = i;
			
			screenY += gp.tileSize;
		}
		
		screenY += gp.tileSize;
		
		gp.introPellet = new Food_Pellet(gp);
		gp.introPellet.x = screenX;
		gp.introPellet.y = screenY;
		
		screenY += gp.tileSize;
		
		gp.introEnergizer = new Food_Energizer(gp);
		gp.introEnergizer.x = screenX;
		gp.introEnergizer.y = screenY;
		
		screenY += gp.tileSize;
		
		gp.introVulnerableGhost = new Ghost_Vulnerable(gp, 0);
		gp.introVulnerableGhost.currentDir = "right";
		gp.introVulnerableGhost.x = screenX;
		gp.introVulnerableGhost.y = screenY;
	}
}
