package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.LeftWall;
import static com.diamond.iain.javagame.utils.GameConstants.RightWall;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.diamond.iain.javagame.gfx.SpriteManager;

public class Aliens {

	ArrayList<Invader> invaders = new ArrayList<>();
	Point anchor = new Point(30, 50);
	private final int xSpacing = 35;
	private final int ySpacing = 85;

	boolean hitWall = false;

	public Aliens(SpriteManager manager) {

		// Build me an army

		// Row 1
		Invader m1_1 = new Martian(manager, anchor);
		anchor.translate(xSpacing, 0);
		Invader m1_2 = new Martian(manager, anchor);

		// new Row 2
		anchor.move(30, ySpacing);
		Invader m2_1 = new Plutonian(manager, anchor);
		anchor.translate(xSpacing, 0);
		Invader m2_2 = new Plutonian(manager, anchor);

		invaders.add(m1_1);
		invaders.add(m1_2);
		invaders.add(m2_1);
		invaders.add(m2_2);
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
		invaders.stream().forEach(invader -> invader.render(g));
	}

	// TODO Returns false when all invaders are destroyed
	public boolean isActive() {
		return false;
	}
}
