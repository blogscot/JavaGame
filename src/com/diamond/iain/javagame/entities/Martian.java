package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Martian extends Invader implements Tile {
	
	private BufferedImage alien;

	public Martian(SpriteManager manager, Point p) {
		x = p.x;
		y = p.y;
		this.alien = manager.martian;
	}
	
	
	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, scaledHeight, scaledWidth, null);
	}
}
