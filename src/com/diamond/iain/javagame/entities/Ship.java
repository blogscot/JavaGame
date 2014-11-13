package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Ship implements Tile {

	private static final int SPEED = 2;
	protected int speed = SPEED;
	protected int x = 0, y = 0;
	protected boolean isActive = true;

	protected BufferedImage ship;
	
	protected int score = 0;
	
	@Override
	public void tick() {
		x += speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ship, x, y, scaledWidth * 2, scaledHeight, null);
	}

	@Override
	public void destroy() {
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public Point getPosition() {
		return new Point(x, y);
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x,y, scaledWidth * 2, scaledHeight);
	}
}
