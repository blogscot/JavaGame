package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.SCALE;
import static com.diamond.iain.javagame.utils.GameConstants.ScreenWidth;
import static com.diamond.iain.javagame.utils.GameConstants.TileWidth;

import java.awt.Graphics;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {
	
	private static int speed = 2;
	public int x = 0, y = 0;
	
	// Scoring data
	int baseValue = 10;
	
	@Override
	public void tick() {
		
		/*
		 * I noticed that with 2 aliens, A and B, because they are drawn individually,
		 * when they are moving towards the right wall A is incremented first with a 
		 * +ve value. However if B has reached the wall its speed is reversed and a 
		 * -ve value is applied. During the next loop they both move to the left, but
		 * A and B have now moved closer together! 
		 */
		
		// When invader reaches a wall reverse direction
		if (x <= 0 || x >= (ScreenWidth * SCALE - TileWidth) ) {
			speed *= -1;
		}
		x += speed;
	}

	@Override
	public void render(Graphics g) {
	}
}
