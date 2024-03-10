package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class UI 
{
	GamePanel gp;
	Graphics g;
	
	Font maruMonica;
	public int menuOptionIndex = 0;
	
	public boolean showUsernameCursor = false;
	
	public double elapsedTimeInSeconds = 0.0;
	public double targetTimeInSeconds = 0.5;
	
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
	
	public void tick()
	{
		elapsedTimeInSeconds += gp.secondsPerTick;
		
		if(elapsedTimeInSeconds >= targetTimeInSeconds)
		{
			if(showUsernameCursor)
			{
				showUsernameCursor = false;
			}
			else
			{
				showUsernameCursor = true;
			}
			
			elapsedTimeInSeconds = 0.0;
		}
	}
	
	public void render(Graphics g)
	{
		this.g = g;
		
		if(gp.gameState == gp.titleState)
		{
			drawTitleScreen(g);
		}
		if(gp.gameState == gp.leaderboardState)
		{
			drawLeaderboardScreen(g);
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
			drawGameStats(g);
		}
		if(gp.gameState == gp.usernameState)
		{
			drawUsernameScreen(g);
		}
		if(gp.gameState == gp.playState)
		{
			gp.level.render(g);
			drawGameStats(g);
		}
	}
	
	public void drawTitleScreen(Graphics g)
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
		g.setFont(g.getFont().deriveFont(Font.BOLD, 28F));
		
		text = "by Lucas Tucker";
		x = getXForCenteredText(g, text);
		y += gp.tileSize*2;
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		
		text = "PLAY";
		x = getXForCenteredText(g, text);
		y += 32*8;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "LEADERBOARD";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "SETTINGS";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(menuOptionIndex == 3)
		{
			g.drawString(">",  x - 32,  y);
		}
	}

	public void drawLeaderboardScreen(Graphics g)
	{		
		int textX, textY, tableX, tableY, tableW, tableRowH;
		String text;
		
		// BACKGROUND COLOR
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
				
		// TITLE
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		text = "LEADERBOARD";
		
		textX = getXForCenteredText(g, text);
		textY = gp.tileSize * 5;
		
		g.setColor(Color.gray);
		g.drawString(text, textX + 4, textY + 4);
		
		g.setColor(Color.black);
		g.drawString(text, textX + 3, textY + 3);
		
		g.setColor(Color.white);
		g.drawString(text, textX, textY);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
        
		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.white);
		
        tableX = gp.tileSize*3;
        tableY = gp.tileSize*7;
        tableW = gp.screenWidth-(gp.tileSize*6);
        tableRowH = gp.tileSize*2;
        
        // DRAW TABLE
        for(int i = 0; i < 6; i++)
        {
        	g2.drawRect(tableX, tableY+(i*gp.tileSize*2), tableW, tableRowH);
        	g2.drawLine(gp.screenWidth/2, tableY+(i*gp.tileSize*2), gp.screenWidth/2, tableY+((i+1)*gp.tileSize*2));
        }
        
        // LEADERBOARD HEADER
        text = "USERNAME";
        textX = getXForCenteredText(g, text, tableX, gp.screenWidth/2);
        textY = getYForCenteredText(g, tableY, tableY+tableRowH);
        g2.drawString(text, textX, textY);
        text = "POINTS";
        textX = getXForCenteredText(g, text, (gp.screenWidth/2), gp.screenWidth - (gp.tileSize*3));
        g2.drawString(text, textX, textY);
        
        // LEADERBOARD DATA
        for(int i = 0; i < 5; i++)
        {
        	textY = getYForCenteredText(g, (tableY+(gp.tileSize*2)) + (i*gp.tileSize*2), (tableY+(gp.tileSize*4)) + (i*gp.tileSize*2));
        	
        	// PODIUM COLORS
        	switch(i)
        	{
        		case 0: g2.setColor(new Color(255, 215, 0)); break;
        		case 1: g2.setColor(new Color(192, 192, 192)); break;
        		case 2: g2.setColor(new Color(205, 127, 50)); break;
        		default: g2.setColor(Color.white); break;
        	}
        	
        	if(gp.fHandler.leaderboardInfo[i][0] != null && gp.fHandler.leaderboardInfo[i][1] != null)
        	{
	        	text = gp.fHandler.leaderboardInfo[i][0];
	        	textX = getXForCenteredText(g, text, tableX, gp.screenWidth/2);
	
	        	g2.drawString(text, textX, textY);
	            
	            text = gp.fHandler.leaderboardInfo[i][1];
	            textX = getXForCenteredText(g, text, gp.screenWidth/2, gp.screenWidth - tableX);
	            
	            g2.drawString(text, textX, textY);
        	}
        }
        
        // BACK OPTION
        text = "BACK";
        textX = getXForCenteredText(g, text);
		textY = gp.screenHeight - (gp.tileSize*3);
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 0)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
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
		
		text = "SPEED";
		textY += gp.tileSize * 1.2;
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 2)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
		}
		
		text = "BACK";
		textX = getXForCenteredText(g, text);
		textY += gp.tileSize * 2.2;
		g.drawString(text, textX, textY);
		
		if(menuOptionIndex == 3)
		{
			g.drawString(">",  textX - gp.tileSize,  textY);
		}
		
		// VOLUME BARS
		textX = gp.tileSize * 11;
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
		
		// SPEED SETTING
		textY += gp.tileSize * 1.2;
		
		if(gp.speed == 2)
		{
			g.drawString("FAST", textX, textY);
		}
		else if(gp.speed == 1)
		{
			g.drawString("SLOW", textX, textY);
		}
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
		
		for(int i = 0; i < 5; i++)
		{
			if(gp.fHandler.leaderboardInfo[i][1] != null)
			{
				int temp = Integer.parseInt(gp.fHandler.leaderboardInfo[i][1]);
				
				if(gp.score == temp)
				{
					g.setFont(g.getFont().deriveFont(Font.BOLD, 25F));
					text = "You made it to #" + (i+1) + " on the leaderboard!";
					x = getXForCenteredText(g, text);
					g.drawString(text, x, y+50);
					break;
				}
			}
		}
		
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
	
	private void drawUsernameScreen(Graphics g)
	{
		String text;
		
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		text = "INSERT USERNAME";
		int x = getXForCenteredText(g, text); 
		int y = gp.tileSize*5;
		
		g.setColor(Color.gray);
		g.drawString(text, x + 4, y + 4);
		
		g.setColor(Color.black);
		g.drawString(text, x + 3, y + 3);
		
		g.setColor(Color.white);
		g.drawString(text, x, y);
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 40F));
		x = getXForCenteredText(g, gp.username);
		y = gp.screenHeight/2;
		g.drawString(gp.username, x, y);
		
		if(showUsernameCursor && gp.username.length() < 15)
		{
			g.drawString("_", (gp.screenWidth/2) + ((int)g.getFontMetrics().getStringBounds(gp.username, g).getWidth() / 2), y);
		}
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 36F));
		text = "PRESS [ENTER] TO START THE GAME";
		x = getXForCenteredText(g, text);
		y += gp.tileSize*7;
		g.drawString(text, x, y);
		
		text = "PRESS [ESC] TO GO BACK";
		x = getXForCenteredText(g, text);
		y += gp.tileSize*2;
		g.drawString(text, x, y);
	}
	
	private void drawGameStats(Graphics g)
	{	
		Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.white);
		
		int y = gp.screenHeight - (80/2) + (g.getFontMetrics(font).getHeight()/4);
		
		g.drawString("SCORE: ", 20, y);	
		g.drawString(String.valueOf(gp.score), 118, y);
		
		g.drawString("HIGHSCORE: ", 210, y);	
		g.drawString(String.valueOf(gp.highscore), 368, y);
		
		g.drawString("LIVES:", 465, y);
		
		y = gp.screenHeight - (80/2) - (gp.tileSize/2); // 730

		for(int i = 0; i < gp.lives; i++)
		{
			g.drawImage(gp.animation.alivePacmanSprites[0][2], 555 + ((gp.tileSize + 5) * i), y, 30, 30, null);
		}
	}
	
	public int getXForCenteredText(Graphics g, String text)
	{
		int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	
	public int getXForCenteredText(Graphics g, String text, int x1, int x2)
	{
		int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x = ((x2+x1)/2) - length/2;
		
		return x;
	}
	
	public int getYForCenteredText(Graphics g, int y1, int y2)
	{
		int height = (int)g.getFontMetrics().getHeight();
		int y = ((y2+y1)/2) + (height/2);
		
		return y;
	}
}
