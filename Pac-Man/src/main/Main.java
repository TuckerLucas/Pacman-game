package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main 
{
	public static JFrame window;
	
	ImageIcon logo = new ImageIcon(getClass().getResource("/Images/logo.png"));
	
	public static void main(String[] args) 
	{
		new Main();
	}
	
	public Main()
	{
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("PACMAN");
		window.setIconImage(logo.getImage());
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.fHandler.loadSettings();
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
