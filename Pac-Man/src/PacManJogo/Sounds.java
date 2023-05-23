package PacManJogo;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds
{
	public static Clip clip;
	
	public Sounds(String sound)
	{
		try 
		{
			File path = new File(sound);
			clip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(path);
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

