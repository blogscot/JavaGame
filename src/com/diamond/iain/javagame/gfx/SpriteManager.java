package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.tiles.Tile;

public class SpriteManager {
	
	public BufferedImage alien1;
	public BufferedImage alien2;
	public BufferedImage alien3;
	public BufferedImage alien4;
	public BufferedImage player;
	
	public SpriteManager(SpriteSheet ss) {
		
		alien1 = ss.crop(0, 0, Tile.HEIGHT, Tile.WIDTH);
		alien2 = ss.crop(0, 1, Tile.HEIGHT, Tile.WIDTH);
		alien3 = ss.crop(1, 0, Tile.HEIGHT, Tile.WIDTH);
		alien4 = ss.crop(1, 1, Tile.HEIGHT, Tile.WIDTH);
		player = ss.crop(2, 0, Tile.HEIGHT, Tile.WIDTH);
		
	}

}
