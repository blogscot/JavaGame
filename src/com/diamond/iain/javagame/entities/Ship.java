package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Tile;

public abstract class Ship implements Tile, CanFire {

	private static final int SPEED = 2;
	protected int speed = SPEED;
	protected int x = 0, y = 0;
	protected boolean active = true;
	protected static final int width = scaledWidth * 2;
	protected static final int height = scaledHeight;

	protected Point p;

	protected SpriteManager manager;

	protected ArrayList<Missile> missiles = new ArrayList<>();
	private boolean fireTimerRunning = false;
	private final int ShotRate = 4000;
	protected Random r = new Random();

	protected BufferedImage ship;

	protected int startLives = 0;
	protected int lives = 0;
	protected int score = 0;

	@Override
	public void tick() {
		x += speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ship, x, y, width, height, null);
	}

	public int getScore() {
		return score;
	}

	/**
	 * Ships take a number of hits before being destroyed
	 */
	public void destroy() {
		lives -= 1;
		if (lives <= 0) {
			active = false;
			lives = startLives;
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean flag) {
		active = flag;
	}

	public Point getPosition() {
		return new Point(x, y);
	}

	public int getWidth() {
		return width;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void resetPosition() {
		lives = startLives;
		x = p.x;
		y = p.y;
	}

	public void restartGame() {
		resetPosition();
		lives = startLives;
		active = false;
	}

	@Override
	public void fire() {

		if (!fireTimerRunning) {
			fireTimerRunning = true;

			Timer f = new Timer(r.nextInt(ShotRate), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					fireTimerRunning = false;
					Game.getAliens().addMissile(new Point(x + width / 2, y + height));
				}
			});

			f.setRepeats(false);
			f.start();
		}
	}
}
