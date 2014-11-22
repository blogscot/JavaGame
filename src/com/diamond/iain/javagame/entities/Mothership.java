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
	private static final Point StartPosition = new Point(30, 50);

	private int distanceToPlayer = playerYPos - StartPosition.y;
	private int numberofSpeedUps = 4;
	private int speedUpDistance = distanceToPlayer / numberofSpeedUps;

	private final int SPEED1 = 4;
	private final int SPEED2 = 5;
	private final int SPEED3 = 6;
	private final int SPEED4 = 7;
	private final int SPEEDUP1 = StartPosition.y + speedUpDistance;
	private final int SPEEDUP2 = StartPosition.y + speedUpDistance * 2;
	private final int SPEEDUP3 = StartPosition.y + speedUpDistance * 3;

	private static final double BossScalingFactor = 1.4;
	protected static final int BossWidth = (int) (scaledWidth * 2 * BossScalingFactor);
	protected static final int BossHeight = (int) (scaledHeight * BossScalingFactor);

	boolean flag1 = false, flag2 = false;

	public Mothership(SpriteManager manager) {
		p = new Point(StartPosition.x, StartPosition.y);
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.ship = manager.mothership;
		direction = 1;
		speed = SPEED1;
		startLives = 3;
		lives = startLives;
		active = false;

		score = 400;
	}

	@Override
	public void tick() {

		if (speed == SPEED1 && y > SPEEDUP1) {
			speed = SPEED2;
		}

		if (speed == SPEED2 && y > SPEEDUP2) {
			speed = SPEED3;
		}

		if (speed == SPEED3 && y > SPEEDUP3) {
			speed = SPEED4;
		}

		x += speed * direction;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ship, x, y, BossWidth, BossHeight, null);
	}

	public void reverseDirection() {
		direction *= -1;
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

	/**
	 * Resets the ship's position, direction and firing timers.
	 * 
	 */
	public void reset() {
		if (t != null && t.isRunning()) {
			t.stop();
		}
		lives = startLives;
		active = false;

		// Always start moving left to right
		direction = 1;
		speed = SPEED1;

		// Move to start position
		x = p.x;
		y = p.y;
	}
}
