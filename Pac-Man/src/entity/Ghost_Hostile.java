package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Ghost_Hostile extends Ghost
{
	private static final long serialVersionUID = 1L;
	
	public Ghost_Hostile(GamePanel gp, int i) 
	{
		super(gp, i);
		
		frameIndex = 0;
		elapsedFrameTimeInSeconds = 0;		
		targetTimePerFrameInSeconds = 0.05;
		totalNumberOfFrames = gp.animation.hostileGhostSprites[0][0].length;
	}
	
	public void tick()
	{
		if(!isCrossingPortal(ghostID, this))
		{
			if(movementType == "random")
			{
				moveRandomly(this);
			}
			else if(movementType == "methodical")
			{
				moveMethodically(this);
			}
			else if(movementType == "sideToSide")
			{
				moveSideToSide(this);
			}
		}
		
		manageAnimationTiming(this);
	}
	
	public void render(Graphics g)
	{
		switch(currentDir)
		{
			case "right": 
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][0][frameIndex], x, y, width, height, null);
				break;
			case "left":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][1][frameIndex], x, y, width, height, null);
				break;
			case "up":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][2][frameIndex], x, y, width, height, null);
				break;
			case "down":
				g.drawImage(gp.animation.hostileGhostSprites[ghostID][3][frameIndex], x, y, width, height, null);
				break;
		}
		
		//g.setColor(Color.red);
		//g.fillRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
	}
}
