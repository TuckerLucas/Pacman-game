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
		if(!pacmanIntersectedGhost())
		{
			return;
		}
		
		if(Ghost.ghostArray[intersectedGhost] instanceof VulnerableGhost)
		{
			eatGhost();
		}
		else 
		{
			die();
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
		
		Ghost.ghostArray[intersectedGhost] = new HostileGhost(Ghost.ghostArray[intersectedGhost], Ghost.spawnBoxX, Ghost.spawnBoxY);
				
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
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
		
		pacman = new DeadPacman(x, y);
		Game.gameStatus = Game.lifeLost;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.alivePacmanSprites[currentDir][frameIndex], x, y, width, height, null);
	}
}
