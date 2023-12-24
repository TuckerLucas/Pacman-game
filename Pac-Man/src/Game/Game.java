package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable
{	
	private static final long serialVersionUID = 1L;
	
	static final int originalTileSize = 16;
	static final int scale = 2;
	
	public static final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 21;
	public final int maxScreenRow = 22;
	
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = (tileSize * maxScreenRow) + 80;	// 704 game + 80 data
	
	private static Thread thread;
	private static boolean isRunning = false;	
	
	public static int gameStatus = 0;
	public static final int init = 1;		
	public static final int play = 2;
	public static final int win = 3;
	public static final int lose = 4; 
	public static final int lifeLost = 5;
	
	public static int highscore;
	public static int score = 0;
	
	public boolean enter = false;					
	public boolean space = false;
	
	private int blinkTime = 0;							
	private int targetFrames = 30;
	private static boolean showText = true;
	
	private static String scoresPath = "res/Files/LeaderboardInfo.txt";

	private static double targetTick = 60.0; 				
	public static double secondsPerTick = 1.0 / targetTick;
	
	public int commandNumInit = 0;
	public int commandNumLose = 0;
	public int commandNumWin = 0;
	
	static Font maruMonica;
	
	public KeyHandler keyH = new KeyHandler(this);
	
	public Game()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
		addKeyListener(keyH);
		gameStatus = init;			
		Animation.animation = new Animation();
		
		getGameHighScore();
		
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

	public synchronized void startGame()
	{
		if(isRunning)
		{
			return;
		}
			
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized static void stopGame()
	{
		if(!isRunning)
		{
			return;
		}
		
		isRunning = false;
		
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void getGameHighScore()
	{
		try
		{
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(scoresPath));
			String line = reader.readLine();
			highscore = Integer.parseInt(line);
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadGameElements()
	{
		Pacman.pacman = new AlivePacman(Character.right, Character.right, 320, 512);
		Ghost.ghostArray[0] = new Ghost(0, Ghost.randomMovement, Character.notCrossingPortal, false); 
		Ghost.ghostArray[1] = new Ghost(1, Ghost.randomMovement, Character.notCrossingPortal, false);
		Ghost.ghostArray[2] = new Ghost(2, Ghost.randomMovement, Character.notCrossingPortal, false);
		Ghost.ghostArray[3] = new Ghost(3, Ghost.randomMovement, Character.notCrossingPortal, false);
		
		switch(gameStatus)
		{
			case init:

				Food.foodList = new ArrayList<>();	
				SpawnBoxDoor.spawnBoxDoor = new SpawnBoxDoor(0, 0);
				Energizer.energizer = new Energizer(0, 0);
				
				// fall through
				
			case win:
				
				// fall through
				
			case lose:
				
				// Load bonus score object
				BonusScore.bonusScore = new BonusScore();
				
				Level.level = new Level(); 
				break;
				
			default: break;
		}
	}

	private void blinkText()
	{
		if(showText)
		{
			showText = false;
			return;
		}
		showText = true;
	}

	public static void setLetteringStyle(Graphics g, Color color, String font, int fontSize)
	{
		g.setColor(color);												
		g.setFont(new Font(font, Font.BOLD, fontSize)); 
	}
	

	public void drawInitScreen(Graphics g)
	{	
		// BACKGROUND COLOR
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, screenWidth, screenHeight);
		
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
		
		if(commandNumInit == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "LEADERBOARD";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumInit == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "SETTINGS";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumInit == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "AUTHOR'S NOTE";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumInit == 3)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumInit == 4)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	public int getXForCenteredText(Graphics g, String text)
	{
		int length = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x = screenWidth/2 - length/2;
		
		return x;
	}

	private void drawWinScreen(Graphics g)
	{		
		String text = "YOU WON!";
		
		g.setFont(maruMonica);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 92F));
		int x = getXForCenteredText(g, text);
		int y = 32*5;
		
		if(showText)
		{
			g.setColor(Color.white);
			g.drawString(text, x + 4, y + 4);
			
			g.setColor(Color.black);
			g.drawString(text, x + 3, y + 3);
			
			g.setColor(Color.green);
			g.drawString(text, x, y);
		}
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + score;
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
		
		if(commandNumWin == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "HOME";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumWin == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumWin == 2)
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
		
		if(showText)
		{
			g.setColor(Color.white);
			g.drawString(text, x + 4, y + 4);
			
			g.setColor(Color.black);
			g.drawString(text, x + 3, y + 3);
			
			g.setColor(Color.red);
			g.drawString(text, x, y);
		}
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 60F));
		text = "SCORE : " + score;
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
		
		if(commandNumLose == 0)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "PLAY AGAIN";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumLose == 1)
		{
			g.drawString(">",  x - 32,  y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(g, text);
		y += 40;
		g.drawString(text, x, y);
		
		if(commandNumLose == 2)
		{
			g.drawString(">",  x - 32,  y);
		}
	}
	
	private void drawPacmanDying(Graphics g)
	{
		Pacman.pacman.render(g);
		
		if(gameStatus != lose)
		{
			Level.level.render(g);
		}
	}

	private void tick()
	{
		switch(gameStatus)
		{
			case play:
				
				Pacman.pacman.tick();
				Ghost.ghostArray[0].tick(); 
				Ghost.ghostArray[1].tick();
				Ghost.ghostArray[2].tick();
				Ghost.ghostArray[3].tick();
				BonusScore.bonusScore.tick();
				Energizer.energizer.tick();
				Level.level.tick();

				break;
				
			case init:
				
				blinkTime++;
				
				if(blinkTime == targetFrames)
				{
					blinkTime = 0;
					blinkText();
				}
				
				if(enter)
				{
					enter = false;
					loadGameElements();
					gameStatus = play;

            		//Sounds.loop(Sounds.sirenSoundPath);
				}
				
				break;
				
			case win:
				
				BonusScore.isBeingDisplayed = false;
				
				Energizer.isActive = false;	
				
				Energizer.elapsedTimeWhileActiveInSeconds = 0.0f;
				
				blinkTime++;
				
				if(blinkTime == targetFrames)
				{
					blinkTime = 0;
					blinkText();
				}
				
				if(enter)
				{
					enter = false;
					loadGameElements();
					gameStatus = play;
				}
				
				if(space == true)
				{
					space = false;
					score = 0;
					gameStatus = init;
				}
				
				break;
				
			case lose:
				
				BonusScore.isBeingDisplayed = false;
				
				Pacman.numberOfLives = 3;
				
				if(score >= highscore)
				{
	        		highscore = score;
				}
				
				blinkTime++;
				
				if(blinkTime == targetFrames)
				{
					blinkTime = 0;
					blinkText(); 
				}
				
				if(enter == true)
				{
					enter = false;
					score = 0;
					loadGameElements();
					gameStatus = play;
				}
				
				if(space == true)
				{
					space = false;
					score = 0;
					gameStatus = init;
				}
				
				break;
				
			case lifeLost:
				
				BonusScore.isBeingDisplayed = false;
				
				if(!DeadPacman.pacmanDeathAnimationHasFinished)
				{
					Pacman.pacman.tick();
					
					if(DeadPacman.pacmanDeathAnimationHasFinished)
					{
						Pacman.numberOfLives--;
						
						DeadPacman.pacmanDeathAnimationHasFinished = false;
						
						if(Pacman.numberOfLives == 0) 
						{
							gameStatus = lose;
						}
						else
						{	
							loadGameElements();
							gameStatus = play;
						}
						
						Energizer.deactivate();
					}
				}
				
				break;
		}
		
		if(score >= highscore)
		{
			highscore = score;
		}
	}
	
	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null)				
		{
			createBufferStrategy(3);	
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 26);
		
		switch(gameStatus)
		{
			case init: drawInitScreen(g); break;
			case win: drawWinScreen(g); break;
			case lose: drawLoseScreen(g); break;
			case lifeLost: drawPacmanDying(g); break; 
			case play: Level.level.render(g); break;
		}
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() 
	{					
		long lastTime = System.nanoTime();		// Previous game instant
		double delta = 0;						// Time difference between game instants
		double ns = 1000000000 / targetTick; 	// Time interval between ticks
				
		while(isRunning)				
		{	
			long now = System.nanoTime();		// This game instant
			delta += (now - lastTime) / ns;		// Calculate time difference between instants
			lastTime = now;						// Update previous game instant
			
			while(delta >= 1)					// Check if enough time has elapsed	to justify updating the game		
			{
				tick();							// Check for in game character positions and collisions			
				render();						// Render game components
				delta--;						// Update time difference between instants
			}
		}
		
		stopGame();							
	}


}