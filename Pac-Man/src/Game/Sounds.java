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
	private Clip clip;
	
	public static boolean soundOn = false;
	
	// Paths to sound clips
	public static String pacmanEatingSoundPath = "res/Sounds/PacmanEating.wav";
	public static String energizerSoundPath    = "res/Sounds/Energizer.wav";
	public static String pacmanDeathSoundPath  = "res/Sounds/PacmanDeath.wav";
	public static String ghostEatenSoundPath   = "res/Sounds/GhostEaten.wav";
	
	// Constructor
	public Sounds(String sound)
	{
		// Check if sound is on
		if(soundOn == false)
		{
			return;
		}
		
		try 
		{
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
}

