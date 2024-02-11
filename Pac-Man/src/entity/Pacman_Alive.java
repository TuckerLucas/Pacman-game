package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman_Alive extends Pacman
{	
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Pacman_Alive(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
		
		totalNumberOfFrames = gp.animation.alivePacmanSprites[0].length;
		
		solidArea.x = 15;
		solidArea.y = 15;
		solidArea.width = 1;
		solidArea.height = 1;
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		move(this, nextDir);
		portalCross();
		gp.animation.manageAnimationTiming(this);
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
	
	private void foodCollision()
	{	
		for(int i = 0; i < gp.pelletList.size(); i++) 		
		{    
			if(this.intersects(gp.pelletList.get(i)))							
			{
				gp.eHandler.eat(gp.pelletList.get(i));
				return;
			}
		}
		
		for(int i = 0; i < gp.energizerList.size(); i++) 		
		{    
			if(this.intersects(gp.energizerList.get(i)))							
			{
				gp.eHandler.eat(gp.energizerList.get(i));
				return;
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
			gp.pacman.solidArea.x = gp.pacman.x + 15;
			gp.pacman.solidArea.y = gp.pacman.y + 15;
			
			if(gp.ghostArray[i].intersects(gp.pacman.solidArea))
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
