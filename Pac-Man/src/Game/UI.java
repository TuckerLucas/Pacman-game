package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class UI 
{
	Game game;
	Graphics g;
	
	static Font maruMonica;
	
	public UI(Game game)
	{
		this.game = game;
		
		try 
		{
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT,  is);
		} 
		catch (FontFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g)
	{
		this.g = g;
		g.setFont(maruMonica);
		g.setColor(Color.white);
		
		if(game.gameStatus == game.init)
		{
			drawInitScreen(g);
		}
		if(game.gameStatus == game.win)
		{
			drawWinScreen(g);
		}
		if(game.gameStatus == game.lose)
		{
			drawLoseScreen(g);
		}
		if(game.gameStatus == game.lifeLost)
		{
			drawPacmanDying(g);
		}
		if(game.gameStatus == game.play)
		{
			Level.level.render(g);
		}
	}
	
	public void drawInitScreen(Graphics g)
	{	
		// BACKGROUND COLOR
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, game.screenWidth, game.screenHeight);
		
		// TITLE NAME
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		String text = "PACMAN";
		int x = getXForCenteredText(g, text);
		int y = 32*5;
		
		// SHADOW
		g.setColor(Color.white);
		g.drawString(text, x + 4, y + 4);
		
		g.setColor(Color.orange);
		g.drawString(text, x + 3, y + 3);
		
		// MAIN COLOR
		g.setColor(Color.yellow);
		g.drawString(text, x, y);
		
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		text = "PLAY";
		x = getXForCenteredText(g, text);
		y += 32*8;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		/*
		text = "LEADERBOARD";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndexInit == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "SETTINGS";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndexInit == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "AUTHOR'S NOTE";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndexInit == 3)
		{
			g.drawString(">",  x - 32,  y);
		}*/
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	public int getXForCenteredText(Graphics g, String text)
	{
		int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x = game.screenWidth/2 - length/2;
		
		return x;
	}

	private void drawWinScreen(Graphics g)
	{		
		String text = "YOU WON!";
		
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		int x = getXForCenteredText(g, text);
		int y = 32*5;
		
		if(game.showText)
		{
			g.setColor(Color.white);
			g.drawString(text, x + 4, y + 4);
			
			g.setColor(Color.black);
			g.drawString(text, x + 3, y + 3);
			
			g.setColor(Color.green);
			g.drawString(text, x, y);
		}
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + game.score;
		x = getXForCenteredText(g, text);
		y += 32*5;
		
		g.setColor(Color.gray);
		g.drawString(text, x+4, y+4);
		g.setColor(Color.black);
		g.drawString(text, x+3, y+3);
		g.setColor(Color.white);
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		text = "NEXT LEVEL";
		x = getXForCenteredText(g, text);
		y += 32*5;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "HOME";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	public void drawLoseScreen(Graphics g)
	{		
		String text = "YOU LOST!";
		
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		int x = getXForCenteredText(g, text);
		int y = 32*5;
		
		if(game.showText)
		{
			g.setColor(Color.white);
			g.drawString(text, x + 4, y + 4);
			
			g.setColor(Color.black);
			g.drawString(text, x + 3, y + 3);
			
			g.setColor(Color.red);
			g.drawString(text, x, y);
		}
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + game.score;
		x = getXForCenteredText(g, text);
		y += 32*5;
		
		g.setColor(Color.gray);
		g.drawString(text, x+4, y+4);
		g.setColor(Color.black);
		g.drawString(text, x+3, y+3);
		g.setColor(Color.white);
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		text = "HOME";
		x = getXForCenteredText(g, text);
		y += 32*5;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "PLAY AGAIN";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(game.menuOptionIndex == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	private void drawPacmanDying(Graphics g)
	{
		Pacman.pacman.render(g);
		
		if(game.gameStatus != game.lose)
		{
			Level.level.render(g);
		}
	}
}
