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

package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
		
		try
		{
			BufferedReader ProjectInfoReader;
			ProjectInfoReader = new BufferedReader(new FileReader("ProjectInfo.txt"));
			String ProjectInfoLine = ProjectInfoReader.readLine();
			
			ProjectInfoReader.close();
			
			JLabel labelDesc = new JLabel(ProjectInfoLine);
			labelDesc.setHorizontalAlignment(SwingConstants.CENTER);
			labelDesc.setBounds(5, 5, 650, 450);
			labelDesc.setFont(new Font("Calibri Light", Font.PLAIN, 18));
			labelDesc.setForeground(Color.BLACK);
			add(labelDesc);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
		
		
	}
}
