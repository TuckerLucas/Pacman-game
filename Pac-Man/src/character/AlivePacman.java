package character;

import java.awt.Graphics;

import food.Energizer;
import food.Food;
import food.Pellet;
import main.GamePanel;
import main.Sounds;

public class AlivePacman extends Pacman
{	
	GamePanel gp;
	
	public int intersectedGhost;
	
	private static final long serialVersionUID = 1L;
	
	public AlivePacman(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
		setBounds(x, y, gp.tileSize, gp.tileSize);
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		move(this, nextDir);
		portalCross();
		manageAnimationTiming();
		foodCollision();
		ghostCollision();
	}
	
	public void portalCross()
	{
		if(gp.pacman.x == 0 && gp.pacman.y == 320 && gp.pacman.currentDir == left)
		{
			gp.pacman.x = 640;
			gp.pacman.y = 320;
		}
		
		if(gp.pacman.x == 640 && gp.pacman.y == 320 && gp.pacman.currentDir == right)
		{
			gp.pacman.x = 0;
			gp.pacman.y = 320;
		}
	}

	public void manageAnimationTiming()
	{
		elapsedFrameTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedFrameTimeInSeconds >= targetTimePerFrameInSeconds)
		{
			elapsedFrameTimeInSeconds = 0;
			
			frameIndex++;
			
			if(frameIndex >= gp.animation.alivePacmanSprites[currentDir].length)
			{
				frameIndex = 0;
			}
		}
	}
	
	private void foodCollision()
	{	
		for(int i = 0; i < gp.foodList.size(); i++) 		
		{    
			if(this.intersects(gp.foodList.get(i)))							
			{
				eat(gp.foodList.get(i));
				break;
			}
		}
		
		if(gp.foodList.size() == 0)
		{
			gp.gameState = gp.winState;
		}
	}	

	public void eat(Food food)
	{	
		if(food instanceof Energizer)
		{
			gp.allToVulnerable();
		}
		else if(food instanceof Pellet)
		{
			Pellet.getEaten();
		}

		gp.score += food.getFoodPoints();
		gp.foodList.remove(food);
	}
	
	private void ghostCollision()
	{
		if(!pacmanIntersectedGhost())
		{
			return;
		}
		
		if(gp.ghostArray[intersectedGhost] instanceof VulnerableGhost
				|| gp.ghostArray[intersectedGhost] instanceof FlashingGhost)
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
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i].intersects(this))
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
		
		gp.ghostArray[intersectedGhost] = new HostileGhost(gp, intersectedGhost);
		gp.ghostArray[intersectedGhost].x = 320;
		gp.ghostArray[intersectedGhost].y = 320;
		gp.ghostArray[intersectedGhost].movementType = Ghost.randomMovement;
				
		gp.numberOfEatenGhosts++;
		
		gp.bonusScore.displayBonusScore(x, y);
		gp.bonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
		gp.numberOfLives--;
		gp.bonusScore.isBeingDisplayed = false;
		gp.pacman = new DeadPacman(gp);
		gp.gameState = gp.lifeLostState;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(gp.animation.alivePacmanSprites[currentDir][frameIndex], x, y, width, height, null);
	}
}
