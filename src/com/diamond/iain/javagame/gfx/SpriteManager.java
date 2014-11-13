package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;

import static com.diamond.iain.javagame.utils.GameConstants.*;

public class SpriteManager {
	
	public BufferedImage martian;
	public BufferedImage venusian;
	public BufferedImage plutonian;
	public BufferedImage mercurian;
	public BufferedImage player;
	public BufferedImage playerMissile;
	public BufferedImage invaderMissile;
	public BufferedImage destroyer;
	public BufferedImage mothership;
	
	public SpriteManager(SpriteSheet ss) {
		
		martian        = ss.crop(0, 0, TileWidth, TileHeight);
		plutonian      = ss.crop(1, 0, TileWidth, TileHeight);
		player         = ss.crop(2, 0, TileWidth, TileHeight);
		
		venusian       = ss.crop(0, 1, TileWidth, TileHeight);
		mercurian      = ss.crop(1, 1, TileWidth, TileHeight);
		playerMissile  = ss.crop(2, 1, TileWidth, TileHeight);
		
		destroyer      = ss.crop(0, 2, TileWidth * 2, TileHeight);
		invaderMissile = ss.crop(2, 2, TileWidth, TileHeight);

		mothership     = ss.crop(0, 3, TileWidth * 2, TileHeight);
	}
}
