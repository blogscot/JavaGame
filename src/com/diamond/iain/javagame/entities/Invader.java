package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Invader implements Tile {
	
	private static int speed = 2;
	private BufferedImage alien;
	public int x = 0, y = 0;
	
	public Invader(SpriteManager manager){
		this.x = 30;
		this.y = 50;
		this.alien = manager.alien1;
	}

	@Override
	public void tick() {
		
		// When invader reaches a wall reverse direction
		if (x <= 0 || x >= (ScreenWidth * SCALE - TileWidth) ) {
			speed *= -1;
		}
		x += speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, scaledHeight, scaledWidth, null);
	}
}
