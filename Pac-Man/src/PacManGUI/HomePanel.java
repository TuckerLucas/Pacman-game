package PacManGUI;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import javax.swing.JLabel;
import java.awt.Font;

public class HomePanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public static boolean username_inserted = false;
	
	public HomePanel() 
	{
		// Insert PAC-MAN logo image on the home panel
		JLabel labelImage = new JLabel("New label");
		labelImage.setBounds(137, 49, 405, 216);
		ImageIcon imageIcon = new ImageIcon("res\\Pacman\\PacManLogo.png"); 
		labelImage.setIcon(imageIcon);
		add(labelImage);
		
		// Insert author information on the home panel
		JLabel labelID = new JLabel("Created by: Lucas Tucker\n");
		labelID.setHorizontalAlignment(SwingConstants.CENTER);
		labelID.setBounds(190, 250, 300, 30);
		labelID.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		labelID.setForeground(Color.BLACK);
		add(labelID);
		
		// Set home panel's background and layout
		setBackground(new Color(240,240,240));
		setLayout(null);
		
		// Buttons present on the Home panel
		JButton PlayButton 			= new JButton("PLAY");
		JButton LeaderboardButton 	= new JButton("LEADERBOARD");
		JButton SettingsButton 		= new JButton("SETTINGS");
		JButton ProjectInfoButton 	= new JButton("PROJECT INFO");
		JButton ExitButton 			= new JButton("EXIT");
		
		// Button's bounds and placement on the home panel
		PlayButton.setBounds(190, 325, 300, 30);
		LeaderboardButton.setBounds(190, 375, 300, 30);
		SettingsButton.setBounds(190, 425, 300, 30);
		ProjectInfoButton.setBounds(190, 475, 300, 30);
		ExitButton.setBounds(190, 525, 300, 30);
		
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
