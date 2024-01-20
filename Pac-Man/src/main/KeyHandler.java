package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import character.Character;
import character.Pacman;

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
		
		if(gp.gameStatus == gp.init)
		{
			initState(code, 3);
		}
		else if(gp.gameStatus == gp.settings)
		{
			settingsState(code, 2);
		}
		else if(gp.gameStatus == gp.play)
		{
			playState(code);
		}
		else if(gp.gameStatus == gp.win)
		{
			winState(code, 3);
		}
		else if(gp.gameStatus == gp.lose)
		{
			loseState(code, 3);
		}
	}
	
	public void initState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.menuOptionIndex)
			{
				case 0: gp.loadGameElements(); gp.gameStatus = gp.play; break;
				case 1: gp.gameStatus = gp.settings; break;
				case 2: System.exit(0); break;
			}
			
			gp.menuOptionIndex = 0;
		}
	}
	
	public void settingsState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.menuOptionIndex)
			{
				case 0: break;
				case 1: gp.gameStatus = gp.init; break;
			}
			
			gp.menuOptionIndex = 0;
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
	
	public void loseState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.menuOptionIndex)
			{
				case 0: gp.score = 0; gp.gameStatus = gp.init; break;
				case 1: gp.score = 0; gp.loadGameElements(); gp.gameStatus = gp.play; break;
				case 2: System.exit(0); break;
			}
			
			gp.menuOptionIndex = 0;
		}
	}
	
	public void winState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.menuOptionIndex)
			{
				case 0: gp.loadGameElements(); gp.gameStatus = gp.play; break;
				case 1: gp.score = 0; gp.gameStatus = gp.init; break;
				case 2: System.exit(0); break;
			}
			
			gp.menuOptionIndex = 0;
		}
	}
	
	private void enableScrolling(int code, int nMenuOptions)
	{
		int maxIndex = nMenuOptions - 1;
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
		{
			gp.menuOptionIndex--;
			
			if(gp.menuOptionIndex < 0)
			{
				gp.menuOptionIndex = maxIndex;
			}
		}
		else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
		{
			gp.menuOptionIndex++;
			
			if(gp.menuOptionIndex > maxIndex)
			{
				gp.menuOptionIndex = 0;
			}
		}
	}
}
