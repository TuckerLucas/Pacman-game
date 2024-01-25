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
		
		if(gp.gameState == gp.titleState)
		{
			titleState(code, 3);
		}
		else if(gp.gameState == gp.settingsState)
		{
			settingsState(code, 2);
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
	}
	
	public void titleState(int code, int nMenuOptions)
	{
		enableScrolling(code, nMenuOptions);
		
		if(code == KeyEvent.VK_ENTER)
		{
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.resetGame(); gp.gameState = gp.playState; break;
				case 1: gp.gameState = gp.settingsState; break;
				case 2: System.exit(0); break;
			}
			
			gp.ui.menuOptionIndex = 0;
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
				case 1: gp.gameState = gp.titleState; break;
			}
			
			gp.ui.menuOptionIndex = 0;
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
			switch(gp.ui.menuOptionIndex)
			{
				case 0: gp.score = 0; gp.gameState = gp.titleState; break;
				
				case 1: 
					
					gp.resetGame();
					
					if(gp.score >= gp.highscore)
					{
		        		gp.highscore = gp.score;
					}
					
					gp.blinkTime++;
					
					if(gp.blinkTime == gp.targetFrames)
					{
						gp.blinkTime = 0;
						gp.blinkText(); 
					}
					
					gp.gameState = gp.playState; 
					
					break;
					
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
				case 0: 
					
					gp.resetLevel(); 	
					
					gp.blinkTime++;
					
					if(gp.blinkTime == gp.targetFrames)
					{
						gp.blinkTime = 0;
						gp.blinkText();
					}
					gp.gameState = gp.playState; 
					
					break;
					
				case 1: gp.score = 0; gp.gameState = gp.titleState; break;
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
