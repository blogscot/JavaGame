package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;

import static com.diamond.iain.javagame.utils.GameConstants.*;

public class SpriteManager {
	
	public BufferedImage alien1;
	public BufferedImage alien2;
	public BufferedImage alien3;
	public BufferedImage alien4;
	public BufferedImage player;
	public BufferedImage missile;
	
	public SpriteManager(SpriteSheet ss) {
		
		alien1 = ss.crop(0, 0, TileHeight, TileWidth);
		alien2 = ss.crop(0, 1, TileHeight, TileWidth);
		alien3 = ss.crop(1, 0, TileHeight, TileWidth);
		alien4 = ss.crop(1, 1, TileHeight, TileWidth);
		player = ss.crop(2, 0, TileHeight, TileWidth);
		missile = ss.crop(2, 1, TileHeight, TileWidth);
	}

}
