package com.diamond.iain.javagame.utils;

public interface GameConstants {

	public static final int ScreenWidth = 600;
	public static final int ScreenHeight = 500;
	public static final int SCALE = 2;

	public static final int TileHeight = 60;
	public static final int TileWidth = 40;
	
	public static final int playerYPos = ScreenHeight * SCALE - 100;
	public static final int missileYPos = playerYPos - TileHeight / 2;
	
	public static final int scaledHeight = TileHeight / SCALE;
	public static final int scaledWidth = TileWidth;
}
