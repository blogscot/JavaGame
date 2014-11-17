package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.playerYPos;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ListIterator;

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
		g.drawImage(ship, x, y, width, height, null);
		
		// Collision detection
		missiles.stream().forEach(missile -> {
				if (player.getBounds().intersects(missile.getBounds())) {
					Player.losesOneLife();
					missile.destroy();
				}
		});
		
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
		
	public void reverseDirection() {
		speed *= -1;
	}
	
	public void moveDown() {
		y += height * 2;
	}
	
	/**
	 * 
	 * @return true when the invader reaches the player's position
	 */
	public boolean reachedPlayer() {
		return y >= playerYPos;
	}
}
