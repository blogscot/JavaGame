package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.Timer;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Cloakable;
import com.diamond.iain.javagame.tiles.Tile;

public class Destroyer extends Ship implements Tile, Cloakable, CanFire {

	private boolean cloakTimerRunning = false;
	private final int cloakCycle = 10000;
	private final int cloakDuration = 3000;
	private boolean cloakEngaged = false;
	Player player = Game.getPlayer();

	Timer cloak;

	public Destroyer(SpriteManager manager) {
		p = new Point(0, 50);
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.ship = manager.destroyer;
		startLives = 2;
		lives = startLives;
		active = false;

		score = 200;

		// This is a fixed timer so set it up once
		cloak = new Timer(cloakDuration, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cloakTimerRunning = false;
				cloakEngaged = false;
			}
		});
		cloak.setRepeats(false);
	}

	@Override
	public void render(Graphics g) {
		if (!cloakEngaged) {
			g.drawImage(ship, x, y, width, height, null);
		}
		
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

	@Override
	public void cloak() {

		if (!cloakTimerRunning) {
			cloakTimerRunning = true;

			Timer c = new Timer(r.nextInt(cloakCycle), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// switch the cloak on
					cloakEngaged = true;
					cloak.start();
				}
			});

			c.setRepeats(false);
			c.start();
		}
	}
}
