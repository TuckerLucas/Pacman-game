package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class UI 
{
	GamePanel gp;
	Graphics g;
	
	Font maruMonica;
	public int menuOptionIndex = 0;
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		
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
		
		if(gp.gameState == gp.titleState)
		{
			drawInitScreen(g);
		}
		if(gp.gameState == gp.settingsState)
		{
			drawSettingsScreen(g);
		}
		if(gp.gameState == gp.winState)
		{
			drawWinScreen(g);
		}
		if(gp.gameState == gp.gameOverState)
		{
			drawLoseScreen(g);
		}
		if(gp.gameState == gp.lifeLostState)
		{
			drawPacmanDying(g);
			displayGameStats(g);
		}
		if(gp.gameState == gp.playState)
		{
			gp.level.render(g);
			displayGameStats(g);
		}
	}
	
	public void drawInitScreen(Graphics g)
	{	
		// BACKGROUND COLOR
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE NAME
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
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "SETTINGS";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
	}

	public void drawSettingsScreen(Graphics g)
	{		
		int textX;
		int textY;
		String text;
		
		// BACKGROUND COLOR
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		text = "SETTINGS";
		
		textX = getXForCenteredText(g, text);
		textY = gp.tileSize * 5;
		
		g.setColor(Color.gray);
		g.drawString(text, textX + 4, textY + 4);
		
		g.setColor(Color.black);
		g.drawString(text, textX + 3, textY + 3);
		
		g.setColor(Color.white);
		g.drawString(text, textX, textY);
		
		// OPTIONS
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		textX = gp.tileSize * 7;
		
		text = "MUSIC";
		textY += gp.tileSize*8;
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
		}
		
		text = "SOUNDS";
		textY += gp.tileSize * 1.2;
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 1)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
		}
		
		text = "BACK";
		textY += gp.tileSize * 1.2;
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 2)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
		}
		
		// VOLUME BARS
		textX += gp.tileSize * 4;
		textY = gp.tileSize * 13;
		int rectWidth = 140;
		int rectHeight = 28;
		int musicVolumeWidth = rectHeight * gp.music.volumeScale;
		
		g.drawRect(textX, textY - rectHeight, rectWidth, rectHeight); 
		g.fillRect(textX, textY - rectHeight, musicVolumeWidth, rectHeight);
		
		textY += gp.tileSize * 1.2;
		int seVolumeWidth = rectHeight * gp.se.volumeScale;
		
		g.drawRect(textX, textY - rectHeight, rectWidth, rectHeight); 
		g.fillRect(textX, textY - rectHeight, seVolumeWidth, rectHeight);
	}
	
	private void drawWinScreen(Graphics g)
	{		
		String text = "YOU WON!";
		
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		int x = getXForCenteredText(g, text);
		int y = 32*5;
		
		g.setColor(Color.white);
		g.drawString(text, x + 4, y + 4);
		
		g.setColor(Color.black);
		g.drawString(text, x + 3, y + 3);
		
		g.setColor(Color.green);
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + gp.score;
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
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "HOME";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 2)
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
		
		g.setColor(Color.white);
		g.drawString(text, x + 4, y + 4);
		
		g.setColor(Color.black);
		g.drawString(text, x + 3, y + 3);
		
		g.setColor(Color.red);
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + gp.score;
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
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "PLAY AGAIN";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	private void drawPacmanDying(Graphics g)
	{
		gp.pacman.render(g);
		
		if(gp.gameState != gp.gameOverState)
		{
			gp.level.render(g);
		}
	}
	
	private void displayGameStats(Graphics g)
	{	
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 35F));
		g.setColor(Color.white);
		
		int y = getYForCenteredText(g);
		
		g.drawString("SCORE : ", 20, y);	
		g.drawString(String.valueOf(gp.score), 120, y);
		
//		g.drawString("HIGHSCORE : ", 210, y);	
//		g.drawString(String.valueOf(Game.highscore), 365, y);
		
		g.drawString("LIVES:", 470, y);
		
		y = gp.screenHeight - (80/2) - (gp.tileSize/2) + 2; // 730

		for(int i = 0; i < gp.lives; i++)
		{
			g.drawImage(gp.animation.alivePacmanSprites[0][2], 555 + ((gp.tileSize + 5) * i), y, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public int getXForCenteredText(Graphics g, String text)
	{
		int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	
	public int getYForCenteredText(Graphics g)
	{
		int height = (int)g.getFontMetrics().getHeight();
		int y = gp.screenHeight - (80/2 - height/2);
		
		return y;
	}
}
