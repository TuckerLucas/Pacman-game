package Game;

import java.awt.Graphics;

public class Pacman extends Character
{
	private static final long serialVersionUID = 1L;
	
	public static int numberOfLives = 3;
	
	private int intersectedGhost;
	
	public static int pacmanSpawnXCoordinate = 320;
	public static int pacmanSpawnYCoordinate = 512;
	
	public static int currentDir;
	public static int nextDir;
	
	public static int portalCrossingStatus = notCrossingPortal;
	
	public static double elapsedFrameTimeInSeconds = 0.0;	
	public static double targetTimePerFrameInSeconds = 0.1;
	
	public static int frameIndex = 0;
	
	public static Pacman pacman;
	
	public Pacman(int spawnType, int nD)
	{
		switch(spawnType)
		{
			case notCrossingPortal:
				
				spawnPacman(pacmanSpawnXCoordinate, pacmanSpawnYCoordinate);
				nextDir = stopped;
				currentDir = right;
				
				break;
				
			case crossingPortalFromLeftSide:
				
				spawnPacman(portalRightSideCrossingPointXCoordinate, portalYCoordinate);
				currentDir = left;
				nextDir = nD;
				
				break;
				
			case crossingPortalFromRightSide:
				
				spawnPacman(portalLeftSideCrossingPointXCoordinate, portalYCoordinate);
				currentDir = right;
				nextDir = nD;
				
				break;
		}
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		if(portalCrossingStatus == notCrossingPortal)
		{
			move(this, nextDir);
		}
		
		portalEvents(this);
		foodCollision();
		ghostCollision();
		manageAnimationTiming();
	}
	
	private void spawnPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
	}

	private void foodCollision()
	{	
		for(int i = 0; i < Food.foodList.size(); i++) 		
		{    
			if(this.intersects(Food.foodList.get(i)))							
			{
				Food.eat(Food.foodList.get(i));
				break;
			}
		}
	}	

	public boolean pacmanIntersectedGhost()
	{
		for(int i = 0; i < Ghost.ghostArray.length; i++)
		{
			if(Ghost.ghostArray[i].intersects(this))
			{	
				intersectedGhost = i;
				
				return true;
			}
		}
		return false;
	}

	private void eatGhost()
	{
		Sounds.playSoundEffect(Sounds.ghostEatenSoundPath);
		
		Ghost.ghostArray[intersectedGhost] = new Ghost(intersectedGhost, Ghost.randomMovement, notCrossingPortal, false);
		
		Ghost.numberOfEatenGhosts++;
		
		if(Ghost.numberOfEatenGhosts == Ghost.ghostArray.length)
		{
			Energizer.deactivate();
		}
		
		BonusScore.displayBonusScore(x, y);
		BonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		if(Game.gameStatus == Game.play)
		{
			numberOfLives--;
			Game.gameStatus = Game.lifeLost;
			Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
		}
	}
	
	private void ghostCollision()
	{
		if(!pacmanIntersectedGhost())
		{
			return;
		}
		
		if(Ghost.ghostArray[intersectedGhost].isVulnerable)
		{
			eatGhost();
		}
		else 
		{
			die();
		}
	}
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(Game.gameStatus == Game.play)
			{
				if(frameIndex >= Texture.pacmanLook[currentDir].length)
				{
					frameIndex = 0;
				}
			}
			else if(Game.gameStatus == Game.lifeLost)
			{
				if(frameIndex >= Texture.pacmanDie.length)
				{
					frameIndex = 0;
					Game.loadGameElements();
					
					if(numberOfLives == 0)
					{
						Game.gameStatus = Game.lose;
					}
					else
					{	
						Game.gameStatus = Game.play;
					}
				}
			}
		}
	}
	
	public void render(Graphics g)
	{
		if(Game.gameStatus == Game.lifeLost)
		{	
			g.drawImage(Texture.pacmanDie[frameIndex], x, y, width, height, null);
		}
		else if(Game.gameStatus == Game.play)
		{
			g.drawImage(Texture.pacmanLook[currentDir][frameIndex], x, y, width, height, null);
		}
	}
	
	
	public int getCurrentDirection()
	{
		return currentDir;
	}
	
	public void setCurrentDirection(int dir)
	{
		currentDir = dir;
	}
	
	int getPortalCrossingStatus() 
	{
		return -1;
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
		return -1;
	}
	
	int getMovementType() 
	{
		return -1;
	}

	boolean getVulnerabilityStatus() 
	{
		return false;
	}
}