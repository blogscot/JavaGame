package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.LeftWall;
import static com.diamond.iain.javagame.utils.GameConstants.RightWall;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;

import com.diamond.iain.javagame.gfx.SpriteManager;

public class Aliens {

	private Point anchor = new Point(30, 50);
	private final int xSpacing = 45;
	private final int ySpacing = 25;
	private final int numOfInvaders = 11;
	private final SpriteManager manager;

	private ArrayList<Invader> invaders = new ArrayList<>();

	boolean hitWall = false;

	enum InvaderType {
		Martian, Plutonian, Mercurian, Venusian
	}

	public Aliens(SpriteManager manager) {
		this.manager = manager;

		// Build me an army
		addRow(InvaderType.Martian, 0);
		addRow(InvaderType.Plutonian, 1);
		addRow(InvaderType.Mercurian, 2);
		addRow(InvaderType.Venusian, 3);
	}

	public void tick() {

		// check if any invader has hit the left or right wall
		for (Invader invader : invaders) {
			double pos = invader.getPosition().getX();
			if (pos > RightWall || pos < LeftWall) {
				invader.reverseDirection();
				hitWall = true;
				break;
			}
		}

		if (true == hitWall) {
			hitWall = false;
			invaders.stream().forEach(Invader::moveDown);
		}

		invaders.stream().forEach(Invader::tick);
	}

	public void render(Graphics g) {

		ArrayList<Missile> missiles = Player.getMissiles();
		ListIterator<Invader> it = invaders.listIterator();

		// Collision detection
		missiles.stream().forEach(missile -> {
			invaders.stream().forEach(invader -> {
				if (invader.getBounds().intersects(missile.getBounds())) {
					invader.destroy();
					missile.destroy();
				}
			});
		});

		while (it.hasNext()) {
			Invader inv = it.next();
			if (inv.isActive()) {
				// render each invader if it is still on screen
				inv.render(g);
			} else {
				// remove 'destroyed' invaders
				it.remove();
			}
		}
	}

	// TODO Returns false when all invaders are destroyed
	public boolean isGameOver() {
		// TODO Check is Player.getLives() is zero
		return invaders.size() == 0;
	}

	/**
	 * 
	 * @param invader
	 *            the invader type
	 * @param row
	 *            0..n the row number
	 */
	private void addRow(InvaderType invader, int row) {
	
		anchor.setLocation(new Point(30, 50 + ySpacing * row));
	
		for (int i = 0; i < numOfInvaders; i++) {
	
			switch (invader) {
			case Martian:
				invaders.add(new Martian(manager, anchor));
				break;
			case Plutonian:
				invaders.add(new Plutonian(manager, anchor));
				break;
			case Mercurian:
				invaders.add(new Mercurian(manager, anchor));
				break;
			case Venusian:
				invaders.add(new Venusian(manager, anchor));
				break;
			}
			anchor.translate(xSpacing, 0);
		}
	}
}
