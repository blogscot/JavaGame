package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {

	private static final int SPEED = 2;    // the start speed
	private static int speed = SPEED;      // speed also controls direction
	protected int x = 0, y = 0;
	protected boolean isActive = true;

	protected BufferedImage alien;

	protected int score = 0;

	@Override
	public void tick() {
		x += speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(alien, x, y, scaledWidth, scaledHeight, null);
	}

	public void reverseDirection() {
		speed *= -1;
	}

	// Let's begin at the start
	public static void restartGame() {
		speed = SPEED;
	}

	/**
	 * Make the invaders faster
	 */
	public static void levelUp() {
		// speed can be negative so be wary when adding
		speed = Math.abs(speed) + 1;
	}

	// the invaders are approaching
	public void moveDown() {
		y += TileHeight;
	}

	public int getScore() {
		return score;
	}

	/*
	 *  returns the current invader's position
	 */
	public Point getPosition() {
		return new Point(x, y);
	}

	/**
	 * 
	 * @return  true when the invader reaches the player's position
	 */
	public boolean reachedPlayer() {
		return y >= playerYPos;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, scaledWidth,	scaledHeight);
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void destroy() {
		isActive = false;
	}
}
