package character;

import java.awt.Graphics;

import food.Energizer;
import food.Food;
import food.Pellet;
import Game.Animation;
import Game.Level;
import main.Game;
import main.Sounds;

public class AlivePacman extends Pacman
{	
	Game game;
	
	private static final long serialVersionUID = 1L;

	private int totalNumberOfFrames = Animation.alivePacmanSprites[currentDir].length;

	public AlivePacman(int cD, int nD, int x, int y, Game game)
	{
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		currentDir = cD;
		nextDir = nD;
		setBounds(x, y, Level.objectWidth, Level.objectHeight);
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
		for(int i = 0; i < game.foodList.size(); i++) 		
		{    
			if(this.intersects(game.foodList.get(i)))							
			{
				eat(game.foodList.get(i));
				break;
			}
		}
	}	

	public void eat(Food food)
	{	
		if(food instanceof Energizer)
		{
			Energizer.activate();
		}
		else if(food instanceof Pellet)
		{
			Pellet.getEaten();
		}

		game.score += food.getFoodPoints();
		game.foodList.remove(food);
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
		
		Ghost.ghostArray[intersectedGhost] = new Ghost(intersectedGhost, Ghost.randomMovement, notCrossingPortal, false, game);
				
		Ghost.numberOfEatenGhosts++;
		
		if(Ghost.numberOfEatenGhosts == Ghost.ghostArray.length)
		{
			Energizer.deactivate();
		}
		
		game.bonusScore.displayBonusScore(x, y);
		game.bonusScore.sumBonusScoreToGameScore();
	}
	
	private void die()
	{
		Sounds.playSoundEffect(Sounds.pacmanDeathSoundPath);
		
		pacman = new DeadPacman(x, y, game);
		game.gameStatus = game.lifeLost;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Animation.alivePacmanSprites[currentDir][frameIndex], x, y, width, height, null);
	}
}
