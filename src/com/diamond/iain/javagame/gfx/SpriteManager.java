package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.tiles.Tile;

public class SpriteManager {
	
	public BufferedImage player;
	
	public SpriteManager(SpriteSheet ss) {
		player = ss.crop(0, 0, Tile.HEIGHT, Tile.WIDTH);
		
	}

}
