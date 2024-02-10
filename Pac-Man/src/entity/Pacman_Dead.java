package entity;

import java.awt.Graphics;

import main.GamePanel;

public class Pacman_Dead extends Pacman
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public Pacman_Dead(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
		targetTimePerFrameInSeconds = 0.1;
		totalNumberOfFrames = gp.animation.deadPacmanSprites.length;
	}
	
	public void tick()
	{
		gp.animation.manageAnimationTiming(this);
	}
	
	public void render(Graphics g)
	{	
		g.drawImage(gp.animation.deadPacmanSprites[frameIndex], x, y, width, height, null);
	}
}