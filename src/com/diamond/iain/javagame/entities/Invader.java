package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Invader extends Tile {
	
	private static int speed = 3;
	private static final int height = TileHeight / SCALE;
	private static final int width = TileWidth;
	private BufferedImage alien;
	
	public Invader(SpriteManager manager){
		super(manager);
		this.x = 30;
		this.y = 50;
		this.alien = manager.alien1;
	}

	@Override
	public void tick() {
		if (x <= 0 || x >= (ScreenWidth * SCALE - TileWidth) ) {
			speed *= -1;
		}
		
		x += speed;
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, height, width, null);
		
	}

}
