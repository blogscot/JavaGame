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

	// Motherships get faster each level
	private final int initialSpeed = 3;
	private int speed1 = 4;
	private int speed2 = speed1 + 1;
	private int speed3 = speed2 + 1;
	private int speed4 = speed3 + 1;

	// As the game can be played on different screen sizes it's necessary
	// to calculate speed up positions at compile time.
	private int distanceToPlayer = playerYPos - StartPosition.y;
	private int numberofSpeedUps = 4;
	private int speedUpDistance = distanceToPlayer / numberofSpeedUps;
	private final int speedUpPos1 = StartPosition.y + speedUpDistance;
	private final int speedUpPos2 = StartPosition.y + speedUpDistance * 2;
	private final int speedUpPos3 = StartPosition.y + speedUpDistance * 3;

	// Let's make motherships appear bigger
	private static final double BossScalingFactor = 1.4;
	protected static final int BossWidth = (int) (scaledWidth * 2 * BossScalingFactor);
	protected static final int BossHeight = (int) (scaledHeight * BossScalingFactor);

	public Mothership(SpriteManager manager) {
		p = new Point(StartPosition.x, StartPosition.y);
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.ship = manager.mothership;
		direction = 1;
		speed = speed1;
		startLives = 3;
		lives = startLives;
		active = false;

		score = 400;
	}

	@Override
	public void tick() {

		if (speed == speed1 && y > speedUpPos1) {
			speed = speed2;
		}

		if (speed == speed2 && y > speedUpPos2) {
			speed = speed3;
		}

		if (speed == speed3 && y > speedUpPos3) {
			speed = speed4;
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
		recalculateSpeeds();
		speed = speed1;

		// Move to start position
		x = p.x;
		y = p.y;
	}

	public void levelUp() {
		recalculateSpeeds();
	}

	private void recalculateSpeeds() {
		speed1 = initialSpeed + Player.getLevel();
		speed2 = speed1 + 1;
		speed3 = speed2 + 1;
		speed4 = speed3 + 1;
	}
}
