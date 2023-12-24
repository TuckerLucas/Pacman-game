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
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
		{
			game.commandNumInit--;
			
			if(game.commandNumInit < 0)
			{
				game.commandNumInit = 1;
			}
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
		{
			game.commandNumInit++;
			
			if(game.commandNumInit > 1)
			{
				game.commandNumInit = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER)
		{
			if(game.commandNumInit == 0)
			{
				game.enter = true;
			}
			/*
			if(game.commandNumInit == 1)
			{
				// LEADERBOARD
			}
			if(game.commandNumInit == 2)
			{
				// SETTINGS
			}
			if(game.commandNumInit == 3)
			{
				// AUTHOR'S NOTE
			}*/
			if(game.commandNumInit == 1)
			{
				System.exit(0);
			}
		}
	}
	
	public void loseState(int code)
	{
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
		{
			game.commandNumLose--;
			
			if(game.commandNumLose < 0)
			{
				game.commandNumLose = 2;
			}
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
		{
			game.commandNumLose++;
			
			if(game.commandNumLose > 2)
			{
				game.commandNumLose = 0;
			}
		}
		
		if(code == KeyEvent.VK_SPACE)
		{
			game.space = true;
		}
		if(code == KeyEvent.VK_ENTER)
		{
			if(game.commandNumLose == 0)
			{
				// HOME PANEL
				game.space = true;
			}
			if(game.commandNumLose == 1)
			{
				game.enter = true;
			}
			if(game.commandNumLose == 2)
			{
				System.exit(0);
			}						
		}
	}
	
	public void winState(int code)
	{
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
		{
			game.commandNumWin--;
			
			if(game.commandNumWin < 0)
			{
				game.commandNumWin = 2;
			}
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
		{
			game.commandNumWin++;
			
			if(game.commandNumWin > 2)
			{
				game.commandNumWin = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER)
		{
			if(game.commandNumWin == 0)
			{
				game.enter = true;
			}
			if(game.commandNumWin == 1)
			{
				// HOME PANEL
				game.space = true;
			}
			if(game.commandNumWin == 2)
			{
				System.exit(0);
			}			
		}
	}
}
