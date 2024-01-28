package character;

import java.awt.Graphics;
import java.util.Random;

import Game.Animation;
import main.GamePanel;

public class Ghost extends Character
{
	private static final long serialVersionUID = 1L;
	
	private Random randomGen;
	
	public int ghostID;
	public int movementType;
	public boolean isVulnerable = false;
	
	public static final int randomMovement = 0;
	public static final int methodicalMovement = 1;
	public static final int findingPath = 2;
	
	private boolean findDir1Blocked = false;
	private boolean isFindingPath = false;
	
	private int nextDir = 0;
	public int currentDir = 0;
	
	private double timeMovingMethodicallyInSeconds = 0.0;
	private double targetTimeMovingMethodicallyInSeconds = 12.0; 
	private boolean isCoolingDown = false;
	private double coolDownTimeInSeconds = 0.0;
	private double coolDownTargetTimeInSeconds = 5.0; 
	
	public int deltaX;
	public int deltaY;
	
	private int pacmanZone;
	
	private int frameIndex = 0;
	private double elapsedFrameTimeInSeconds = 0;		
	private double targetTimePerFrameInSeconds = 0.05;
	private int totalNumberOfFrames = Animation.hostileGhostSprites[0][0].length;
	
	private int frameIndexVulnerable = 0;
	private double elapsedFrameTimeInSecondsVulnerable = 0;		
	private double targetTimePerFrameInSecondsVulnerable = 0.05;
	private int totalNumberOfFramesVulnerable = Animation.vulnerableGhostSprites.length;
	
	public static int frameIndexFlashing = 0;
	public static double elapsedFrameTimeInSecondsFlashing = 0;
	public static double targetTimePerFrameInSecondsFlashing = 0.33;
	private int totalNumberOfFramesFlashing = Animation.flashingGhostSprites.length;
	
	public boolean isFlashing = false;
	public static double timeInstantToBeginFlashingInSeconds = 5.0;
	
	public Ghost(int ID, int cD, int x, int y, int movementStatus, boolean vulnerabilityStatus, GamePanel gp)
	{	
		super(gp);
		
		ghostID = ID;                  			
		movementType = movementStatus; 			
		isVulnerable = vulnerabilityStatus; 	
		currentDir = cD;
		setBounds(x, y, gp.tileSize, gp.tileSize);
		
		randomGen = new Random();
		nextDir = randomGen.nextInt(4);
	}
	
	public void tick()
	{	
		if(!isCrossingPortal(ghostID))
		{
			selectGhostMovementType();
		}
		
		manageAnimationTiming();
		manageVulnerableAnimationTiming();
		manageFlashingAnimationTiming();
	}

