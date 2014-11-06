package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {
	
	private static int speed = 2;
	protected int x = 0, y = 0;
	protected boolean isActive = true;
	
	protected BufferedImage alien;
	
	private final int xOffset = 10;
	private final int yOffset = 5;
	private final int missileWidth = TileWidth-20;
	private final int missileHeight = TileHeight-10;
	
	// Scoring data
	int baseValue = 10;
	
	@Override
	public void tick() {
		x += speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, scaledWidth, scaledHeight, null);
	}
	
	public void reverseDirection(){
		speed *= -1;
	}
	
	public void moveDown(){
		y += TileHeight;
	}

	public Point getPosition() {
		return new Point(x,y);
	}
	
	// Make an invader a bit smaller to enforce accuracy in game play
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x+xOffset,y+yOffset, missileWidth, missileHeight);
	}

	@Override
	public boolean isActive(){
		return isActive;
	}

	@Override
	public void destroy(){
		isActive = false;
	}
}
