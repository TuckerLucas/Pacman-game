package Game;

public class Zone 
{
	public Zone()
	{
		
	}
	
	public static void updateMethodicalDirections(Ghost ghost, int zone)
	{	
		switch(zone)
		{
			case 0:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.downwards;
				ghost.findDir2 = Character.upwards;
				break;
			case 1:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = Character.upwards;
				ghost.findDir1 = Character.downwards;
				break;
			case 2:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 3:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 4:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.right;
				ghost.findDir2 = Character.left;
				break;
			case 5:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 6:
				ghost.methodicalDir1 = Character.upwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 7:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = Character.upwards;
				ghost.findDir1 = Character.downwards;
				break;
			case 8:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.upwards;
				ghost.findDir2 = Character.downwards;
				break;
			case 9:
				ghost.methodicalDir1 = Character.right;
				ghost.methodicalDir2 = Character.downwards;
				ghost.findDir1 = Character.upwards;
				break;
			case 10:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 11:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.right;
				ghost.findDir1 = Character.left;
				break;
			case 12:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = -1;
				ghost.findDir1 = Character.right;
				ghost.findDir2 = Character.left;
				break;
			case 13:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 14:
				ghost.methodicalDir1 = Character.downwards;
				ghost.methodicalDir2 = Character.left;
				ghost.findDir1 = Character.right;
				break;
			case 15:
				ghost.methodicalDir1 = Character.left;
				ghost.methodicalDir2 = Character.downwards;
				ghost.findDir1 = Character.upwards;
				break;
		}
	}	
}
