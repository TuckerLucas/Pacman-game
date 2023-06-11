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
	
	// Constructor
	public Sounds(String sound)
	{
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

