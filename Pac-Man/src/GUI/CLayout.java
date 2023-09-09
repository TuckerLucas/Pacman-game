/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: CLayout.java
* 
* Description: 
* 
* This file sets the layout to be used by the application's 
* graphical user interface and declares all the panels to be 
* used in the layout. This class contains the main function.
* 
/**************************************************************/

package GUI;

import java.awt.*;
import javax.swing.*;

import Game.Game;
import Game.Sounds;

public class CLayout
{		
	// Card layout JFrame to include container
	JFrame CLayout = new JFrame("Pacman Java Game");
	
	// Create new card layout
	public static CardLayout cardLayout = new CardLayout();
	
	// Container to include JPanels
	public static JPanel panelContainer = new JPanel();
	
	// JPanels supported by the application
	HomePanel panelHome = new HomePanel();
	PlayPanel panelPlay = new PlayPanel();
	//LeaderboardPanel panelLeaderboard = new LeaderboardPanel();
	HowToPlayPanel panelHowToPlay = new HowToPlayPanel();
	SettingsPanel panelSettings = new SettingsPanel();
	ProjectInfoPanel panelProjectInfo = new ProjectInfoPanel();
	
	// JPanel to include game
	JPanel GamePanel = new JPanel();
	
	// Create a new game object
	static Game game = new Game();	
	
	// Window dimensions
	public static int windowWidth = 672;
	public static int windowHeight = 816;
	
	// Constructor
	public CLayout() 
	{
		// Set game dimensions
    	game.setBounds(0, 0, windowWidth, windowHeight);
    	
		// Set the container's layout
		panelContainer.setLayout(cardLayout);
		
		// Add panels to the container
		panelContainer.add(panelHome, "Home");
		panelContainer.add(panelPlay, "Play");
		//panelContainer.add(panelLeaderboard, "Leaderboard");
		panelContainer.add(panelHowToPlay, "HowToPlay");
		panelContainer.add(panelSettings, "Settings");
		panelContainer.add(panelProjectInfo, "ProjectInfo");
    	panelContainer.add(GamePanel, "Game");
    	
    	// Add the game to the game panel
    	GamePanel.add(game);
		
		// Set the default panel to be shown first
		cardLayout.show(panelContainer, "Home");
		
		// JFrame configurations
		CLayout.add(panelContainer);				
		CLayout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// DISPOSE_ON_CLOSE leaves pending processes
		CLayout.setSize(windowWidth, windowHeight);						// Set JFrame width and height
		CLayout.setLocationRelativeTo(null);							// Make JFrame appear in the centre of monitor
		CLayout.setVisible(true); 
		
		new Sounds(Sounds.introMusicSoundPath);
	}
	
	// main function
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				// Start the application
				new CLayout();
			}
		});
	}

}