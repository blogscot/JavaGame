package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.SCALE;
import static com.diamond.iain.javagame.utils.GameConstants.ScreenWidth;
import static com.diamond.iain.javagame.utils.GameConstants.TileWidth;

import java.awt.Graphics;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {
	
	private static int speed = 6;
	public int x = 0, y = 0;
	
	// Scoring data
	int baseValue = 10;
	
	@Override
	public void tick() {
				
		if (speed > 0 && x >= (ScreenWidth * SCALE - TileWidth / SCALE)) {
			// When the end of the row sprite hits the right wall, all the previous sprites
			// have already moved to right, so move the bouncing sprite to the right
			// before changing direction.
			x += speed;
			speed *= -1;
			
		} else if (x <= 0) {
			// When the start of the row sprite hits the left wall it is the first to
			// move so just change direction and adjust position as normal.
			speed *= -1;
			x += speed;
		} else {
			// no walls!
			x += speed;
		}
	}

	@Override
	public void render(Graphics g) {
	}
}
