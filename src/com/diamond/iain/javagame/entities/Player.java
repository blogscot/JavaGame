package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Player extends Tile {

	private final int SPEED = 3;
	//private int x, y;
	private boolean up = false, down = false, right = false, left = false;
	private int height = TILE_HEIGHT / Game.SCALE;
	private int width = TILE_WIDTH * Game.SCALE / Game.SCALE;
	private BufferedImage player;
	
	public Player(SpriteManager manager) {
		super(manager);
		this.x = 0;
		this.y = 0;
		this.player = manager.player;

	}

	@Override
	public void tick() {
		if (up)
			y -= SPEED;
		if (down)
			y += SPEED;
		if (left)
			x -= SPEED;
		if (right)
			x += SPEED;
	}

	@Override
	public void render(Graphics g) {
			g.drawImage(player, x, y, height, width, null);
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}
