package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	GamePanel gp;
	
	public boolean enterPressed, spacePressed;
	
	public KeyHandler(GamePanel gp)
	{
		this.gp = gp;
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
		
		if(gp.gameState == gp.titleState)
		{
			titleState(code, 4);
		}
		else if(gp.gameState == gp.leaderboardState)
		{
			leaderboardState(code, 1);
		}
		else if(gp.gameState == gp.settingsState)
		{
			settingsState(code, 3);
		}
		else if(gp.gameState == gp.playState)
		{
			playState(code);
		}
		else if(gp.gameState == gp.winState)
		{
			winState(code, 3);
		}
		else if(gp.gameState == gp.gameOverState)
		{
			loseState(code, 3);
		}
		else if(gp.gameState == gp.usernameState)
		{
			usernameState(e);
		}
	}
	
	public void titleState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.resetGame(); gp.stopSE(); gp.playMusic(5); gp.gameState = gp.usernameState; break;
				case 1: gp.gameState = gp.leaderboardState; break;
				case 2: gp.gameState = gp.settingsState; break;
				case 3: System.exit(0); break;
			}
			
			gp.ui.menuOptionIndex = 0;
		}
	}
	
	public void leaderboardState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.gameState = gp.titleState; break;
			}
		}
	}
	
	public void settingsState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: break;
				case 1: break;
				case 2: gp.ui.menuOptionIndex = 0; gp.gameState = gp.titleState; break;
			}
		}
		
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_MINUS)
		{
			if(gp.ui.menuOptionIndex == 0 && gp.music.volumeScale > 0)
			{
				gp.music.volumeScale--;
			}
			
			if(gp.ui.menuOptionIndex == 1 && gp.se.volumeScale > 0)
			{
				gp.se.volumeScale--;
			}
		}
		
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_PLUS)
		{
			if(gp.ui.menuOptionIndex == 0 && gp.music.volumeScale < 5)
			{
				gp.music.volumeScale++;
			}
			
			if(gp.ui.menuOptionIndex == 1 && gp.se.volumeScale < 5)
			{
				gp.se.volumeScale++;
			}
		}
	}
	
	public void usernameState(KeyEvent e)
	{
		char character;
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(gp.username.length() != 0)
			{
				gp.gameState = gp.playState;
			}
			
			return;
		}
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			gp.gameState = gp.titleState;
			return;
		}
		else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			if(gp.username != null && gp.username.length() > 0)
			{
				gp.username = gp.username.substring(0, gp.username.length() - 1);
			}
		}
		else
		{
			character = e.getKeyChar();
			
			if(Character.isDigit(character) || Character.isAlphabetic(character))
			{
				gp.username += character;
			}
		}
	}
	
	public void playState(int code)
	{
		switch(code)
		{
			case KeyEvent.VK_D:	// fall through
			case KeyEvent.VK_RIGHT: gp.pacman.nextDir = "right"; break;
			case KeyEvent.VK_A:	// fall through
			case KeyEvent.VK_LEFT: gp.pacman.nextDir = "left"; break;
			case KeyEvent.VK_W:	// fall through
			case KeyEvent.VK_UP: gp.pacman.nextDir = "up"; break;
			case KeyEvent.VK_S:	// fall through
			case KeyEvent.VK_DOWN: gp.pacman.nextDir = "down"; break;
		}
	}
	
	public void loseState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.gameState = gp.titleState; break;
				case 1: gp.resetGame(); gp.gameState = gp.playState; break;
				case 2: System.exit(0); break;
			}
			
			gp.ui.menuOptionIndex = 0;
		}
	}
	
	public void winState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.resetLevel(); gp.gameState = gp.playState; break;
				case 1: gp.gameState = gp.titleState; break;
				case 2: System.exit(0); break;
			}
			
			gp.ui.menuOptionIndex = 0;
		}
	}
	
	private void enableScrolling(int code, int nMenuOptions)
	{
		int maxIndex = nMenuOptions - 1;
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
		{
			gp.ui.menuOptionIndex--;
			
			if(gp.ui.menuOptionIndex < 0)
			{
				gp.ui.menuOptionIndex = maxIndex;
			}
		}
		else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
		{
			gp.ui.menuOptionIndex++;
			
			if(gp.ui.menuOptionIndex > maxIndex)
			{
				gp.ui.menuOptionIndex = 0;
			}
		}
	}
}
