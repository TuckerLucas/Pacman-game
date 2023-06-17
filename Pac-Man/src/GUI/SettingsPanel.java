/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: SettingsPanel.java
* 
* Description: 
* 
* This file contains the implementation of the Settings panel 
* displayed in the application's graphical user interface.
* 
/**************************************************************/

package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Game.Pacman;

public class SettingsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	// Constructor
	public SettingsPanel()
	{
		// Insert pacman logo image on the home panel
		JLabel labelImage = new JLabel("Pacman Logo");
		labelImage.setBounds(HomePanel.logoHorizontalPos, HomePanel.logoVerticalPos, HomePanel.logoWidth, HomePanel.logoHeight);
		ImageIcon pacmanLogoImageIcon = new ImageIcon(HomePanel.pacmanLogoPath); 
		labelImage.setIcon(pacmanLogoImageIcon);
		add(labelImage);
		
		// Insert author information on the home panel
		JLabel labelID = new JLabel("Created by: Lucas Tucker\n");
		labelID.setHorizontalAlignment(SwingConstants.CENTER);
		labelID.setBounds(HomePanel.componentHorizontalPos, HomePanel.authorInforVerticalPos, HomePanel.componentWidth, HomePanel.componentHeight);
		labelID.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		labelID.setForeground(Color.black);
		add(labelID);
		
		// Set play panel's background and layout
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		// Game difficulty button configuration
		JButton DifficultyButton = new JButton("DIFFICULTY: EASY");
		DifficultyButton.setBounds(190, 345, 300, 30);
		add(DifficultyButton);
				
		// Sound button configuration
		JButton SoundButton = new JButton("GAME SOUNDS: OFF");
		SoundButton.setBounds(190, 393, 300, 30);
		add(SoundButton);
		
		// Back home button configuration
		JButton BackButton = new JButton("Back to Home");
		BackButton.setBounds(190, 500, 300, 30);
		add(BackButton);
		
		// Action listeners
		SoundButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			if(Pacman.soundOn)
        			{
        				Pacman.soundOn = false;
        				SoundButton.setText("GAME SOUNDS: OFF");
        			}
        			else if(!Pacman.soundOn)
        			{	
        				Pacman.soundOn = true;
        				SoundButton.setText("GAME SOUNDS: ON ");
        			}
        		}
        		
        	}
        ); 
		
		DifficultyButton.addActionListener
        (
        	new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent e) 
        		{
        			switch(Game.Game.difficulty)
        			{
        				case 1: Game.Game.difficulty = 2;
        						DifficultyButton.setText("DIFFICULTY: MEDIUM");
        						break;
        				
        				case 2: Game.Game.difficulty = 3;
        						DifficultyButton.setText("DIFFICULTY: HARD");
        						break;
        				
        				case 3: Game.Game.difficulty = 4;
        						DifficultyButton.setText("DIFFICULTY: CRUSHING");
        						break;
        					
        				case 4: Game.Game.difficulty = 1;
        						DifficultyButton.setText("DIFFICULTY: EASY");
        						break;
        			}
        		}
        	}
        );
		
		BackButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	CLayout.cardLayout.show(CLayout.panelContainer, "Home");
                }  
            }  
        );
	}
}
