package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import food.Energizer;
import food.Food;
import Game.Animation;
import Game.BonusScore;
import character.Character;
import character.Ghost;
import Game.Level;
import character.Pacman;
import character.AlivePacman;
import Game.SpawnBoxDoor;
import Game.Wall;

public class GamePanel extends Canvas implements Runnable
{	
	private static final long serialVersionUID = 1L;
	
	// Screen settings
	final int originalTileSize = 16;
	final int scale = 2;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 21;
	public final int maxScreenRow = 22;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = ((tileSize * maxScreenRow) + 80);	// 704 game + 80 data
	
	// Timing settings
	private double targetTick = 60.0; 				
	public double secondsPerTick = 1.0 / targetTick;
	
	// System
	public AssetSetter aSetter = new AssetSetter(this);
	public KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// Characters and objects
	public Animation animation = new Animation();
	public List<Food> foodList = new ArrayList<>();
	public BonusScore bonusScore = new BonusScore(this);
	public Pacman pacman = new AlivePacman(Character.right, Character.right, 320, 512, this);
	public Ghost ghostArray[] = new Ghost[4];
	public SpawnBoxDoor spawnBoxDoor = new SpawnBoxDoor(this, 320, 288);
	public Wall[][] wallMatrix;
	public Energizer energizer = new Energizer(0, 0, this);
	public Level level;
	
	// Game state
	public int gameState;
	public final int titleState = 0;		
	public final int playState = 1;
	public final int winState = 2;
	public final int gameOverState = 3; 
	public final int lifeLostState = 4;
	public final int settingsState = 5;
	
	public boolean isActive = false;
	public int numberOfEatenGhosts = 0;	
	public int highscore;
	public int score = 0;							
	public int targetFrames = 30;
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame()
	{
		aSetter.setGhosts();
		aSetter.setPacman();
		aSetter.setDoor();
		
		gameState = titleState;			
		
		getGameHighScore();
	}
	
	public void resetGame()
	{
		score = 0;
		Pacman.numberOfLives = 3;	
		resetLevel();
	}
	
	public void resetLevel()
	{
		isActive = false;
		Energizer.elapsedTimeWhileActiveInSeconds = 0.0f;
		respawnCharacters();
		level = new Level(this);
	}
	
	public void respawnCharacters()
	{
		bonusScore.isBeingDisplayed = false;
		deactivate();
		aSetter.setGhosts();
		aSetter.setPacman();
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
			reader = new BufferedReader(new FileReader("res/Files/LeaderboardInfo.txt"));
			String line = reader.readLine();
			highscore = Integer.parseInt(line);
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void turnAllVulnerable()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i].isVulnerable = true;
		}
		
		numberOfEatenGhosts = 0;
	}
	
	public void turnAllHostile()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i].isVulnerable = false;
		}
	}
	
	public void startFlashing()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			if(ghostArray[i].isVulnerable)
			{
				ghostArray[i].isFlashing = true;
			}
		}
	}
	
	public void stopFlashing()
	{
		for(int i = 0; i < ghostArray.length; i++)
		{
			ghostArray[i].isFlashing = false;
		}
	}
	
	public void activate()
	{	
		Sounds.playSoundEffect(Sounds.eatenEnergizerSoundPath);
		
		Energizer.elapsedTimeWhileActiveInSeconds = 0.0f;
		isActive = true;	
		stopFlashing();
		
		turnAllVulnerable();
	}
	
	public void deactivate()
	{	
		turnAllHostile();
	}
	
	private void tick()
	{
		if(gameState == playState)
		{
			pacman.tick();
			
			for(int i = 0; i < ghostArray.length; i++)
			{
				ghostArray[i].tick();
			}
			
			bonusScore.tick();
			energizer.tick();
		}
		else if(gameState == lifeLostState)
		{
			pacman.tick();
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