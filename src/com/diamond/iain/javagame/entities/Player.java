package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;
import static com.diamond.iain.javagame.utils.GameConstants.*;

public class Player extends Tile {

	private static final int SPEED = 6;
	private static final int height = TileHeight / SCALE;
	private static final int width = TileWidth * SCALE / SCALE;
	//private int x, y;
	private boolean right = false, left = false;
	private BufferedImage player;
	
	public Player(SpriteManager manager) {
		super(manager);
		this.x = 30;
		this.y = ScreenHeight * SCALE - 100;
		this.player = manager.player;

	}

	@Override
	public void tick() {
		if (left && x >= 0)
			x -= SPEED;
		if (right && x <= ScreenWidth * SCALE - (TileWidth))
			x += SPEED;
	}

	@Override
	public void render(Graphics g) {
			g.drawImage(player, x, y, height, width, null);
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}
