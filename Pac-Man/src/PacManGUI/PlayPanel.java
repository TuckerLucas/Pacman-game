package PacManGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import PacManJogo.Game;

public class PlayPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static boolean username_inserted = false;
	private JTextField textField;
	
	public PlayPanel()
	{
		// Set play panel's background and layout
		setBackground(new Color(240,240,240));
		setLayout(null);
		
		// Play button configuration
		JButton playButton = new JButton("LET'S PLAY PAC-MAN!");
		playButton.setBounds(190, 393, 300, 30);
		add(playButton);
		
		// Back home button configuration
		JButton BackButton = new JButton("Back to Home");
		BackButton.setBounds(190, 500, 300, 30);
		add(BackButton);
		
		// Username text field input configuration
		textField = new JTextField("Username");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setBounds(190, 350, 300, 30);
		add(textField);
		
		// Action listeners
		playButton.addActionListener  
        (  
            new ActionListener() 
            {  
                public void actionPerformed(ActionEvent e) 
                {  
                	// Valid username?
                	if(textField.getText().length() != 0 && textField.getText() != null)
                	{
                		CLayout.game.start();													// Start the game
                    	CLayout.cardLayout.show(CLayout.panelContainer, "Game");				// Show game panel
                		Game.GAME_STATUS = Game.INIT;											// Start on the inital screen
                		LeaderboardPanel.username = textField.getText();                		// Save player's username
                	}
                	else
                	{
                		JFrame popupwindow = new JFrame();										// Display popup window
                	    JOptionPane.showMessageDialog(popupwindow, "Insert valid username");	// requesting valid username
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
		
		textField.addFocusListener
		(
				new FocusListener() 
				{
					public void focusGained(FocusEvent e) 
					{
						textField.setText("");									// Make suggestive text disappear on mouse click
						textField.setForeground(Color.BLACK);					// Turn user text input black
					}

					public void focusLost(FocusEvent e) 
					{
						textField.setForeground(Color.LIGHT_GRAY);				// Suggestive text in gray
					}
				}
		);
	}
}
