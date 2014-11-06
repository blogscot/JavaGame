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

	private ArrayList<Invader> invaders = new ArrayList<>();

	boolean hitWall = false;
	
	public Aliens(SpriteManager manager) {

		// Build me an army

		// Row 1
		for(int i = 0; i < 11; i++){
			invaders.add(new Martian(manager, anchor));
			anchor.translate(xSpacing, 0);
		}
		
		anchor.setLocation(new Point(30, 50+ySpacing));

		// Row 2
		for(int i = 0; i < 11; i++){
			invaders.add(new Plutonian(manager, anchor));
			anchor.translate(xSpacing, 0);
		}
		
		anchor.setLocation(new Point(30, 50+ySpacing*2));

		// Row 3
		for(int i = 0; i < 11; i++){
			invaders.add(new Mercurian(manager, anchor));
			anchor.translate(xSpacing, 0);
		}
		
		anchor.setLocation(new Point(30, 50+ySpacing*3));

		// Row 4
		for(int i = 0; i < 11; i++){
			invaders.add(new Venusian(manager, anchor));
			anchor.translate(xSpacing, 0);
		}
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
				if (invader.getBounds().intersects(missile.getBounds())){
					invader.destroy();
					missile.destroy();
				}
			});
		});
		
		//invaders.stream().forEach(invader -> invader.render(g));
		
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
	public boolean isActive() {
		return false;
	}
}
