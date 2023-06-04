/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: ProjectInfoPanel.java
* 
* Description: 
* 
* This file contains the implementation of the Project Info 
* panel displayed in the application's graphical user interface.
* 
/**************************************************************/

package PacManGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ProjectInfoPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public ProjectInfoPanel()
	{
		// Set project info panel's background and layout
		setBackground(new Color(240,240,240));
		setLayout(null);
		
		JButton BackButton = new JButton("Back to Home");
		BackButton.setBounds(190, 500, 300, 30);
		add(BackButton);
		
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
		
		setBackground(new Color(240,240,240));
		setLayout(null);
		
		/*
		JLabel labelID = new JLabel("<html><b>Author:</b> Lucas Tucker\n");
		labelID.setHorizontalAlignment(SwingConstants.CENTER);
		labelID.setBounds(190, 100, 300, 30);
		labelID.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		labelID.setForeground(Color.BLACK);
		add(labelID);
		*/
		
		JLabel labelDesc = new JLabel("<html>This project was initially developed in an academic context as part of a curricular "
				+ "unit<br> belonging to the author's Masters degree course. The main objective was the<br> development of a stand-alone "
				+ "application with a user interface which, among other <br>functionalities, required the inclusion of a game. The selected"
				+ " game for this project was <br>PAC-MAN, which was coded in Java in its entirety with use of the Eclipse IDE.<br/><br> "
				+ "The initial development of the project was based on an already existent game present<br> online (link below)."
				+ "<br/><br/>YouTube channel: https://www.youtube.com/@JavaGameDevelopment<br/>"
				+ "<br/><br/> The mentioned game (used as a base) was significantly improved upon by adding (among <br>other things) a User Interface,"
				+ " animations and an Artificial Intelligence system for the ghosts within the game. If you wish to consult the source"
				+ " code as well as the written<br> documentation of the project please head to the GitHub project. <br><br><b>GitHub project:</b> https://github.com/TuckerLucas<br/><br/>"
				+ "</html>");
		labelDesc.setHorizontalAlignment(SwingConstants.CENTER);
		labelDesc.setBounds(5, 5, 650, 450);
		labelDesc.setFont(new Font("Calibri Light", Font.PLAIN, 18));
		labelDesc.setForeground(Color.BLACK);
		add(labelDesc);
	}
}
