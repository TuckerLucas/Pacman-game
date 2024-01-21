package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import food.Energizer;
import food.Food;
import character.AlivePacman;
import Game.Animation;
import Game.BonusScore;
import character.Character;
import character.DeadPacman;
import character.Ghost;
import Game.Level;
import character.Pacman;
import Game.SpawnBoxDoor;

public class GamePanel extends Canvas implements Runnable
{	
	private static final long serialVersionUID = 1L;
	
	final int originalTileSize = 16;
	final int scale = 2;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 21;
	public final int maxScreenRow = 22;
	
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = ((tileSize * maxScreenRow) + 80);	// 704 game + 80 data
	
	Thread gameThread;	
	
	public int gameStatus = 0;
	final int init = 1;		
	final int play = 2;
	final int win = 3;
	final int lose = 4; 
	public final int lifeLost = 5;
	final int settings = 6;
	
	public int highscore;
	public int score = 0;
	
	private int blinkTime = 0;							
	private int targetFrames = 30;
	public boolean showText = true;
	
	private String scoresPath = "res/Files/LeaderboardInfo.txt";

	private double targetTick = 60.0; 				
	public double secondsPerTick = 1.0 / targetTick;
	
	public int menuOptionIndex = 0;
	
	public List<Food> foodList;
	public BonusScore bonusScore;
	public Pacman pacman;
	public Energizer energizer;
	public SpawnBoxDoor spawnBoxDoor;
	Font maruMonica;
	
	public KeyHandler keyH = new KeyHandler(this);
	
	public UI ui = new UI(this);
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		gameStatus = init;			
		Animation.animation = new Animation();
		
		getGameHighScore();
	}

	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGame()
	{	
		try
		{
			gameThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void getGameHighScore()
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
	
	public void loadGameElements()
	{
		pacman = new AlivePacman(Character.right, Character.right, 320, 512, this);
		Ghost.ghostArray[0] = new Ghost(0, Ghost.randomMovement, Character.notCrossingPortal, false, this); 
		Ghost.ghostArray[1] = new Ghost(1, Ghost.randomMovement, Character.notCrossingPortal, false, this);
		Ghost.ghostArray[2] = new Ghost(2, Ghost.randomMovement, Character.notCrossingPortal, false, this);
		Ghost.ghostArray[3] = new Ghost(3, Ghost.randomMovement, Character.notCrossingPortal, false, this);
		
		switch(gameStatus)
		{
			case init:

				foodList = new ArrayList<>();	
				spawnBoxDoor = new SpawnBoxDoor(0, 0);
				energizer = new Energizer(0, 0, this);
				
				// fall through
				
			case win:
				
				// fall through
				
			case lose:
				
				// Load bonus score object
				bonusScore = new BonusScore(this);
				
				Level.level = new Level(this); 
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
	
	private void tick()
	{
		switch(gameStatus)
		{
			case play:
				
				pacman.tick();
				Ghost.ghostArray[0].tick(); 
				Ghost.ghostArray[1].tick();
				Ghost.ghostArray[2].tick();
				Ghost.ghostArray[3].tick();
				bonusScore.tick();
				energizer.tick();

				if(foodList.size() == 0)
				{
					gameStatus = win;
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
				
				break;
				
			case lifeLost:
				
				BonusScore.isBeingDisplayed = false;
				
				if(!DeadPacman.pacmanDeathAnimationHasFinished)
				{
					pacman.tick();
					
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
		
		ui.render(g);
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() 
	{					
		long lastTime = System.nanoTime();		// Previous game instant
		double delta = 0;						// Time difference between game instants
		double drawInterval = 1000000000 / targetTick; 	// Time interval between ticks
				
		while(gameThread != null)				
		{	
			long currentTime = System.nanoTime();		// This game instant
			delta += (currentTime - lastTime) / drawInterval;		// Calculate time difference between instants
			lastTime = currentTime;						// Update previous game instant
			
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