package Game;

import java.awt.Graphics;

public class AlivePacman extends Pacman
{	
	private static final long serialVersionUID = 1L;

	private int totalNumberOfFrames = Animation.alivePacmanSprites[currentDir].length;
			
	public AlivePacman()
	{
		spawnPacman(pacmanSpawnXCoordinate, pacmanSpawnYCoordinate);
		nextDir = stopped;
		currentDir = right;
	}
	
	public AlivePacman(int spawnType, int nD)
	{
		switch(spawnType)
		{
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
	
	private void spawnPacman(int xCoordinate, int yCoordinate)
	{
		setBounds(xCoordinate, yCoordinate, Level.objectWidth, Level.objectHeight);
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		move(this, nextDir);
		portalEvents(this);
		manageAnimationTiming();
		foodCollision();
		ghostCollision();
	}

	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(frameIndex >= totalNumberOfFrames)
			{
				frameIndex = 0;
			}
		}
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
	
	
	private void ghostCollision()
	{
		if(Ghost.blinky.intersects(this))
		{
			if(Ghost.blinky instanceof VulnerableGhost)
			{
				Ghost.blinky = new HostileGhost(Ghost.blinky, 320, 320);
				eatGhost();
			}
			else 
			{
				die();
			}
		}
		else if(Ghost.inky.intersects(this))
		{
			if(Ghost.inky instanceof VulnerableGhost)
			{
				Ghost.inky = new HostileGhost(Ghost.inky, 320, 320);
				eatGhost();
			}
			else 
			{
				die();
			}
		}
		else if(Ghost.pinky.intersects(this))
		{
			if(Ghost.pinky instanceof VulnerableGhost)
			{
				Ghost.pinky = new HostileGhost(Ghost.pinky, 320, 320);
				eatGhost();
			}
			else 
			{
				die();
			}
		}
		else if(Ghost.clyde.intersects(this))
		{
			if(Ghost.clyde instanceof VulnerableGhost)
			{
				Ghost.clyde = new HostileGhost(Ghost.clyde, 320, 320);
				eatGhost();
			}
			else 
			{
				die();
			}
		}
	}
	
	public boolean pacmanIntersectedGhost()
	{
		if(Ghost.blinky.intersects(this))
		{
			intersectedGhost = Ghost.blinky;
			return true;
		}
		else if(Ghost.inky.intersects(this))
		{
			intersectedGhost = Ghost.inky;
			return true;
		}
		else if(Ghost.pinky.intersects(this))
		{
			intersectedGhost = Ghost.pinky;
			return true;
		}
		else if(Ghost.clyde.intersects(this))
		{
			intersectedGhost = Ghost.clyde;
			return true;
		}
		
		return false;
	}
	
	private void eatGhost()
	{
		Sounds.playSoundEffect(Sounds.ghostEatenSoundPath);
				
		Ghost.numberOfEatenGhosts++;
		
		if(Ghost.numberOfEatenGhosts == 4)
		{
			Energizer.deactivate();
		}
		
		BonusScore.displayBonusScore(x, y);
		BonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
		
		pacman = new DeadPacman(x, y);
		Game.gameStatus = Game.lifeLost;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.alivePacmanSprites[currentDir][frameIndex], x, y, width, height, null);
	}
}
