package com.diamond.iain.javagame.utils;

public class GameConstants {

	public static final int ScreenWidth = 600;
	private static int MacScreenHeight = 360;
	private static int PCScreenHeight = 500;
	public static final int SCALE = 2;

	public static final int TileHeight = 40;
	public static final int TileWidth = 60;

	public static final int LeftWall = 0;
	public static final int RightWall = ScreenWidth * SCALE - TileWidth / SCALE;
	
	public static final int playerYPos = getScreenHeight() * SCALE - 100;
	public static final int missileYPos = playerYPos - TileHeight / SCALE;
	
	public static final int scaledHeight = TileHeight / SCALE;
	public static final int scaledWidth = TileWidth / SCALE;

	public static int getScreenHeight(){
		if (OSValidator.isMac()){
			return MacScreenHeight;
		}
		return PCScreenHeight;
	}
}
