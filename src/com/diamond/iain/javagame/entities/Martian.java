package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

import static com.diamond.iain.javagame.utils.GameConstants.*;

public class Martian extends Invader implements Tile {
	
	private BufferedImage alien;

	public Martian(SpriteManager manager, int x, int y) {
		this.x = x;
		this.y = y;
		this.alien = manager.martian;
	}
	
	
	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, scaledHeight, scaledWidth, null);
	}

}
