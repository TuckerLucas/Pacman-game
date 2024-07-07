package entity;

//import java.awt.Color;
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
	}
	
	public void tick()
	{	
		if(canMove(this, nextDir))
		{
			currentDir = nextDir;
		}
		
		manageMovement(this, nextDir);
		portalCross();
		manageAnimationTiming(this);
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
		
//		g.setColor(Color.red);
//		g.fillRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
	}
}