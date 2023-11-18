package Game;

import java.awt.Graphics; // remove
import java.util.Random;

public class VulnerableGhost extends Ghost
{
	private static final long serialVersionUID = 1L;

	public static boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	private int ghostID;
	
	private int nextDir = 0;
	public int currentDir = 0;
	
	private Random randomGen;
	
	public VulnerableGhost(int ID)
	{
		ghostID = ID;
		spawnGhost(spawnBoxX, spawnBoxY);
		randomGen = new Random();
	}
	
	public static void startFlashing()
	{
		isFlashing = true;
	}
	
	void tick()
	{
		portalEvents(this);
		moveRandomly();
		if(!VulnerableGhost.isFlashing)
		{
			manageVulnerableGhostAnimationTiming();
		}
		else if(VulnerableGhost.isFlashing)
		{
			manageFlashingGhostAnimationTiming();
		}
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
		if(VulnerableGhost.isFlashing)
		{
			g.drawImage(Texture.flashGhost[frameIndexFlash], x, y, width, height, null);
		}
		else if(!VulnerableGhost.isFlashing)
		{
			g.drawImage(Texture.blueGhost[frameIndexBlue], x, y, width, height, null);
		}
	}
}
