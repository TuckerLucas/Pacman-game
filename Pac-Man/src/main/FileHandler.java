package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler 
{
	GamePanel gp;
	
	public String scoreArray[] = new String[5];
	public String usernameArray[] = new String[5];
	
	public FileHandler(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void readLeaderboardInfo()
	{
		try
		{
			BufferedReader scoreReader, usernameReader;
			scoreReader = new BufferedReader(new FileReader("res/Files/Scores.txt"));
			usernameReader = new BufferedReader(new FileReader("res/Files/Usernames.txt"));
			
			String username;
			String score;
			
			for(int i = 0; i < 5; i++)
			{
				username = usernameReader.readLine();
				score = scoreReader.readLine();
				
				usernameArray[i] = username;
				scoreArray[i] = score;
			}
			
	        scoreReader.close();
	        usernameReader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	public void swap_values()
	{
		for(int i = 0; i < N_SUPPORTED_USERNAMES; i++)
		{
			scoreValue = (Integer) scoreVector.get(i);
			usernameValue = (String) usernameVector.get(i);
			
			if(gp.score >= scoreValue)
			{
				for(int j = N_SUPPORTED_USERNAMES-1; j > i; j--)
				{
					scoreVector.set(j, scoreVector.get(j-1));
					usernameVector.set(j, usernameVector.get(j-1));
				}
				scoreVector.set(i, gp.score);
				//usernameVector.set(i, LeaderboardPanel.username);
				i = N_SUPPORTED_USERNAMES;
			}
		}
	}

	public static void write_to_file()
	{
		try
		{
			BufferedWriter scoreWriter, usernameWriter = null;
			scoreWriter = new BufferedWriter(new FileWriter("res/Files/Scores.txt"));
			usernameWriter = new BufferedWriter(new FileWriter("res/Files/Usernames.txt"));
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
	}*/
}
