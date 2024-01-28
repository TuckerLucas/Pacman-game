package ai;

import main.GamePanel;
import character.Character;

public class PathFinder 
{
	GamePanel gp;
	
	public int pathFinderArray[][] = new int[16][4];
	
	public PathFinder(GamePanel gp)
	{
		this.gp = gp;
		loadPathFinder();
	}
	
	public void tick()
	{
		updateDistanceToPacman();
	}
	
	private void updateDistanceToPacman()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			gp.ghostArray[i].deltaX = gp.ghostArray[i].x - gp.pacman.x;
			gp.ghostArray[i].deltaY = gp.ghostArray[i].y - gp.pacman.y;
		}
	}
	
	public void loadPathFinder()
	{	
		for(int i = 0; i < 16; i++)
		{
			switch(i)
			{
				case 0:
					pathFinderArray[i][0] = Character.left;
					pathFinderArray[i][1] = -1;
					pathFinderArray[i][2] = Character.downwards;
					pathFinderArray[i][3] = Character.upwards;
					break;
				case 1:
					pathFinderArray[i][0] = Character.left;
					pathFinderArray[i][1] = Character.upwards;
					pathFinderArray[i][2] = Character.downwards;
					break;
				case 2:
					pathFinderArray[i][0] = Character.upwards;
					pathFinderArray[i][1] = Character.left;
					pathFinderArray[i][2] = Character.right;
					break;
				case 3:
					pathFinderArray[i][0] = Character.upwards;
					pathFinderArray[i][1] = Character.left;
					pathFinderArray[i][2] = Character.right;
					break;
				case 4:
					pathFinderArray[i][0] = Character.upwards;
					pathFinderArray[i][1] = -1;
					pathFinderArray[i][2] = Character.right;
					pathFinderArray[i][3] = Character.left;
					break;
				case 5:
					pathFinderArray[i][0] = Character.upwards;
					pathFinderArray[i][1] = Character.right;
					pathFinderArray[i][2] = Character.left;
					break;
				case 6:
					pathFinderArray[i][0] = Character.upwards;
					pathFinderArray[i][1] = Character.right;
					pathFinderArray[i][2] = Character.left;
					break;
				case 7:
					pathFinderArray[i][0] = Character.right;
					pathFinderArray[i][1] = Character.upwards;
					pathFinderArray[i][2] = Character.downwards;
					break;
				case 8:
					pathFinderArray[i][0] = Character.right;
					pathFinderArray[i][1] = -1;
					pathFinderArray[i][2] = Character.upwards;
					pathFinderArray[i][3] = Character.downwards;
					break;
				case 9:
					pathFinderArray[i][0] = Character.right;
					pathFinderArray[i][1] = Character.downwards;
					pathFinderArray[i][2] = Character.upwards;
					break;
				case 10:
					pathFinderArray[i][0] = Character.downwards;
					pathFinderArray[i][1] = Character.right;
					pathFinderArray[i][2] = Character.left;
					break;
				case 11:
					pathFinderArray[i][0] = Character.downwards;
					pathFinderArray[i][1] = Character.right;
					pathFinderArray[i][2] = Character.left;
					break;
				case 12:
					pathFinderArray[i][0] = Character.downwards;
					pathFinderArray[i][1] = -1;
					pathFinderArray[i][2] = Character.right;
					pathFinderArray[i][3] = Character.left;
					break;
				case 13:
					pathFinderArray[i][0] = Character.downwards;
					pathFinderArray[i][1] = Character.left;
					pathFinderArray[i][2] = Character.right;
					break;
				case 14:
					pathFinderArray[i][0] = Character.downwards;
					pathFinderArray[i][1] = Character.left;
					pathFinderArray[i][2] = Character.right;
					break;
				case 15:
					pathFinderArray[i][0] = Character.left;
					pathFinderArray[i][1] = Character.downwards;
					pathFinderArray[i][2] = Character.upwards;
					break;
			}
		}
	}
	
	public int updatePacmanZone(int deltaX, int deltaY)
	{	
		if(deltaX > 0 && deltaY == 0)										
		{
			return 0;	
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX > deltaY)			
		{
			return 1;										
		}
		else if(deltaX > 0 && deltaY > 0 && deltaX == deltaY)					
		{
			return 2;										
		}
		else if(deltaX > 0 && deltaY > 0 && deltaY > deltaX)						
		{
			return 3;										
		}
		else if(deltaX == 0 && deltaY > 0)									
		{
			return 4;		
		}
		else if(deltaX < 0 && deltaY > 0 && deltaY > -deltaX)						
		{
			return 5;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX == deltaY)					
		{
			return 6;										
		}
		else if(deltaX < 0 && deltaY > 0 && -deltaX > deltaY)						
		{
			return 7;										
		}
		else if(deltaX < 0 && deltaY == 0)										
		{
			return 8;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaX > -deltaY)						
		{
			return 9;										
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY == -deltaX)						
		{
			return 10;									
		}
		else if(deltaX < 0 && deltaY < 0 && -deltaY > -deltaX)						
		{
			return 11;									
		}
		else if(deltaX == 0 && deltaY < 0)										
		{
			return 12;									
		}
		else if(deltaX > 0 && deltaY < 0 && -deltaY > deltaX)						
		{
			return 13;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX == -deltaY)	 					
		{
			return 14;									
		}
		else if(deltaX > 0 && deltaY < 0 && deltaX > -deltaY)	 					
		{
			return 15;									
		}
		
		return 0;
	}
}
