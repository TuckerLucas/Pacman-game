package Game;

import java.awt.Graphics;

public class AlivePacman extends Pacman
{	
	private static final long serialVersionUID = 1L;

	public AlivePacman(int spawnType, int nD)
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
	
	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += Game.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(frameIndex >= Texture.pacmanLook[currentDir].length)
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
		numberOfLives--;
		pacman = new DeadPacman(x, y);
		Game.gameStatus = Game.lifeLost;
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
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
	
	public void render(Graphics g)
	{
		g.drawImage(Texture.pacmanLook[currentDir][frameIndex], x, y, width, height, null);
	}
}
