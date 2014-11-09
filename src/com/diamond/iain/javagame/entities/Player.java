package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Player implements Tile {

	private static final int SPEED = 6;
	private int x = 0, y = 0;
	private boolean right = false, left = false;
	private BufferedImage player;
	private SpriteManager manager;
	
	private boolean alive = true;
	private static int level = 1;
	private static int lives = 3;

	// Scoring data
	private static int totalScore = 0;
	private static int highScore = 0;
	
	Font f;
	Point scorePosition = new Point(20, 20);
	Point levelPosition = new Point(getScreenHeight(), 20);
	Point highScorePosition = new Point(ScreenWidth*SCALE - 200 , 20);
	
	long lastPressed = System.currentTimeMillis();

	private static ArrayList<Missile> missiles = new ArrayList<>();

	public Player(SpriteManager manager) {
		this.x = 30;
		this.y = playerYPos;
		this.player = manager.player;
		this.manager = manager;
		f = new Font("Dialog", Font.PLAIN, 18);
	}

	@Override
	public void tick() {

		// Move Player
		if (left && x >= 0)
			x -= SPEED;
		if (right && x <= ScreenWidth * SCALE - (TileWidth))
			x += SPEED;

		// Move each missile if it is still on screen
		for (Missile m : missiles) {
			if (m.getPosition().getY() > 0) {
				m.tick();
			} else {
				// otherwise remove from list
				m.destroy();
			}
		}
	}

	@Override
	public void render(Graphics g) {

		updateScore(g);
		
		// draw Player
		g.drawImage(player, x, y, scaledWidth, scaledHeight, null);

		// Note: use ListIterator to avoid ConcurrentModificationException
		ListIterator<Missile> it = missiles.listIterator();

		while (it.hasNext()) {
			Missile m = it.next();
			if (m.isActive()) {
				// render each missile if it is still on screen
				m.render(g);
			} else {
				// remove 'destroyed' missiles
				it.remove();
			}
		}
	}

	/**
	 * Adds points to the players current score
	 * 
	 * @param score 
	 */
	public static void addScore(int score) {
		totalScore += score;
	}
	
	public static void levelUp(){
		level += 1;
	}

	private void updateScore(Graphics g) {
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("Player Score: "+ totalScore, scorePosition.x, scorePosition.y);
		g.drawString("Level "+ level, levelPosition.x, levelPosition.y);
		g.drawString("High Score: "+ highScore, highScorePosition.x, highScorePosition.y);
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void firePressed(boolean fire) {
		
		long now = System.currentTimeMillis();
		long duration = now - lastPressed;
		
		// Avoid the machine gun effect
		if (fire && duration > 300 ) {
			// create new missile at player's x position
			missiles.add(new Missile(manager, this.x));
			lastPressed = System.currentTimeMillis();
		}
	}

	@Override
	public Point getPosition() {
		return new Point(x,y);
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x,y, TileWidth, TileHeight);
	}
	
	public static ArrayList<Missile> getMissiles(){
		return missiles;
	}

	/**
	 *  returns false when the player is killed
	 */
	@Override
	public boolean isActive() {
		return alive;
	}

	@Override
	public void destroy() {
		alive = false;
	}
}
