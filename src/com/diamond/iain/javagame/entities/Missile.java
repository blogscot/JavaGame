package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.missileYPos;
import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Missile implements Tile {
	
	private final int SPEED = 5;
	private final int xOffset = 25;
	private final int yOffset = 10;
	private final int missileWidth = 6;
	private final int missileHeight = 22;
	
	private int x = 0, y = 0;
	private BufferedImage missile;

	private boolean active = true;
	
	public Missile(SpriteManager manager, int startingX){
		x = startingX;
		y = missileYPos;
		missile = manager.missile;
	}

	@Override
	public void tick() {
		if (y > 0) y -= SPEED;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(missile, x, y, scaledWidth, scaledHeight, null);
	}

	@Override
	public Point getPosition() {
		return new Point(x,y);
	}
	
	@Override
	public Rectangle getBounds(){
		// A missile is quite a bit smaller than a Tile
		return new Rectangle(x+xOffset,y+yOffset, missileWidth, missileHeight);
	}

	@Override
	public boolean isActive(){
		return active;
	}
	
	@Override
	public void destroy() {
		active = false;
	}
}
