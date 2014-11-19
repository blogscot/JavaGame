package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.playerYPos;

import java.awt.Graphics;
import java.awt.Point;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Tile;

public class Mothership extends Ship implements Tile, CanFire {

	Player player = Game.getPlayer();
	
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
	
	/**
	 * 
	 * @return true when the invader reaches the bottom of the screen
	 */
	public boolean reachedPlayer() {
		// bottom of ship is 'lower than' top of player
		return y + height >= playerYPos;
	}
}
