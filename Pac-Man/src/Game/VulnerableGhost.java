package Game;

import java.awt.Graphics;
import java.util.Random;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	private static int nextDir = 0;
	private static int currentDir = 0;
	
	private int imageIndexEnemy = 0;
	
	private int timeImage = 0;		
	private int targetTimeImage = 4;
	
	private Random randomGen;

	
	VulnerableGhost(int x, int y, int ID, int movement, int portalStatus)
	{
		ghostID = ID;                  			// Update ghost ID
		movementType = movement; 			// Set default ghost movement type
		portalCrossingStatus = portalStatus;
		
		spawnGhost(x, y);
		
		randomGen = new Random();
		generateNextDirection();
	}
	
	private void generateNextDirection()
	{
		nextDir = randomGen.nextInt(4);
		
		if(!canLeaveSpawnBox(timeSpentInSpawnBoxInSeconds) && nextDir == movingUpwards)
		{
			nextDir = currentDir;
		}
	}
	
	private void spawnGhost(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}
	
	private void flashGhost(Graphics g)
	{
		g.drawImage(Texture.flashGhost[Texture.flashAnimationPhase], x, y, width, height, null);
	}
	
	private void stayBlue(Graphics g)
	{
		g.drawImage(Texture.blueGhost[imageIndexEnemy], x, y, width, height, null);
	}
	
	private void animation()
	{
		timeImage ++;
		
		if(timeImage == targetTimeImage)
		{
			timeImage = 0;
			imageIndexEnemy ++;
		}
		
		flashAnimationTime++;
		
		if(flashAnimationTime == flashAnimationTargetTime)
		{
			flashAnimationTime = 0;
			
			if(Texture.flashAnimationPhase == 3)
			{
				Texture.flashAnimationPhase = 0;
			}
			else
			{
				Texture.flashAnimationPhase++;
			}
		}
	}
	public void render(Graphics g)
	{
		if(imageIndexEnemy == 2)
		{
			imageIndexEnemy = 0;
		}
		
		if(isFlashing)
		{
			flashGhost(g);
		}
		else if(!isFlashing)
		{
			stayBlue(g);
		}
	}
	
	private void moveRandomly()
	{
		// Ghost can move in the next defined direction
		if(canMove(nextDir, this))
		{
			currentDir = nextDir;
			// Move in the next defined direction
			moveGivenCharacterInGivenDirection(this, nextDir);
		}
		// Ghost can move in the current defined direction
		else if(canMove(currentDir, this))
		{
			// Continue moving in the current defined direction
			moveGivenCharacterInGivenDirection(this, currentDir);
			
			// Will continue moving in the current direction until can move in the next one
			return;
		}
		
		// Generate a new direction for the ghost to move in
		generateNextDirection();
	}
	
	public void tick()
	{
		moveRandomly();
		animation();
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	public void setPortalCrossingStatus(int portalStatus)
	{
		portalCrossingStatus = portalStatus;
	}
	
	public int getNextDirection()
	{
		return nextDir;
	}
	
	public int getID()
	{
		return ghostID;
	}
	
	int getPortalCrossingStatus() 
	{
		return portalCrossingStatus;
	}

	int getMovementType() 
	{
		return movementType;
	}
}
