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
import java.awt.Rectangle;
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
	
	// Game running variables
	private static Thread thread;
	private static boolean isRunning = false;	

	// Pacman portal crossing flag variables
	public static boolean pacmanCrossingLeftPortal  = false;
	public static boolean pacmanCrossingRightPortal = false;

	// Game boundaries variables
	public static final int WIDTH  = 672;
	public static final int	HEIGHT = 800;	
	
	// Game difficulty variable
	public static int difficulty = 1;
	
	// Game characters direction variables
	public static final int right = 1;
	public static final int left  = 2;
	public static final int up    = 3;
	public static final int down  = 4;
	
	// Game speed variable
	public static int speed = 2;
	
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
	public static int inkySpawnX   = 288;
	public static int inkySpawnY   = 320;
	public static int pinkySpawnX  = 320;
	public static int pinkySpawnY  = 320;
	public static int clydeSpawnX  = 352;
	public static int clydeSpawnY  = 320;

	// Pacman lives variable
	public static int lives = 3;
	
	// Ghost identifiers
	public static final int blinkyID = 0;
	public static final int inkyID 	 = 1;
	public static final int pinkyID  = 2;
	public static final int clydeID  = 3;
	
	// Portal coordinate variables
	public static final int leftPortalX	 = 0;
	public static final int rightPortalX = 640;
	public static final int bothPortalsY = 320;
	
	// Game status variables
	public static int gameStatus 	 = 0;
	public static final int init 	 = 1;		
	public static final int play 	 = 2;
	public static final int win 	 = 3;
	public static final int lose 	 = 4; 
	public static final int lifeLost = 5;
	
	// Score variables
	public static int score = 0;
	public static int highscore;
	
	// Game key flag variables
	public boolean enter = false;					
	public boolean space = false;
	
	// Animation variables
	private int blinkTime 	 = 0;							
	private int targetFrames = 30;
	private boolean showText = true;
	
	// Map coordinate variables to locate events
	public static int xEvent;
	public static int yEvent;
	
	// Paths to required resources
	String mapPath    = "/Images/map.png";
	String scoresPath = "res/Files/Scores.txt";
	
	// Pacman animation variables
	public static int pacmanAnimationTime = 0;	
	public static int pacmanAnimationTargetTime = 6;
	
	public static int flashAnimationTime = 0;
	public static int flashAnimationTargetTime = 20;
	
	// Bonus score animation variables
	public static boolean showBonusScore 			= false;
	public static int bonusScoreAnimationTime       = 0;
	public static int bonusScoreAnimationTargetTime = 10;
	public static int bonusScoreFlashes 			= 0;
	public static int bonusScoreTargetFlashes		= 3;
	
	// Constructor
	public Game()
	{
		addKeyListener(this);
		gameStatus = init;			
		new Texture();
		
		// Get game's highscore
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

	
	// Start a new game thread
	public synchronized void startGame()
	{
		// Check if game is already running
		if(isRunning)
		{
			return;
		}
			
		// Start the game
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	// Stop the running game thread
	public synchronized static void stopGame()
	{
		// Check if game is running
		if(!isRunning)
		{
			return;
		}
		
		// Stop the game
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

	
	// Load the required game elements
	private void loadGameElements()
	{
		// Load game characters
		pacman 	= new Pacman(pacmanSpawnX, pacmanSpawnY);
		blinky 	= new Ghost(blinkySpawnX, blinkySpawnY, blinkyID, -1, -1); 			
		inky 	= new Ghost(inkySpawnX, inkySpawnY, inkyID, -1, -1);
		pinky 	= new Ghost(pinkySpawnX, pinkySpawnY, pinkyID, -1, -1);
		clyde 	= new Ghost(clydeSpawnX, clydeSpawnY, clydeID, -1, -1);
		
		// Load other game objects based on game status
		switch(gameStatus)
		{
			case init:
				
				door 	  = new Door(0, 0);
				energizer = new Energizer(0, 0);
				
				// fall through
				
			case win:
				
				// fall through
				
			case lose:
				
				level = new Level(mapPath); 
				break;
				
			default: break;
		}
	}

	
	// Make game's screen text blink
	private void blinkText()
	{
		if(showText)
		{
			showText = false;
			return;
		}
		showText = true;
	}

	// Set game's screen lettering style
	public static void setLetteringStyle(Graphics g, Color color, String font, int fontSize)
	{
		// Lettering colour, size and font
		g.setColor(color);												
		g.setFont(new Font(font, Font.BOLD, fontSize)); 
	}
	
	
	// Draw game's initial screen
	private void drawInitScreen(Graphics g)
	{	
		// Display text
		if(showText)
		{
			g.drawString("ENTER", 266, 350); 
		}
		g.drawString("PRESS       TO START!", 170, 350); 
	}

	// Draw game's winning screen
	private void drawWinScreen(Graphics g)
	{		
		// Display text
		g.drawString("WELL DONE!", 210, 0);
		if(showText)
		{
			g.drawString("ENTER", 156, 350);
		}
		g.drawString("PRESS       TO CONTINUE GAME", 60, 350);
	}
	
	// Draw game's losing screen
	private void drawLoseScreen(Graphics g)
	{		
		// Display text
		g.drawString("BAD LUCK!", 270, 100);
		if(showText)
		{
			g.drawString("ENTER", 156, 350);
			g.drawString("SPACE", 156, 400);
		}
		g.drawString("PRESS       TO RESTART GAME", 60, 350);
		g.drawString("PRESS       TO GO HOME", 60, 400);
	}

	// Manage game's different states
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
				
				break;
				
			case win:
				
				showBonusScore = false;
				
				Energizer.isActive = false;				

				Pacman.resetEatenGhosts();
				
				Energizer.activeTime = 0;
				
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
				
				break;
				
			case lose:
				
				showBonusScore = false;
				lives = 3;
				
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
					CLayout.cardLayout.show(CLayout.panelContainer, "Home");
					gameStatus = init;
				}
				
				break;
				
			case lifeLost:
				
				showBonusScore = false;
				loadGameElements();
				gameStatus = play;
				
				break;
		}
					
		Game.bonusScoreAnimationTime++;
		
		if(Game.bonusScoreAnimationTime == Game.bonusScoreAnimationTargetTime)
		{
			Game.bonusScoreAnimationTime = 0;
			Texture.animationPhaseBonusScore++;
		}
	}
	
	// Render/draw game's screens based on game status 
	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null)				
		{
			createBufferStrategy(3);	
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// Configure screen background and lettering
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		setLetteringStyle(g, Color.white, Font.DIALOG_INPUT, 26);
		
		// Render screens based on status of the game
		switch(gameStatus)
		{
			case init: 		drawInitScreen(g); 	break;
			case win: 		drawWinScreen(g); 	break;
			case lose: 		drawLoseScreen(g); 	break;
			case play: 		Level.render(g); 	break;
			case lifeLost: 	pacman.render(g); 	break;			
		}
		
		g.dispose();
		bs.show();
	}
	
	// Manage game's timing
	@Override
	public void run() 
	{
		requestFocus();					
		
		long lastTime = System.nanoTime();		// Previous game instant
		double targetTick = 60.0; 				// Game speed
		double delta = 0;						// Time difference between game instants
		double ns = 1000000000 / targetTick; 	// Time interval between ticks
				
		// Game loop
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

	
	// Listen for pressed keys
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(gameStatus)
		{
			case play: 
				
				// Read game keys
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_D:	// fall through
					case KeyEvent.VK_RIGHT: Pacman.dir = right; break;
					case KeyEvent.VK_A:	// fall through
					case KeyEvent.VK_LEFT: Pacman.dir = left; break;
					case KeyEvent.VK_W:	// fall through
					case KeyEvent.VK_UP: Pacman.dir = up; break;
					case KeyEvent.VK_S:	// fall through
					case KeyEvent.VK_DOWN: Pacman.dir = down; break;
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

	public static boolean canMove(int direction, int x, int y, int width, int height)
	{
		int nextx = 0, nexty = 0;
		
		switch(direction)
		{
			case right: nextx = x + Game.speed; nexty = y; break;
			case left:	nextx = x - Game.speed; nexty = y; break;
			case up: 	nextx = x; nexty = y - Game.speed; break;
			case down: 	if(x == 320 && y == 256) {return false;}
						nextx = x; nexty = y + Game.speed; break;
		}
		
		Rectangle bounds = new Rectangle(nextx, nexty, width, height);

		for(int xx = 0; xx < Level.tiles.length; xx++)
		{
			for(int yy = 0; yy < Level.tiles[0].length; yy++)
			{
				if(Level.tiles[xx][yy] != null)								
				{
					if(bounds.intersects(Level.tiles[xx][yy]))						
					{
						return false;								
					}
				}
			}
		}
		return true;
	}
	// Unimplemented method
	@Override
	public void keyReleased(KeyEvent e)
	{

	}
	
	// Unimplemented method
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
}
