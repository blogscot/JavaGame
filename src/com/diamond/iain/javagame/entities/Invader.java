package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.SCALE;
import static com.diamond.iain.javagame.utils.GameConstants.ScreenWidth;
import static com.diamond.iain.javagame.utils.GameConstants.TileWidth;

import java.awt.Graphics;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {
	
	private static int speed = 2;
	public int x = 0, y = 0;
	
	public Invader(){}

	@Override
	public void tick() {
		
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
