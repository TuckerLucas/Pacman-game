/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: HomePanel.java
* 
* Description: 
* 
* This file contains the implementation of the Home panel 
* displayed in the application's graphical user interface.
* 
/**************************************************************/

package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class HomePanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	// Pacman logo variables
	public static String pacmanLogoPath = "res/Images/PacManLogo.png";
	public static int logoWidth = 405;
	public static int logoHeight = 216;
	public static int logoHorizontalPos = 137;
	public static int logoVerticalPos = 49;
	
	// Home panel components dimensions 
	public static int componentWidth = 300;
	public static int componentHeight = 30;
	
	// Home panel components positioning
	public static int componentHorizontalPos = 190;
	public static int authorInforVerticalPos = 250;
	private int playButtonVerticalPos = 325;
	private int lbButtonVerticalPos = 375;
	private int settingsButtonVerticalPos = 425;
	private int projectInfoButtonVerticalPos = 475;
	private int exitButtonVerticalPos = 525;
	
	// Constructor
	public HomePanel() 
	{
		// Insert pacman logo image on the home panel
		JLabel labelImage = new JLabel("New label");
		labelImage.setBounds(logoHorizontalPos, logoVerticalPos, logoWidth, logoHeight);
		ImageIcon pacmanLogoImageIcon = new ImageIcon(pacmanLogoPath); 
		labelImage.setIcon(pacmanLogoImageIcon);
		add(labelImage);
		
		// Insert author information on the home panel
		JLabel labelID = new JLabel("Created by: Lucas Tucker\n");
		labelID.setHorizontalAlignment(SwingConstants.CENTER);
		labelID.setBounds(componentHorizontalPos, authorInforVerticalPos, componentWidth, componentHeight);
		labelID.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		labelID.setForeground(Color.black);
		add(labelID);
		
		// Set home panel's background and layout
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		// Buttons present on the Home panel
		JButton PlayButton = new JButton("PLAY");
		JButton LeaderboardButton = new JButton("LEADERBOARD");
		JButton SettingsButton = new JButton("SETTINGS");
		JButton ProjectInfoButton = new JButton("PROJECT INFO");
		JButton ExitButton = new JButton("EXIT");
		
		// Button's bounds and placement on the home panel
		PlayButton.setBounds(componentHorizontalPos, playButtonVerticalPos, componentWidth, componentHeight);
		LeaderboardButton.setBounds(componentHorizontalPos, lbButtonVerticalPos, componentWidth, componentHeight);
		SettingsButton.setBounds(componentHorizontalPos, settingsButtonVerticalPos, componentWidth, componentHeight);
		ProjectInfoButton.setBounds(componentHorizontalPos, projectInfoButtonVerticalPos, componentWidth, componentHeight);
		ExitButton.setBounds(componentHorizontalPos, exitButtonVerticalPos, componentWidth, componentHeight);
		
		// Add the buttons to the home panel
		add(PlayButton);
		add(LeaderboardButton);
		add(SettingsButton);
		add(ProjectInfoButton);
		add(ExitButton);
		
		// Button's action listeners
		PlayButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	CLayout.cardLayout.show(CLayout.panelContainer, "Play");
                }  
            }  
        ); 
		
		LeaderboardButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	CLayout.cardLayout.show(CLayout.panelContainer, "Leaderboard");                	
                }  
            }  
        );
		
		SettingsButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			CLayout.cardLayout.show(CLayout.panelContainer, "Settings");
        		}
        		
        	}
        ); 
		
		ProjectInfoButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	CLayout.cardLayout.show(CLayout.panelContainer, "ProjectInfo");                	
                }  
            }  
        );
		
		ExitButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	System.exit(0);
                }  
            }  
        );
	}
}
