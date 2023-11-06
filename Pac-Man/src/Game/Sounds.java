/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: Sounds.java
* 
* Description: 
* 
* This file contains the implementation for the game sounds.
* 
/**************************************************************/

package Game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds
{
	public static Clip clip;
	
	public static boolean soundIsOn = false;
	
	// Paths to sound clips
	public static String pacmanEatingSoundPath = "res/Sounds/PacmanEating.wav";
	public static String eatenEnergizerSoundPath = "res/Sounds/Energizer.wav";
	public static String pacmanDeathSoundPath  = "res/Sounds/PacmanDeath.wav";
	public static String ghostEatenSoundPath   = "res/Sounds/GhostEaten.wav";
	public static String introMusicSoundPath   = "res/Sounds/IntroMusic.wav";
	public static String sirenSoundPath        = "res/Sounds/Siren.wav";
	
	public static Sounds soundEffect;
	
	// Constructor
	public Sounds(String sound)
	{
		
	}
	

	
	public static void playSoundEffect(String sound)
	{
		try 
		{
			if(!soundIsOn)
			{
				return;
			}
			
			File path = new File(sound);
			AudioInputStream audio = AudioSystem.getAudioInputStream(path);
			
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
			clip.setFramePosition(0);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	
	}
	
	public static void loop(String sound)
	{	
		try 
		{
			if(!soundIsOn)
			{
				return;
			}
			
			File path = new File(sound);
			AudioInputStream audio = AudioSystem.getAudioInputStream(path);
			
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	
	}
}

