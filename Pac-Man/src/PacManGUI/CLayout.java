/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: CLayout.java
* 
* Description: 
* 
* This file sets the layout to be used by the application's 
* graphical user interface and declares all the panels to be 
* used in the layout. This class contains the main function
* 
/**************************************************************/

package PacManGUI;

import java.awt.*;
import javax.swing.*;

import PacManJogo.Game;

public class CLayout
{	
	// Card layout JFrame to include container
	JFrame CLayout = new JFrame("PAC-MAN Java Game");
	
	// Container to include JPanels
	public static JPanel panelContainer = new JPanel();
	
	// JPanels supported by the application
	HomePanel panelHome = new HomePanel();
	PlayPanel panelPlay = new PlayPanel();
	LeaderboardPanel panelLeaderboard = new LeaderboardPanel();
	SettingsPanel panelSettings = new SettingsPanel();
	ProjectInfoPanel panelProjectInfo = new ProjectInfoPanel();
	
	static Game game = new Game();	
	
	public static CardLayout cardLayout = new CardLayout();

	public CLayout() 
	{
		// Set the container's layout
		panelContainer.setLayout(cardLayout);
		
		// Add panels to the container
		panelContainer.add(panelHome, "Home");
		panelContainer.add(panelPlay, "Play");
		panelContainer.add(panelLeaderboard, "Leaderboard");
		panelContainer.add(panelSettings, "Settings");
		panelContainer.add(panelProjectInfo, "ProjectInfo");
		
		// PAC-MAN game configuration
    	game.setBounds(0, 0, 672, 800);
    	JPanel GamePanel = new JPanel();
    	GamePanel.add(game);
    	panelContainer.add(GamePanel, "Game");
		
		// Set the default panel to be shown first
		cardLayout.show(panelContainer, "Home");
		
		// JFrame configurations
		CLayout.add(panelContainer);				
		CLayout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// DISPOSE_ON_CLOSE leaves pending processes
		CLayout.setSize(680, 816);										// Set JFrame width and height
		CLayout.setLocationRelativeTo(null);							// Make JFrame appear in the centre of monitor
		CLayout.setVisible(true); 
	}
	
	// main function
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				new CLayout();
			}
		});
	}

}