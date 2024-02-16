package entity;

import java.awt.Rectangle;

import main.GamePanel;

public class Character extends Entity
{
	GamePanel gp;
	
	private static final long serialVersionUID = 1L;
	
	public int pixelsTravelledPerTick = 2;
	
	public Character(GamePanel gp)
	{
		super(gp);
		this.gp = gp;
	}
	
	public void move(Character character, String direction)
	{
		if(gp.gameState == gp.lifeLostState)
		{
			return;
		}
		
		if(canMove(character, direction))
		{
			shiftCharacterInGivenDirection(character, direction);
		}
		else
		{
			if(direction == "right" || direction == "left")
			{
				if(character.getCurrentDirection() == "up" && canMove(character, "up"))
				{
					shiftCharacterInGivenDirection(character, "up");
				}
				if(character.getCurrentDirection() == "down" && canMove(character, "down"))
				{
					shiftCharacterInGivenDirection(character, "down");
				}
			}
			else if(direction == "up" || direction == "down")
			{
				if(character.getCurrentDirection() == "left" && canMove(character, "left"))
				{
					shiftCharacterInGivenDirection(character, "left");
				}
				if(character.getCurrentDirection() == "right" && canMove(character, "right"))
				{
					shiftCharacterInGivenDirection(character, "right");
				}
			}
		}
	}

	public boolean canMove(Character character, String direction)
	{
		int nextX = 0, nextY = 0;
		
		switch(direction)
		{
			case "right": nextX = character.x + pixelsTravelledPerTick; nextY = character.y; break;
			case "left": nextX = character.x - pixelsTravelledPerTick; nextY = character.y; break;
			case "up": nextX = character.x; nextY = character.y - pixelsTravelledPerTick; break;
			case "down": if(character.x == 320 && character.y == 256) {return false;}
							nextX = character.x; nextY = character.y + pixelsTravelledPerTick; break;
		}
		
		Rectangle bounds = new Rectangle();

		bounds.x = nextX;
		bounds.y = nextY;
		bounds.width = character.width;
		bounds.height = character.height;
		
		for(int xx = 0; xx < gp.wallMatrix.length; xx++)
		{
			for(int yy = 0; yy < gp.wallMatrix[0].length; yy++)
			{
				if(gp.wallMatrix[xx][yy] != null)								
				{
					if(bounds.intersects(gp.wallMatrix[xx][yy]))						
					{
						return false;								
					}
				}
			}
		}
		
		return true;
	}
	
	public void shiftCharacterInGivenDirection(Character character, String direction)
	{
		switch(direction)
		{
			case "right": character.x += pixelsTravelledPerTick; break;
			case "left": character.x -= pixelsTravelledPerTick; break;
			case "up": character.y -= pixelsTravelledPerTick; break;
			case "down": character.y += pixelsTravelledPerTick; break;
		}
		
		character.solidArea.x = character.x + 12;//((gp.tileSize-solidArea.width)/2);
		character.solidArea.y = character.y + 12;//((gp.tileSize-solidArea.height)/2);
	}
	
	public String getCurrentDirection() 
	{
		return "";
	}
}