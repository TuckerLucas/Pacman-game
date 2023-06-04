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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import Game.Pacman;

public class SettingsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public SettingsPanel()
	{
		// Set play panel's background and layout
		setBackground(new Color(240,240,240));
		setLayout(null);
		
		// Sound button configuration
		JButton SoundButton = new JButton("GAME SOUNDS: OFF");
		SoundButton.setBounds(190, 200, 300, 30);
		add(SoundButton);
		
		// Game difficulty button configuration
		JButton DifficultyButton = new JButton("DIFFICULTY: EASY");
		DifficultyButton.setBounds(190, 275, 300, 30);
		add(DifficultyButton);
		
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
