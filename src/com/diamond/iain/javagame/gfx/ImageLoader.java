package com.diamond.iain.javagame.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public BufferedImage load(String path){
		
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			System.out.println("Image Loading failure.");
		}
		return null;
	}
}
