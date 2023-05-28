package PacManJogo;

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

import PacManGUI.CLayout;

public class Game extends Canvas implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;

	private static boolean isRunning = false;	
	private static Thread thread;

	// Game dimension variables
	public static final int WIDTH = 672;
	public static final int	HEIGHT = 900;	
	
	// Game difficulty variable
	public static int difficulty = 1;
	
	// Game object variables
	public static Pacman pacman;
	public static Ghost Blinky;
	public static Ghost Inky;
	public static Ghost Pinky;
	public static Ghost Clyde;
	public static Energizer energizer;
	public static Door door;
	public static Level level;
	
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
	
	// Game status variables
	public static final int INIT = 0;		
	public static final int GAME = 1;
	public static final int WIN = 2;
	public static final int LOSE = 3; 
	public static final int LIFE_LOST = 4;
	public static int GAME_STATUS = -1;
	
	// Score variables
	public static int score = 0;
	public static int highscore;

	// Pacman life lost flag
	public boolean life_was_lost = false;
	
	// Key flag variables
	public boolean Enter = false;					
	public boolean Space = false;
	
	// Animation variables
	private int time = 0;							// Animation variables
	private int targetFrames = 30;
	private boolean showText = true;
	
	// Map event coordinates variables
	public static int x_position;
	public static int y_position;
	
	public static Ghost ghost = null;
	
	public Game()
	{
		addKeyListener(this);
		
		GAME_STATUS = INIT;			
		
		new Texture();
		
    	try
		{
			BufferedReader reader;
			reader = new BufferedReader(new FileReader("Scores.txt"));
			String line = reader.readLine();
			int integer = Integer.parseInt(line);
	        reader.close();
	        highscore = integer;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void start()
	{
		if(isRunning)
			return;
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized static void stop()
	{
		if(!isRunning)
			return;
		
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
		pacman 	= new Pacman(192, 512);
		Blinky 	= new Ghost(320, 256, 0, -1, -1); 			
		Inky 	= new Ghost(288, 320, 1, -1, -1);
		Pinky 	= new Ghost(320, 320, 2, -1, -1);
		Clyde 	= new Ghost(352, 320, 3, -1, -1);
		
		Pacman.lastDir = Pacman.right;
		Pacman.dir = Pacman.right;
	}
	
	private void checkShowText()
	{
		if(showText)
			showText = false;
		else 
			showText = true;
	}
	
	private void tick()
	{
		switch(GAME_STATUS)
		{
			case GAME:
				
				pacman.tick();
				Blinky.tick(); 					
				Inky.tick();
				Pinky.tick();
				Clyde.tick();
				energizer.tick();
				
				break;
				
			case INIT:
				
				time++;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText();
				}
				
				if(Enter)
				{
					Enter = false;
					loadCharacters();
					energizer = new Energizer(Game.WIDTH/2, Game.HEIGHT/2);
					door = new Door(Game.WIDTH/2, Game.HEIGHT/2);
					level = new Level("/map/map.png");
					GAME_STATUS = GAME;
				}
				
				break;
				
			case WIN:
				
				Pacman.bonusScore = -1;
				
				time++;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText();
				}
				if(Enter)
				{
					Enter = false;
					loadCharacters();
					level = new Level("/map/map.png");
					GAME_STATUS = GAME;
				}
				
				break;
				
			case LOSE:
				
				Pacman.bonusScore = -1;
				
				if(score >= highscore) 
	        		highscore = score;
				
				time++;
			
				Pacman.lives = 3;
				
				if(time == targetFrames)
				{
					time = 0;
					
					checkShowText(); 
				}
				
				if(Enter)
				{
					score = 0;
					Enter = false;
					loadCharacters();
					level  = new Level("/map/map.png");
					GAME_STATUS = GAME;
				}
				
				if(Space)
				{
					score = 0;
					loadCharacters();
					level = new Level("/map/map.png");
					GAME_STATUS = INIT;
					CLayout.cardLayout.show(CLayout.panelContainer, "Home");
					Space = false;
				}
				
				break;
				
			case LIFE_LOST:
				
				Pacman.bonusScore = -1;
				life_was_lost = true;
				loadCharacters();
				GAME_STATUS = GAME;
				
				break;
		}
	}
	
	private void setLetteringStyle(Graphics g)
	{
		// Lettering color, size and font
		g.setColor(Color.white);												
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 26)); 
	}
	
	private void drawWinScreen(Graphics g)
	{		
		setLetteringStyle(g);
		
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
		setLetteringStyle(g);
		
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
		setLetteringStyle(g);
		
		// Displayed text
		if(showText)
			g.drawString("ENTER", 266, 350); 	
		
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
		
		switch(GAME_STATUS)
		{
			case GAME: Level.render(g); break;
			case INIT: drawInitScreen(g); break;
			case WIN: drawWinScreen(g); break;
			case LOSE: drawLoseScreen(g); break;
			case LIFE_LOST: pacman.render(g); break;			
		}
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() 
	{
		requestFocus();					
		
		long lastTime = System.nanoTime();
		double targetTick = 65.0; 				// Game speed
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
		switch(GAME_STATUS)
		{
			case GAME: 
				
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_RIGHT: Pacman.dir = Pacman.right; break;
					case KeyEvent.VK_LEFT: Pacman.dir = Pacman.left; break;
					case KeyEvent.VK_UP: Pacman.dir = Pacman.up; break;
					case KeyEvent.VK_DOWN: Pacman.dir = Pacman.down; break;
				}
				
				break;
				
			case LOSE:
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
					Space = true;
				
				// fall through
				
			case INIT:	
				
				// fall through
				
			case WIN:
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					Enter = true;						
				
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
