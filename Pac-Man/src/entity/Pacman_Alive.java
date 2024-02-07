package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman_Alive extends Pacman
{	
	GamePanel gp;
	
	//public int intersectedGhost;
	
	private static final long serialVersionUID = 1L;
	
	public Pacman_Alive(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
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
				gp.eHandler.eat(gp.pelletList.get(i));
				break;
			}
		}
		
		for(int i = 0; i < gp.energizerList.size(); i++) 		
		{    
			if(this.intersects(gp.energizerList.get(i)))							
			{
				gp.eHandler.eat(gp.energizerList.get(i));
				break;
			}
		}
		
		if(gp.pelletList.size() == 0 && gp.energizerList.size() == 0)
		{
			gp.gameState = gp.winState;
		}
	}	
	
	private void ghostCollision()
	{
		for(int i = 0; i < gp.ghostArray.length; i++)
		{
			if(gp.ghostArray[i].intersects(this))
			{	
				if(gp.ghostArray[i] instanceof Ghost_Vulnerable
						|| gp.ghostArray[i] instanceof Ghost_Flashing)
				{
					gp.eHandler.eatGhost(i);
				}
				else 
				{
					gp.eHandler.die(this);
				}
			}
		}
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
