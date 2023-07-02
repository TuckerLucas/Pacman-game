/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: HowToPlayPanel.java
* 
* Description: 
* 
* This file contains the implementation of the HowToPlay panel 
* displayed in the application's graphical user interface.
* 
/**************************************************************/

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HowToPlayPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static String pacmanLogoPath = "res/Images/dirKeys.png";
	public static int logoWidth = 405;
	public static int logoHeight = 216;
	public static int logoHorizontalPos = 137;
	public static int logoVerticalPos = 49;
	
	// Constructor
	public HowToPlayPanel()
	{
		System.setProperty("sun.java2d.uiScale", "1.0");
		// Insert pacman logo image on the home panel
		JLabel labelImage = new JLabel("Pacman Logo");
		labelImage.setBounds(logoHorizontalPos, logoVerticalPos, logoWidth, logoHeight);
		ImageIcon pacmanLogoImageIcon = new ImageIcon(pacmanLogoPath); 
		labelImage.setIcon(pacmanLogoImageIcon);
		add(labelImage);
		
		// Back home button configuration
		JButton BackButton = new JButton("Back to Home");
		BackButton.setBounds(190, 500, 300, 30);
		add(BackButton);
		
		// Button action listeners
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