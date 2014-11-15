package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.getScreenDimension;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.Timer;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Cloakable;
import com.diamond.iain.javagame.tiles.Tile;

public class Destroyer extends Ship implements Tile, Cloakable, CanFire {

	private ArrayList<Missile> missiles = new ArrayList<>();
	private SpriteManager manager;
	private boolean cloakTimerRunning = false;
	private final int cloakCycle = 10000;
	private final int cloakDuration = 3000;
	private boolean cloakEngaged = false;
	private boolean fireTimerRunning = false;
	private final int ShotRate = 10000;
	Player player = Game.getPlayer();

	private final Point p = new Point(0, 50);

	private Random r = new Random();
	Timer cloak;

	public Destroyer(SpriteManager manager) {
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.ship = manager.destroyer;
		isActive = false;

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
	public void tick() {
		x += speed;
		
		// Move each missile if it is still on screen
		for (Missile m : missiles) {
			if (m.getPosition().getY() < getScreenDimension().height) {
				m.tick();
			} else {
				// otherwise mark for removal from list
				m.destroy();
			}
		}
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

	public void resetPosition() {
		x = p.x;
		y = p.y;
	}

	public void restartGame() {
		resetPosition();
		isActive = false;
	}

	@Override
	public void cloak() {

		if (!cloakTimerRunning) {
			cloakTimerRunning = true;

			Timer t = new Timer(r.nextInt(cloakCycle), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// switch the cloak on
					cloakEngaged = true;
					cloak.start();
				}
			});

			t.setRepeats(false);
			t.start();
		}
	}

	@Override
	public void fire() {
		if (!fireTimerRunning) {
			fireTimerRunning = true;

			Timer t = new Timer(r.nextInt(ShotRate), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					fireTimerRunning = false;
					missiles.add(new InvaderMissile(manager, new Point(x
							+ width / 2, y + height)));
				}
			});

			t.setRepeats(false);
			t.start();
		}

	}
}
