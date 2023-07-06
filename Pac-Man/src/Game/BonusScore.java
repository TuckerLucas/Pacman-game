/**************************************************************
* Created by: Lucas Tucker (tucker.lucas.1404@gmail.com)
* 
* File: BonusScore.java
* 
* Description: 
* 
* This file contains the implementation for the bonus scores
* that appear when pacman eats a ghost.
* 
/**************************************************************/

package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BonusScore extends Rectangle
{
	private static final long serialVersionUID = 1L;
	
	// Bonus score animation variables
	public static int animationTime       = 0;
	public static int animationTargetTime = 10;
	public static int nFlashes 			  = 0;
	public static int nTargetFlashes	  = 3;
	public static boolean display  		  = false;
	
	// Constructor 
	public BonusScore(int x, int y)
	{
		setBounds(x,y,32,32);
	}
	
	// Render object
	public void render(Graphics g)
	{
		if(display == true)
		{
			// Check if bonus score has flashed the target amount of times
			if(nFlashes == nTargetFlashes)
			{
				nFlashes = 0;
				display = false;
			}
			else if(nFlashes < nTargetFlashes)
			{
				if(Texture.animationPhaseBonusScore >= Texture.bonusScore.length)
				{
					Texture.animationPhaseBonusScore = 0;
					nFlashes++;
				}
				g.drawImage(Texture.bonusScore[Texture.animationPhaseBonusScore], Game.xEvent, Game.yEvent, width, height, null);
			}
		}
	}
}

