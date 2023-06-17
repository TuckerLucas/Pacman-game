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

import javax.swing.JButton;
import javax.swing.JPanel;

public class HowToPlayPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	// Constructor
	public HowToPlayPanel()
	{
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