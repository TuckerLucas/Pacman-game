package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
				
				if(username != null && score != null)
				{
					usernameArray[i] = username;
					scoreArray[i] = score;
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
	
	public void reorderLeaderboardInfo()
	{
		int score;
		
		for(int i = 0; i < 5; i++)
		{
			if(scoreArray[i] != null)
			{
				score = Integer.parseInt(scoreArray[i]);
				
				if(gp.score >= score)
				{
					for(int j = 4; j > i; j--)
					{
						scoreArray[j] = scoreArray[j-1];
						usernameArray[j] = usernameArray[j-1];
					}
					
					scoreArray[i] = Integer.toString(gp.score);
					usernameArray[i] = gp.username;
					return;
				}
			}
			else
			{
				scoreArray[i] = Integer.toString(gp.score);
				usernameArray[i] = gp.username;
				return;
			}
		}
	}
	
	public void writeLeaderboardInfo()
	{
		try
		{
			BufferedWriter scoreWriter, usernameWriter = null;
			scoreWriter = new BufferedWriter(new FileWriter("res/Files/Scores.txt"));
			usernameWriter = new BufferedWriter(new FileWriter("res/Files/Usernames.txt"));
			
			for(int i = 0; i < 5; i++)
			{
				if(usernameArray[i] != null && scoreArray[i] != null)
				{
					scoreWriter.write(scoreArray[i] + "\n");
					usernameWriter.write(usernameArray[i] + "\n");
				}
			}
			
			scoreWriter.close();
			usernameWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
