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
			
			br = new BufferedReader(new FileReader("LeaderboardInfo.txt"));

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
			BufferedWriter bw = new BufferedWriter(new FileWriter("LeaderboardInfo.txt"));
			
			for(int i = 0; i < 5; i++)
			{
				if(leaderboardInfo[i][0] != null && leaderboardInfo[i][1] != null)
				{
					bw.write(leaderboardInfo[i][0] + " ");
					bw.write(leaderboardInfo[i][1] + "\n");
				}
			}
			
			bw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveSettings()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Settings.txt"));
			
			// USERNAME
			bw.write(gp.username);
			
			bw.newLine();
			
			// CONTROLS
			if(gp.wasdControls == true)
			{
				bw.write("WASD");
			}
			if(gp.wasdControls == false)
			{
				bw.write("Arrows");
			}
			
			bw.newLine();
			
			// VOLUME
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			// SPEED
			bw.write(String.valueOf(gp.speed));
			bw.newLine();
			
			bw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadSettings()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("Settings.txt"));

			String s = br.readLine();
			
			// USERNAME
			gp.username = s;
			
			s = br.readLine();
			
			// CONTROLS
			if(s.equals("WASD"))
			{
				gp.wasdControls = true;
			}
			if(s.equals("Arrows"))
			{
				gp.wasdControls = false;
			}
			
			// VOLUME
			s = br.readLine();
			
			gp.se.volumeScale = Integer.parseInt(s);
			
			// SPEED
			s = br.readLine();
			
			gp.speed = Integer.parseInt(s);
			
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
