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

	public static final int WIDTH = 672, HEIGHT = 750;	
	
	private static Thread thread;
	
	public static int difficulty = 1;
	public static Pacman pacman;
	public static Ghost Blinky;
	public static Ghost Inky;
	public static Ghost Pinky;
	public static Ghost Clyde;
	public static Energizer energizer;
	public static Door door;
	public static Level level;
	public static int crossmap = -1;
	public static int crossmap1 = -1;
	public static int crossmap2 = -1;
	public static int crossmap3 = -1;
	 
	public static final int PAUSE_SCREEN = 0;		
	public static final int GAME = 1;
	public static final int WIN = 2;
	public static final int LOSE = 3; 
	public static final int LIFE_LOST = 4;
	public static int STATE = -1;
	
	public static int score = 0;
	public static int highscore;

	public static boolean life_was_lost = false;
	public boolean Enter = false;					
	public boolean Space = false;
	
	private int time = 0;							// Animation variables
	private int targetFrames = 30;
	private boolean showText = true;
	
	public static int x_position;
	public static int y_position;
	
	public Game()
	{
		addKeyListener(this);
		 
		STATE = PAUSE_SCREEN;			
		
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

	private void tick()
	{
		if(STATE == GAME)		
		{	
			pacman.tick();
			Blinky.tick(); 					
			Inky.tick();
			Pinky.tick();
			Clyde.tick();
			energizer.tick();
		}
		else if(STATE == PAUSE_SCREEN)
		{
			time++;
			
			if(time == targetFrames)
			{
				time = 0;
				
				if(showText == true)
				{
					showText = false;
				}
				else 
				{
					showText = true; 
				}
			}
			if(Enter == true)
			{
				Enter = false;
				
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				Blinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 0, -1, -1); 			
				Inky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 1, -1, -1);
				Pinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 2, -1, -1);
				Clyde = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 3, -1, -1);
				energizer = new Energizer(Game.WIDTH/2, Game.HEIGHT/2);
				door = new Door(Game.WIDTH/2, Game.HEIGHT/2);
				
				level = new Level("/map/map.png");
				
				Pacman.lastDir = 1;
				
				Pacman.dir = Pacman.right;
				
				STATE = GAME;
			}
		}
		else if(STATE == WIN)
		{
			Pacman.score2 = false;
			Pacman.score16 = false;
			Pacman.score8 = false;
			Pacman.score4 = false;
			
			time++;
			
			if(time == targetFrames)
			{
				time = 0;
				
				if(showText == true)
				{
					showText = false;
				}
				else 
				{
					showText = true; 
				}
			}
			if(Enter == true)
			{
				Enter = false;
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				Blinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 0, -1, -1); 			
				Inky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 1, -1, -1);
				Pinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 2, -1, -1);
				Clyde = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 3, -1, -1);
				level = new Level("/map/map.png");
				
				Pacman.lastDir = 1;
				Pacman.dir = Pacman.right;

				STATE = GAME;
			}
		}
		else if(STATE == LOSE)
		{
			Pacman.score2 = false;
			Pacman.score16 = false;
			Pacman.score8 = false;
			Pacman.score4 = false;
			
			if(score >= highscore) 
			{
        		highscore = score;
        	}
			
			time++;
		
			Pacman.lives = 3;
			
			if(time == targetFrames)
			{
				time = 0;
				
				if(showText == true)
				{
					showText = false;
				}
				else 
				{
					showText = true; 
				}
			}
			if(Enter == true)
			{
				score = 0;
				Enter = false;
				
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				Blinky  = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 0, -1, -1); 			
				Inky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 1, -1, -1);
				Pinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 2, -1, -1);
				Clyde = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 3, -1, -1);
				level  = new Level("/map/map.png");
				
				Pacman.lastDir 	= 1;
				Pacman.dir 		= Pacman.right;
				
				STATE = GAME;
			}
			
			if(Space == true)
			{
				score = 0;
				Space = false;
				
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				Blinky  = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 0, -1, -1); 			
				Inky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 1, -1, -1);
				Pinky = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 2, -1, -1);
				Clyde = new Ghost(Game.WIDTH/2, Game.HEIGHT/2, 3, -1, -1);
				level  = new Level("/map/map.png");
				
				Pacman.lastDir 	= 1;
				Pacman.dir 		= Pacman.right;
				
				STATE = PAUSE_SCREEN;
				
				CLayout.cardLayout.show(CLayout.panelContainer, "Home");
			}
		}
		else if(STATE == LIFE_LOST)
		{
			Pacman.score2 = false;
			Pacman.score16 = false;
			Pacman.score8 = false;
			Pacman.score4 = false;
			
			life_was_lost = true;
			
			pacman = new Pacman(192, 512);
			Blinky = new Ghost(320, 256, 0, -1, -1); 			
			Inky = new Ghost(288, 320, 1, -1, -1);
			Pinky = new Ghost(320, 320, 2, -1, -1);
			Clyde = new Ghost(352, 320, 3, -1, -1);
			
			Pacman.lastDir = 1;
			Pacman.dir = Pacman.right;

			STATE = GAME;
		}
	}
	
	private void draw_win_screen(Graphics g)
	{
		int boxWidth = 560;														//largura da janela do menu				
		int boxHeight = 400;													//altura da janela do menu
		int xx = Game.WIDTH / 2 - boxWidth / 2;
		int yy = Game.HEIGHT / 2 - boxHeight / 2;
		
		g.setColor(Color.white);												//cor da letra do menu
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 26)); 						//tipo de letra do menu
		g.drawString("WELL DONE!", xx + 210, yy);
		g.drawString("CURRENT SCORE: ", xx + 180, yy + 150);
		g.drawString(String.valueOf(score), xx + 280, yy + 195);
		
		g.drawString("PRESS       TO CONTINUE GAME", xx + 60, yy + 350);
		
		if(showText == true)
		{
			g.drawString("      ENTER", xx + 60, yy + 350);
		}
	}
	
	private void draw_lose_screen(Graphics g)
	{
		int boxWidth = 560;						
		int boxHeight = 400;		
		int xx = Game.WIDTH / 2 - boxWidth / 2;
		int yy = Game.HEIGHT / 2 - boxHeight / 2;
		
		g.setColor(Color.white);												
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 26));
		
		g.drawString("BAD LUCK!", xx + 200, yy);
		g.drawString("YOU SCORED: ", xx + 190, yy + 150);
		g.drawString(String.valueOf(score), xx + 260, yy + 195);
		
		g.drawString("PRESS       TO RESTART GAME", xx + 60, yy + 350);
		g.drawString("PRESS       TO GO HOME", xx + 60, yy + 400);
		if(showText == true)
		{
			g.drawString("      ENTER", xx + 60, yy + 350);
			g.drawString("      SPACE", xx + 60, yy + 400);
		}
	}
	
	private void draw_pause_screen(Graphics g)
	{
		int boxWidth = 560;							
		int boxHeight = 400;		
		int xx = Game.WIDTH / 2 - boxWidth/ 2;
		int yy = Game.HEIGHT / 2 - boxHeight / 2;
		
		g.setColor(new Color(0,0,0));
		g.fillRect(xx, yy, boxWidth, boxHeight);
		
		g.setColor(Color.white);												
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 26)); 
		g.drawString("PRESS       TO START!", xx + 100, yy + 200); 
		if(showText == true)
		{
			g.drawString("ENTER", xx + 196, yy + 200); 	
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
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		if(STATE == GAME)			
		{
			Level.render(g);
		}
		else if(STATE == PAUSE_SCREEN) 	
		{	
			//draw_win_screen(g);
			draw_pause_screen(g);
		}
		else if(STATE == WIN)
		{
			draw_win_screen(g);
		}
		else if(STATE == LOSE)
		{
			draw_lose_screen(g);
		}
		else if(STATE == LIFE_LOST)
		{
			pacman.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() 
	{
		requestFocus();					
		//int fps = 0;				
		//double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 65.0; 		//velocidade do jogo
		double delta = 0;
		double ns = 1000000000 / targetTick; //intervalo entre ticks 
				
		while(isRunning)				//loop do jogo
		{	
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				render();
				//fps++;
				delta--;		//compensar para manter a 60 fps
			}
			
			/*
			if(System.currentTimeMillis() - timer >= 1000)	//maior ou igual a um segundo
			{
				System.out.println(fps);		//imprimir fps
				fps = 0;
				timer += 1000;
			}
			*/
		}
		stop();							
	}

	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(STATE == GAME)						
		{												
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				Pacman.dir = Pacman.right;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				Pacman.dir =  Pacman.left;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				Pacman.dir = Pacman.up;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				Pacman.dir = Pacman.down;
			}
		}
		else if(STATE == PAUSE_SCREEN)			
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				Enter = true;						
			}
		}
		else if(STATE == WIN)				
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)	
			{
				Enter = true;						
			}
		}
		else if(STATE == LOSE)				
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)	
			{
				Enter = true;						
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				Space = true;
			}
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
