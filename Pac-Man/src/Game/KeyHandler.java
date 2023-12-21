package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	Game game;
	
	public KeyHandler(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{

	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if(game.gameStatus == game.play)
		{
			playState(code);
		}
		else if(game.gameStatus == game.init)
		{
			initState(code);
		}
		else if(game.gameStatus == game.win)
		{
			winState(code);
		}
		else if(game.gameStatus == game.lose)
		{
			loseState(code);
		}
	}

	public void playState(int code)
	{
		switch(code)
		{
			case KeyEvent.VK_D:	// fall through
			case KeyEvent.VK_RIGHT: Pacman.nextDir = Character.right; break;
			case KeyEvent.VK_A:	// fall through
			case KeyEvent.VK_LEFT: Pacman.nextDir = Character.left; break;
			case KeyEvent.VK_W:	// fall through
			case KeyEvent.VK_UP: Pacman.nextDir = Character.upwards; break;
			case KeyEvent.VK_S:	// fall through
			case KeyEvent.VK_DOWN: Pacman.nextDir = Character.downwards; break;
		}
	}
	
	public void initState(int code)
	{
		if(code == KeyEvent.VK_W)
		{
			game.commandNum--;
			
			if(game.commandNum < 0)
			{
				game.commandNum = 4;
			}
		}
		
		if(code == KeyEvent.VK_S)
		{
			game.commandNum++;
			
			if(game.commandNum > 4)
			{
				game.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER)
		{
			if(game.commandNum == 0)
			{
				game.enter = true;
			}
			if(game.commandNum == 1)
			{
				// LEADERBOARD
			}
			if(game.commandNum == 2)
			{
				// SETTINGS
			}
			if(game.commandNum == 3)
			{
				// AUTHOR'S NOTE
			}
			if(game.commandNum == 4)
			{
				System.exit(0);
			}
		}
	}
	
	public void loseState(int code)
	{
		if(code == KeyEvent.VK_SPACE)
		{
			game.space = true;
		}
	}
	
	public void winState(int code)
	{
		if(code == KeyEvent.VK_ENTER)
		{
			game.enter = true;					
		}
	}
}
