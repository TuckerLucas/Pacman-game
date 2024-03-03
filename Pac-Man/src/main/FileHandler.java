package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler 
{
	GamePanel gp;
	
	String leaderboardInfo[][] = new String[5][2];
	
	public FileHandler(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void readLeaderboardInfo()
	{
		try
		{
			BufferedReader br;
			String[] temp;
			String line;
			
			br = new BufferedReader(new FileReader("res/Files/LeaderboardInfo.txt"));

			for(int i = 0; i < 5; i++)
			{
				line = br.readLine();
				
				if(line != null)
				{
					temp = line.split(" ");
					
					if(temp.length != 0)
					{
						leaderboardInfo[i][0] = (String)temp[0];
						leaderboardInfo[i][1] = (String)temp[1];
					}
				}
			}
			
			br.close();
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
			if(leaderboardInfo[i][1] != null)
			{
				score = Integer.parseInt(leaderboardInfo[i][1]);
				
				if(gp.score > score)
				{
					for(int j = 4; j > i; j--)
					{
						leaderboardInfo[j][0] = leaderboardInfo[j-1][0];
						leaderboardInfo[j][1] = leaderboardInfo[j-1][1];
					}
					
					leaderboardInfo[i][0] = gp.username;
					leaderboardInfo[i][1] = Integer.toString(gp.score);
					return;
				}
			}
			else
			{
				leaderboardInfo[i][0] = gp.username;
				leaderboardInfo[i][1] = Integer.toString(gp.score);
				return;
			}
		}
	}
	
	public void writeLeaderboardInfo()
	{
		try
		{
			BufferedWriter lbWriter;
			lbWriter = new BufferedWriter(new FileWriter("res/Files/LeaderboardInfo.txt"));
			
			for(int i = 0; i < 5; i++)
			{
				if(leaderboardInfo[i][0] != null && leaderboardInfo[i][1] != null)
				{
					lbWriter.write(leaderboardInfo[i][0] + " ");
					lbWriter.write(leaderboardInfo[i][1] + "\n");
				}
			}
			
			lbWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
