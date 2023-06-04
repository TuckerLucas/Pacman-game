/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: LeaderboardPanel.java
* 
* Description: 
* 
* This file contains the implementation of the Leaderboard 
* panel displayed in the application's graphical user interface.
* 
/**************************************************************/

package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Game.Game;

public class LeaderboardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	static int value;
	static String value1;
	public static int N_SUPPORTED_USERNAMES = 50;
	public static String username;
	
	public static Vector<Integer> scoreVector = new Vector<Integer>();
	public static Vector<String> usernameVector = new Vector<String>();
	
	static int scoreValue;
	public static String usernameValue;
	
	public static DefaultTableModel LeaderboardData;
	
	public static void read_from_file()
	{
		try
		{
			BufferedReader scoreReader, usernameReader;
			scoreReader = new BufferedReader(new FileReader("Scores.txt"));
			usernameReader = new BufferedReader(new FileReader("Usernames.txt"));
			String scoreLine = scoreReader.readLine();
			String usernameLine = usernameReader.readLine();
			
			int integer = Integer.parseInt(scoreLine);
			
			for(int i = 0; i< N_SUPPORTED_USERNAMES; i++)
			{
				usernameVector.add(usernameLine);
				usernameLine = usernameReader.readLine();
			}
			
			for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
			{
				scoreVector.add(integer);
				
				scoreLine = scoreReader.readLine();
				
				if(scoreLine !=  null)
				{
					integer = Integer.parseInt(scoreLine);
				}
				
			}
	        scoreReader.close();
	        usernameReader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void swap_values()
	{
		for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
		{
			scoreValue = (Integer) scoreVector.get(i);
			usernameValue = (String) usernameVector.get(i);
			
			if(Game.score >= scoreValue)
			{
				for(int j = N_SUPPORTED_USERNAMES-1; j > i; j--)
				{
					scoreVector.set(j, scoreVector.get(j-1));
					usernameVector.set(j, usernameVector.get(j-1));
				}
				scoreVector.set(i, Game.score);
				usernameVector.set(i, LeaderboardPanel.username);
				i = N_SUPPORTED_USERNAMES;
			}
		}
	}

	public static void write_to_file()
	{
		try
		{
			BufferedWriter scoreWriter, usernameWriter = null;
			scoreWriter = new BufferedWriter(new FileWriter("Scores.txt"));
			usernameWriter = new BufferedWriter(new FileWriter("Usernames.txt"));
			String string;
			
			for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
			{
				string = String.valueOf(scoreVector.get(i));
				scoreWriter.write(string + "\n");
				usernameWriter.write(usernameVector.get(i) + "\n");
			}
			
			scoreWriter.close();
			usernameWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static DefaultTableModel update_table()
	{
		String header[] = {"Rank", "Player", "Score"};
		
		String information[][] = new String[N_SUPPORTED_USERNAMES][3];
		
		for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
		{
			information[i][0] = String.valueOf(i+1);
    		information[i][1] = usernameVector.get(i).toString();
    		information[i][2] = scoreVector.get(i).toString();
		}
		
		LeaderboardData = new DefaultTableModel(information, header);
		
		return LeaderboardData;
	}
	
	public LeaderboardPanel()
	{	
		read_from_file();
		
		String information[][] = new String[N_SUPPORTED_USERNAMES][3];
		
		for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
		{
			information[i][0] = String.valueOf(i+1);
    		information[i][1] = usernameVector.get(i).toString();
    		information[i][2] = scoreVector.get(i).toString();
		}
		
		LeaderboardData = update_table();
		JTable table = new JTable(LeaderboardData); 
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(450,700));
		add(scrollPane);
		
		table.setModel(LeaderboardData);
		
		JButton BackButton = new JButton("Back to Home");
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
	}
}

