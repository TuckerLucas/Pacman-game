package Game;

import java.awt.Graphics; // remove when ghost abstract
import java.util.Random;

public class HostileGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	private int ghostID;
	
	private int nextDir = 0;
	public int currentDir = 0;
	
	private Random randomGen;
	
	public HostileGhost(int xCoordinate, int yCoordinate, int ID)
	{	
		ghostID = ID;
		spawnGhost(xCoordinate, yCoordinate);
		randomGen = new Random();
	}
	
	void tick()
	{
		moveRandomly();
		manageHostileGhostAnimationTiming();
	}
	
	protected void moveRandomly()
	{
		/*
		updateDistanceToPacman();
		
		if(coolDown == true)
		{
			coolDownTime++;
			
			if(coolDownTime == coolDownTargetTime)
			{
				coolDown = false;
				
				coolDownTime = 0;
			}
		}
		else if(coolDown == false)
		{
			if(pacmanIsClose() && !isVulnerable && !isInSpawnBox(this))
			{
				movementType = methodicalMovement;
			}
		}*/
		
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
			
			move(this, nextDir);
		}
		else if(canMove(this, currentDir))
		{
			move(this, currentDir);
			
			return;
		}
		
		generateNextDirection();
	}
	
	protected void generateNextDirection()
	{
		nextDir = randomGen.nextInt(4);
	}
	
	void render(Graphics g)
	{
		g.drawImage(Texture.ghostLook[ghostID][currentDir][frameIndexHostile], x, y, width, height, null);
	}
}
