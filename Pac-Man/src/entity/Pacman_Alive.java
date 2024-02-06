package entity;

import java.awt.Graphics;

import food.Food;
import food.Food_Energizer;
import food.Food_Pellet;
import main.GamePanel;

public class Pacman_Alive extends Pacman
{	
	GamePanel gp;
	
	public int intersectedGhost;
	
	private static final long serialVersionUID = 1L;
	
	public Pacman_Alive(GamePanel gp)
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
		if(gp.pacman.x == 0 && gp.pacman.y == 320 && gp.pacman.currentDir == "left")
		{
			gp.pacman.x = 640;
			gp.pacman.y = 320;
		}
		
		if(gp.pacman.x == 640 && gp.pacman.y == 320 && gp.pacman.currentDir == "right")
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
			
			if(frameIndex >= gp.animation.alivePacmanSprites[1].length)
			{
				frameIndex = 0;
			}
		}
	}
	
	private void foodCollision()
	{	
		for(int i = 0; i < gp.pelletList.size(); i++) 		
		{    
			if(this.intersects(gp.pelletList.get(i)))							
			{
				eat(gp.pelletList.get(i));
				break;
			}
		}
		
		for(int i = 0; i < gp.energizerList.size(); i++) 		
		{    
			if(this.intersects(gp.energizerList.get(i)))							
			{
				eat(gp.energizerList.get(i));
				break;
			}
		}
		
		if(gp.pelletList.size() == 0 && gp.energizerList.size() == 0)
		{
			gp.gameState = gp.winState;
		}
	}	

	public void eat(Food food)
	{	
		if(food instanceof Food_Energizer)
		{
			gp.allToVulnerable();
			gp.energizerList.remove(food);
		}
		else if(food instanceof Food_Pellet)
		{
			gp.playSE(0);
			gp.pelletList.remove(food);
		}

		gp.score += food.getFoodPoints();
	}
	
	private void ghostCollision()
	{
		if(!pacmanIntersectedGhost())
		{
			return;
		}
		
		if(gp.ghostArray[intersectedGhost] instanceof Ghost_Vulnerable
				|| gp.ghostArray[intersectedGhost] instanceof Ghost_Flashing)
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
		gp.playSE(3);
		
		gp.ghostArray[intersectedGhost] = new Ghost_Hostile(gp, intersectedGhost);
		gp.ghostArray[intersectedGhost].x = 320;
		gp.ghostArray[intersectedGhost].y = 320;
		gp.ghostArray[intersectedGhost].movementType = "random";
				
		gp.numberOfEatenGhosts++;
		
		gp.bonusScore.displayBonusScore(x, y);
		gp.bonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		gp.playSE(2);
		gp.numberOfLives--;
		gp.bonusScore.isBeingDisplayed = false;
		gp.pacman = new Pacman_Dead(gp);
		gp.gameState = gp.lifeLostState;
	}
	
	public void render(Graphics g)
	{
		switch(currentDir)
		{
			case "right":
				g.drawImage(gp.animation.alivePacmanSprites[0][frameIndex], x, y, width, height, null);
				break;
			case "left":
				g.drawImage(gp.animation.alivePacmanSprites[1][frameIndex], x, y, width, height, null);
				break;
			case "up":
				g.drawImage(gp.animation.alivePacmanSprites[2][frameIndex], x, y, width, height, null);
				break;
			case "down":
				g.drawImage(gp.animation.alivePacmanSprites[3][frameIndex], x, y, width, height, null);
				break;
		}
	}
}
