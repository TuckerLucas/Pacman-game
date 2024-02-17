package ai;

import main.GamePanel;

public class PathFinder 
{
	GamePanel gp;
	
	public String pathFinderArray[][][] = new String[4][16][4];
	
	public int detectionRange = 80;
	
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
					pathFinderArray[0][i][0] = "left";
					pathFinderArray[0][i][1] = "N/A";
					pathFinderArray[0][i][2] = "down";
					pathFinderArray[0][i][3] = "up";
					
					pathFinderArray[1][i][0] = "left";
					pathFinderArray[1][i][1] = "N/A";
					pathFinderArray[1][i][2] = "down";
					pathFinderArray[1][i][3] = "up";
					
					pathFinderArray[2][i][0] = "left";
					pathFinderArray[2][i][1] = "N/A";
					pathFinderArray[2][i][2] = "up";
					pathFinderArray[2][i][3] = "down";
					
					pathFinderArray[3][i][0] = "left";
					pathFinderArray[3][i][1] = "N/A";
					pathFinderArray[3][i][2] = "up";
					pathFinderArray[3][i][3] = "down";
					break;
				case 1:
					
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "left";
						pathFinderArray[j][i][1] = "up";
						pathFinderArray[j][i][2] = "down";
					}
				
					break;
				case 2:
					pathFinderArray[0][i][0] = "up";
					pathFinderArray[0][i][1] = "left";
					pathFinderArray[0][i][2] = "right";
					
					pathFinderArray[1][i][0] = "left";
					pathFinderArray[1][i][1] = "up";
					pathFinderArray[1][i][2] = "down";
					
					pathFinderArray[2][i][0] = "left";
					pathFinderArray[2][i][1] = "up";
					pathFinderArray[2][i][2] = "down";
					
					pathFinderArray[3][i][0] = "up";
					pathFinderArray[3][i][1] = "left";
					pathFinderArray[3][i][2] = "right";
					break;
				case 3:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "up";
						pathFinderArray[j][i][1] = "left";
						pathFinderArray[j][i][2] = "right";
					}
					break;
				case 4:
					pathFinderArray[0][i][0] = "up";
					pathFinderArray[0][i][1] = "N/A";
					pathFinderArray[0][i][2] = "right";
					pathFinderArray[0][i][3] = "left";
					
					pathFinderArray[1][i][0] = "up";
					pathFinderArray[1][i][1] = "N/A";
					pathFinderArray[1][i][2] = "left";
					pathFinderArray[1][i][3] = "right";
					
					pathFinderArray[2][i][0] = "up";
					pathFinderArray[2][i][1] = "N/A";
					pathFinderArray[2][i][2] = "right";
					pathFinderArray[2][i][3] = "left";
					
					pathFinderArray[3][i][0] = "up";
					pathFinderArray[3][i][1] = "N/A";
					pathFinderArray[3][i][2] = "left";
					pathFinderArray[3][i][3] = "right";
					break;
				case 5:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "up";
						pathFinderArray[j][i][1] = "right";
						pathFinderArray[j][i][2] = "left";
					}
					break;
				case 6:
					pathFinderArray[0][i][0] = "up";
					pathFinderArray[0][i][1] = "right";
					pathFinderArray[0][i][2] = "left";
					
					pathFinderArray[1][i][0] = "right";
					pathFinderArray[1][i][1] = "up";
					pathFinderArray[1][i][2] = "down";
					
					pathFinderArray[2][i][0] = "up";
					pathFinderArray[2][i][1] = "right";
					pathFinderArray[2][i][2] = "left";
					
					pathFinderArray[3][i][0] = "right";
					pathFinderArray[3][i][1] = "up";
					pathFinderArray[3][i][2] = "down";
					break;
				case 7:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "right";
						pathFinderArray[j][i][1] = "up";
						pathFinderArray[j][i][2] = "down";
					}
					break;
				case 8:
					pathFinderArray[0][i][0] = "right";
					pathFinderArray[0][i][1] = "N/A";
					pathFinderArray[0][i][2] = "up";
					pathFinderArray[0][i][3] = "down";
					
					pathFinderArray[1][i][0] = "right";
					pathFinderArray[1][i][1] = "N/A";
					pathFinderArray[1][i][2] = "down";
					pathFinderArray[1][i][3] = "up";
					
					pathFinderArray[2][i][0] = "right";
					pathFinderArray[2][i][1] = "N/A";
					pathFinderArray[2][i][2] = "down";
					pathFinderArray[2][i][3] = "up";
					
					pathFinderArray[3][i][0] = "right";
					pathFinderArray[3][i][1] = "N/A";
					pathFinderArray[3][i][2] = "up";
					pathFinderArray[3][i][3] = "down";
					break;
				case 9:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "right";
						pathFinderArray[j][i][1] = "down";
						pathFinderArray[j][i][2] = "up";
					}
					break;
				case 10:
					pathFinderArray[0][i][0] = "down";
					pathFinderArray[0][i][1] = "right";
					pathFinderArray[0][i][2] = "left";
					
					pathFinderArray[1][i][0] = "down";
					pathFinderArray[1][i][1] = "right";
					pathFinderArray[1][i][2] = "left";
					
					pathFinderArray[2][i][0] = "right";
					pathFinderArray[2][i][1] = "down";
					pathFinderArray[2][i][2] = "up";
					
					pathFinderArray[3][i][0] = "right";
					pathFinderArray[3][i][1] = "down";
					pathFinderArray[3][i][2] = "up";
					break;
				case 11:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "down";
						pathFinderArray[j][i][1] = "right";
						pathFinderArray[j][i][2] = "left";
					}
					break;
				case 12:
					pathFinderArray[0][i][0] = "down";
					pathFinderArray[0][i][1] = "N/A";
					pathFinderArray[0][i][2] = "right";
					pathFinderArray[0][i][3] = "left";
					
					pathFinderArray[1][i][0] = "down";
					pathFinderArray[1][i][1] = "N/A";
					pathFinderArray[1][i][2] = "right";
					pathFinderArray[1][i][3] = "left";
					
					pathFinderArray[2][i][0] = "down";
					pathFinderArray[2][i][1] = "N/A";
					pathFinderArray[2][i][2] = "left";
					pathFinderArray[2][i][3] = "right";
					
					pathFinderArray[3][i][0] = "down";
					pathFinderArray[3][i][1] = "N/A";
					pathFinderArray[3][i][2] = "left";
					pathFinderArray[3][i][3] = "right";
					break;
				case 13:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "down";
						pathFinderArray[j][i][1] = "left";
						pathFinderArray[j][i][2] = "right";
					}
					break;
				case 14:
					pathFinderArray[0][i][0] = "down";
					pathFinderArray[0][i][1] = "left";
					pathFinderArray[0][i][2] = "right";
					
					pathFinderArray[1][i][0] = "left";
					pathFinderArray[1][i][1] = "down";
					pathFinderArray[1][i][2] = "up";
					
					pathFinderArray[2][i][0] = "down";
					pathFinderArray[2][i][1] = "left";
					pathFinderArray[2][i][2] = "right";
					
					pathFinderArray[3][i][0] = "left";
					pathFinderArray[3][i][1] = "down";
					pathFinderArray[3][i][2] = "up";
					break;
				case 15:
					for(int j = 0; j < 4; j++)
					{
						pathFinderArray[j][i][0] = "left";
						pathFinderArray[j][i][1] = "down";
						pathFinderArray[j][i][2] = "up";
					}
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
	
	public boolean pacmanIsClose(int deltaX, int deltaY)
	{
		return ((deltaX < detectionRange && deltaX > -detectionRange) && 
				(deltaY < detectionRange && deltaY > -detectionRange)) 
				? true : false;
	}
}
