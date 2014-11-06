package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.SCALE;
import static com.diamond.iain.javagame.utils.GameConstants.ScreenWidth;
import static com.diamond.iain.javagame.utils.GameConstants.TileHeight;
import static com.diamond.iain.javagame.utils.GameConstants.TileWidth;
import static com.diamond.iain.javagame.utils.GameConstants.playerYPos;
import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

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

	// Scoring data
	private int Score = 0;
	Font f;
	
	long lastPressed = System.currentTimeMillis();

	private static ArrayList<Missile> missiles = new ArrayList<>();

	public Player(SpriteManager manager) {
		this.x = 30;
		this.y = playerYPos;
		this.player = manager.player;
		this.manager = manager;
		f = new Font("Dialog", Font.PLAIN, 24);
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

	private void updateScore(Graphics g) {
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("Player Score: "+Score, (ScreenWidth * 2) - 200, TileHeight);
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
