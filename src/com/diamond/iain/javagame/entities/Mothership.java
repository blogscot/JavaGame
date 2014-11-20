package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.playerYPos;
import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Tile;

public class Mothership extends Ship implements Tile, CanFire {

	Player player = Game.getPlayer();
	private static final double BossScalingFactor = 1.4;
	protected static final int BossWidth = (int)(scaledWidth * 2 * BossScalingFactor);
	protected static final int BossHeight = (int)(scaledHeight * BossScalingFactor);
	
	public Mothership(SpriteManager manager) {
		p = new Point(30, 50);
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.ship = manager.mothership;
		speed = 4;
		startLives = 3;
		lives = startLives;
		active = false;

		score = 400;
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(ship, x, y, BossWidth, BossHeight, null);
	}
		
	public void reverseDirection() {
		speed *= -1;
	}
	
	public void moveDown() {
		y += height * 2;
	}
	

	@Override
	public int getWidth() {
		return BossWidth;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, BossWidth, BossHeight);
	}
	
	/**
	 * 
	 * @return true when the invader reaches the bottom of the screen
	 */
	public boolean reachedPlayer() {
		// true when bottom of invader is 'lower than' top of player,
		// as it appear on-screen
		return y + height >= playerYPos;
	}
}
