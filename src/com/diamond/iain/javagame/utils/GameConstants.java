package com.diamond.iain.javagame.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameConstants {

	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static final int ScreenWidth = (int)screenSize.getWidth();
	public static int ScreenHeight = (int)screenSize.getHeight();

	public static final int TileHeight = 40;
	public static final int TileWidth = 60;

	public static final int scaledHeight = (int)(TileHeight / 1.5);
	public static final int scaledWidth = (int)(TileWidth / 1.5);

	public static final int LeftWall = 0;
	public static final int RightWall = ScreenWidth - scaledWidth;
	
	public static final int playerYPos = ScreenHeight - 80;
	public static final int missileYPos = playerYPos - scaledHeight;
	

}
