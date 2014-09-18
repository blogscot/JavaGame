package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int col, int row, int width, int height){
		return sheet.getSubimage(col*72, row*20, width, height);
	}
}
