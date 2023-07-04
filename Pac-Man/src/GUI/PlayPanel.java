/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: PlayPanel.java
* 
* Description: 
* 
* This file contains the implementation of the Play panel 
* displayed in the application's graphical user interface.
* 
/**************************************************************/

package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Game.Game;

public class PlayPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JTextField usernameTextField;
	
	// Constructor
	public PlayPanel()
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
		
		// Play button configuration
		JButton playButton = new JButton("PLAY PACMAN");
		playButton.setBounds(190, 393, 300, 30);
		add(playButton);
		
		// Back home button configuration
		JButton BackButton = new JButton("Back to Home");
		BackButton.setBounds(190, 500, 300, 30);
		add(BackButton);
		
		// Username text field input configuration
		usernameTextField = new JTextField("Username");
		usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameTextField.setForeground(Color.lightGray);
		usernameTextField.setBounds(190, 350, 300, 30);
		add(usernameTextField);
		
		// Action listeners
		playButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	// Check for valid username
                	if(checkValidUsername())
                	{
                		// Start and show game
                		CLayout.game.startGame();												
                    	CLayout.cardLayout.show(CLayout.panelContainer, "Game");				
                		Game.gameStatus = Game.init;	
                		
                		// Save username
                		LeaderboardPanel.username = usernameTextField.getText();                
                	}
                	else
                	{
                		// Display pop-up window
                		JFrame popupwindow = new JFrame();						
                		
                		// Request user for a valid username
                	    JOptionPane.showMessageDialog(popupwindow, "Insert a valid username!");	
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
		
		usernameTextField.addFocusListener
		(
				new FocusListener() 
				{
					public void focusGained(FocusEvent e) 
					{
						// Make suggestive text disappear on mouse click
						usernameTextField.setText("");	
						
						// Turn user text input black
						usernameTextField.setForeground(Color.black);					
					}

					public void focusLost(FocusEvent e) 
					{
						// Suggestive text in gray
						usernameTextField.setForeground(Color.lightGray);				
					}
				}
		);
	}
	
	// Check for valid username insertion
	private boolean checkValidUsername()
	{
		return usernameTextField.getText().length() != 0 && 
			   usernameTextField.getText() != null;
	}
}
