/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Game.java
* 
* Description: 
* 
* This file manages the game states and is responsible for 
* loading the characters into the map as well as displaying
* the appropriate content/messages to the user depending on the 
* current state of the game.
* 
/**************************************************************/

package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import GUI.CLayout;

public class Game extends Canvas implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;

	private static boolean isRunning = false;	
	private static Thread thread;

	// Game dimension variables
	public static final int WIDTH = 672;
	public static final int	HEIGHT = 800;	
	
	// Game difficulty variable
	public static int difficulty = 1;
	
	// Game object variables
	public static Pacman pacman;			
	public static Ghost blinky;				// Red ghost
	public static Ghost inky;				// Blue ghost
	public static Ghost pinky;				// Pink ghost
	public static Ghost clyde;				// Orange ghost
	public static Energizer energizer;
	public static Door door;
	public static Level level;
	
	// Pacman spawn coordinate variables
	public static int pacmanSpawnX = 192;
	public static int pacmanSpawnY = 512;
	
	// Ghost spawn coordinate variables
	public static int blinkySpawnX = 320;
	public static int blinkySpawnY = 256;
	public static int inkySpawnX = 288;
	public static int inkySpawnY = 320;
	public static int pinkySpawnX = 320;
	public static int pinkySpawnY = 320;
	public static int clydeSpawnX = 352;
	public static int clydeSpawnY = 320;
	
	// Ghost ID's
	public static final int blinkyID = 0;
	public static final int inkyID = 1;
	public static final int pinkyID = 2;
	public static final int clydeID = 3;
	
	// Portal coordinate variables
	public static final int leftPortalX = 0;
	public static final int leftPortalY = 320;
	public static final int rightPortalX = 640;
	public static final int rightPortalY = 320;
	
	// Game status variables
	public static final int init = 0;		
	public static final int play = 1;
	public static final int win = 2;
	public static final int lose = 3; 
	public static final int lifeLost = 4;
	public static int gameStatus = -1;
	
	// Score variables
	public static int score = 0;
	public static int highscore;
	
	// Key flag variables
	public boolean enter = false;					
	public boolean space = false;
	
	// Animation variables
	private int time = 0;							
	private int targetFrames = 30;
	private boolean showText = true;
	
	// Map event coordinates variables
	public static int xEvent;
	public static int yEvent;
	
	// Path to map image
	String mapPath = "/Images/map.png";
			
	public Game()
	{
		addKeyListener(this);
		
		gameStatus = init;			
		
		new Texture();
		
    	try
		{
			BufferedReader reader;
			reader = new BufferedReader(new FileReader("res/Files/Scores.txt"));
			String line = reader.readLine();
			highscore = Integer.parseInt(line);
	        reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void start()
	{
		if(isRunning)
		{
			return;
		}
			
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized static void stop()
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

	private void loadCharacters()
	{
		pacman = new Pacman(pacmanSpawnX, pacmanSpawnY);
		blinky = new Ghost(blinkySpawnX, blinkySpawnY, blinkyID, -1, -1); 			
		inky = new Ghost(inkySpawnX, inkySpawnY, inkyID, -1, -1);
		pinky = new Ghost(pinkySpawnX, pinkySpawnY, pinkyID, -1, -1);
		clyde = new Ghost(clydeSpawnX, clydeSpawnY, clydeID, -1, -1);
		
		Pacman.lastDir = Pacman.right;
		Pacman.dir = Pacman.right;
	}
	
	private void checkShowText()
	{
		if(showText)
		{
			showText = false;
			return;
		}
		
		showText = true;
	}
	
	private void tick()
	{
		switch(gameStatus)
		{
			case play:
				
				pacman.tick();
				blinky.tick(); 					
				inky.tick();
				pinky.tick();
				clyde.tick();
				energizer.tick();
				
				break;
				
			case init:
				
				time++;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText();
				}
				
				if(enter)
				{
					enter = false;
					loadCharacters();
					energizer = new Energizer(Game.WIDTH/2, Game.HEIGHT/2);
					door = new Door(Game.WIDTH/2, Game.HEIGHT/2);
					level = new Level("/Images/map.png");
					gameStatus = play;
				}
				
				break;
				
			case win:
				
				Pacman.bonusScore = -1;
				
				time++;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText();
				}
				if(enter)
				{
					enter = false;
					loadCharacters();
<<<<<<< HEAD
					level = new Level(mapPath);
=======
					level = new Level("/Images/map.png");
>>>>>>> branch 'main' of https://github.com/TuckerLucas/Pacman-Java
					gameStatus = play;
				}
				
				break;
				
			case lose:
				
				Pacman.bonusScore = -1;
				
				if(score >= highscore)
				{
	        		highscore = score;
				}
				
				time++;
			
				Pacman.lives = 3;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText(); 
				}
				
				if(enter)
				{
					score = 0;
					enter = false;
					loadCharacters();
<<<<<<< HEAD
					level = new Level(mapPath);
=======
					level  = new Level("/Images/map.png");
>>>>>>> branch 'main' of https://github.com/TuckerLucas/Pacman-Java
					gameStatus = play;
				}
				
				if(space)
				{
					score = 0;
					loadCharacters();
<<<<<<< HEAD
					level = new Level(mapPath);
=======
					level = new Level("/Images/map.png");
>>>>>>> branch 'main' of https://github.com/TuckerLucas/Pacman-Java
					gameStatus = init;
					CLayout.cardLayout.show(CLayout.panelContainer, "Home");
					space = false;
				}
				
				break;
				
			case lifeLost:
				
				Pacman.bonusScore = -1;
				loadCharacters();
				gameStatus = play;
				
				break;
		}
	}
	
	public static void setLetteringStyle(Graphics g, Color color, String font, int fontSize)
	{
		// Lettering colour, size and font
		g.setColor(color);												
		g.setFont(new Font(font, Font.BOLD, fontSize)); 
	}
	
	
	private void drawWinScreen(Graphics g)
	{		
		setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 26);
		
		// Displayed text
		g.drawString("WELL DONE!", 210, 0);
		g.drawString("CURRENT SCORE: ", 180, 150);
		g.drawString(String.valueOf(score), 280, 195);
		if(showText)
			g.drawString("ENTER", 60, 350);
		g.drawString("PRESS       TO CONTINUE GAME", 60, 350);
	}
	
	private void drawLoseScreen(Graphics g)
	{		
		setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 26);
		
		// Displayed text
		g.drawString("BAD LUCK!", 270, 100);
		g.drawString("YOU SCORED: ", 255, 180);
		g.drawString(String.valueOf(score), 270, 220);
		
		if(showText)
		{
			g.drawString("ENTER", 156, 350);
			g.drawString("SPACE", 156, 400);
		}
		
		g.drawString("PRESS       TO RESTART GAME", 60, 350);
		g.drawString("PRESS       TO GO HOME", 60, 400);
	}
	
	private void drawInitScreen(Graphics g)
	{	
		setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 26);
		
		// Displayed text
		if(showText)
		{
			g.drawString("ENTER", 266, 350); 
		}
		
		g.drawString("PRESS       TO START!", 170, 350); 
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
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		switch(gameStatus)
		{
			case play: Level.render(g); break;
			case init: drawInitScreen(g); break;
			case win: drawWinScreen(g); break;
			case lose: drawLoseScreen(g); break;
			case lifeLost: pacman.render(g); break;			
		}
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() 
	{
		requestFocus();					
		
		long lastTime = System.nanoTime();
		double targetTick = 60.0; 				// Game speed
		double delta = 0;
		double ns = 1000000000 / targetTick; 	// Time interval between ticks
				
		// Game loop
		while(isRunning)				
		{	
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				render();
				delta--;		
			}
		}
		stop();							
	}

	
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(gameStatus)
		{
			case play: 
				
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_D:	// fall through
					case KeyEvent.VK_RIGHT: Pacman.dir = Pacman.right; break;
					case KeyEvent.VK_A:	// fall through
					case KeyEvent.VK_LEFT: Pacman.dir = Pacman.left; break;
					case KeyEvent.VK_W:	// fall through
					case KeyEvent.VK_UP: Pacman.dir = Pacman.up; break;
					case KeyEvent.VK_S:	// fall through
					case KeyEvent.VK_DOWN: Pacman.dir = Pacman.down; break;
				}
				
				break;
				
			case lose:
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					space = true;
				}
				
				// fall through
				
			case init:	
				
				// fall through
				
			case win:
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					enter = true;					
				}
				
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
}
