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
import com.diamond.iain.javagame.tiles.Tile;

public class Martian extends Invader implements Tile, CanFire {

	private ArrayList<Missile> missiles = new ArrayList<>();
	private SpriteManager manager;
	private boolean timerRunning = false;
	private final int ShotRate = 40000;
	Player player = Game.getPlayer();
	Random r = new Random();
	
	public Martian(SpriteManager manager, Point p) {
		x = p.x;
		y = p.y;
		this.manager = manager;
		this.alien = manager.martian;

		score = 40;
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

		// draw Invader
		g.drawImage(alien, x, y, width, height, null);

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

	/**
	 * Generates missiles at random intervals defined by ShotRate
	 * 
	 */
	@Override
	public void fire() {

		if (!timerRunning) {
			timerRunning = true;

			Timer t = new Timer(r.nextInt(ShotRate), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					timerRunning = false;
					missiles.add(new InvaderMissile(manager, new Point(x, y
							+ height)));
				}
			});

			t.setRepeats(false);
			t.start();
		}
	}
}
