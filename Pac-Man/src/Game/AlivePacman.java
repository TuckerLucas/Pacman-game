package Game;

import java.awt.Graphics;

public class AlivePacman extends Pacman
{	
	private static final long serialVersionUID = 1L;

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
		setBounds(xCoordinate, yCoordinate, Texture.objectWidth, Texture.objectHeight);
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
		
		Ghost.ghostArray[intersectedGhost].isVulnerable = false;
				
		Ghost.ghostArray[intersectedGhost] = new Ghost(Ghost.ghostArray[intersectedGhost]);
		
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
		g.drawImage(Texture.pacmanLook[currentDir][frameIndex], x, y, width, height, null);
	}
}
