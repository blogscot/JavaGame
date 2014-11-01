package com.diamond.iain.javagame.tiles;

import java.awt.Graphics;

import com.diamond.iain.javagame.gfx.SpriteManager;

public interface Tile {
	
	public SpriteManager manager = null;

	public void tick();
	public void render(Graphics g);
}