	public boolean isCrossingPortal(int i)
	{
		if((gp.ghostArray[i].x < 160 || gp.ghostArray[i].x > 480) && gp.ghostArray[i].y == 320)
		{
			if(gp.ghostArray[i].currentDir == left)
			{
				if(gp.ghostArray[i].x == 0 && gp.ghostArray[i].y == 320)
				{
					gp.ghostArray[i] = new Ghost(i, left, 640, 320, gp.ghostArray[i].movementType, gp.ghostArray[i].isVulnerable, gp);
				}
				
				move(gp.ghostArray[i], left);
				
				if(gp.ghostArray[i].x == 480 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			else if(gp.ghostArray[i].currentDir == right)
			{
				if(gp.ghostArray[i].x == 640 && gp.ghostArray[i].y == 320)
				{
					gp.ghostArray[i] = new Ghost(i, right, 0, 320, gp.ghostArray[i].movementType, gp.ghostArray[i].isVulnerable, gp);
				}
				
				move(gp.ghostArray[i], right);
				
				if(gp.ghostArray[i].x == 160 && gp.ghostArray[i].y == 320)
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private void selectGhostMovementType()
	{
		if(movementType == randomMovement)
		{
			moveRandomly();
		}
		else if(movementType == methodicalMovement)
		{
			moveMethodically();
		}		
	}
	
	private void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			frameIndex++;
			
			if(frameIndex == totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
	}
	
	private void manageVulnerableAnimationTiming()
	{
		elapsedFrameTimeInSecondsVulnerable += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsVulnerable >= targetTimePerFrameInSecondsVulnerable)
		{
			elapsedFrameTimeInSecondsVulnerable = 0;
			frameIndexVulnerable++;
			
			if(frameIndexVulnerable == totalNumberOfFramesVulnerable)
			{
				frameIndexVulnerable = 0;
			}
		}
	}
	
	private void manageFlashingAnimationTiming()
	{
		elapsedFrameTimeInSecondsFlashing += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSecondsFlashing >= targetTimePerFrameInSecondsFlashing)
		{
			elapsedFrameTimeInSecondsFlashing = 0;
			frameIndexFlashing++;
			
			if(frameIndexFlashing == totalNumberOfFramesFlashing)
			{
				frameIndexFlashing = 0;
			}
		}
	}

	
	private void moveRandomly()
	{
		if(isCoolingDown == true)
		{
			coolDownTimeInSeconds += gp.secondsPerTick;
			
			if(coolDownTimeInSeconds >= coolDownTargetTimeInSeconds)
			{
				isCoolingDown = false;
				coolDownTimeInSeconds = 0;
			}
		}
		else if(isCoolingDown == false)
		{
			if(gp.pathFinder.pacmanIsClose(deltaX, deltaY) && !isVulnerable && !isInSpawnBox(this))
			{
				movementType = methodicalMovement;
			}
		}
		
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
		
		nextDir = randomGen.nextInt(4);
	}

	private void moveMethodically()
	{
		if(isVulnerable)
		{
			timeMovingMethodicallyInSeconds = 0;
			findDir1Blocked = false;
			isFindingPath = false;
			isCoolingDown = false;
			movementType = randomMovement;
			return;
		}
		
		if(!isFindingPath)
		{
			pacmanZone = gp.pathFinder.updatePacmanZone(deltaX, deltaY);
			
			if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][0]))
			{
				currentDir = gp.pathFinder.pathFinderArray[pacmanZone][0];
				
				move(this, gp.pathFinder.pathFinderArray[pacmanZone][0]);
			}
			else if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][1]))
			{
				currentDir = gp.pathFinder.pathFinderArray[pacmanZone][1];
				
				move(this, gp.pathFinder.pathFinderArray[pacmanZone][1]);
			}
			else
			{
				isFindingPath = true;
			}
		}
		else if(isFindingPath)
		{
			if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][0]))
			{
				findDir1Blocked = false;
				isFindingPath = false;
			}
			else
			{
				if(findDir1Blocked == false)
				{
					if(canMove(this, gp.pathFinder.pathFinderArray[pacmanZone][2]))
					{
						currentDir = gp.pathFinder.pathFinderArray[pacmanZone][2];
						
						move(this, gp.pathFinder.pathFinderArray[pacmanZone][2]);
					}
					else
					{
						findDir1Blocked = true;
					}
				}
				else if(findDir1Blocked == true)
				{
					currentDir = gp.pathFinder.pathFinderArray[pacmanZone][3];
					
					move(this, gp.pathFinder.pathFinderArray[pacmanZone][3]);
				}
			}
		}
		
		timeMovingMethodicallyInSeconds += gp.secondsPerTick;	
		
		if(timeMovingMethodicallyInSeconds >= targetTimeMovingMethodicallyInSeconds) 				
		{			
			timeMovingMethodicallyInSeconds = 0;				
			isCoolingDown = true;			
			movementType = randomMovement;	
		}
	}
	
	public static boolean isInSpawnBox(Ghost ghost)
	{
		return ((ghost.x < 368 && ghost.x > 272) && (ghost.y < 336 && ghost.y > 256)) ? true : false;
	}

	public void render(Graphics g)
	{
		if(isVulnerable == false)
		{
			g.drawImage(Animation.hostileGhostSprites[ghostID][currentDir][frameIndex], x, y, width, height, null);
		}
		else if(isVulnerable == true)
		{
			if(isFlashing)
			{
				g.drawImage(Animation.flashingGhostSprites[frameIndexFlashing], x, y, width, height, null);
			}
			else if(!isFlashing)
			{
				g.drawImage(Animation.vulnerableGhostSprites[frameIndexVulnerable], x, y, width, height, null);
			}
		}
	}
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
}