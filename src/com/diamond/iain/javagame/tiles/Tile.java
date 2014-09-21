package com.diamond.iain.javagame.tiles;

import java.awt.Graphics;

import com.diamond.iain.javagame.gfx.SpriteManager;

public abstract class Tile {
	
	public static final int HEIGHT = 36;
	public static final int WIDTH = 20;
	
	protected SpriteManager manager;
	protected int x, y;

	public Tile(SpriteManager manager) {
		this.manager = manager;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
