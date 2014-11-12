package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;

import com.diamond.iain.javagame.gfx.SpriteManager;

public class Aliens {

	private Point anchor = new Point(30, 50);
	private final int numOfInvaders = 11;
	private final SpriteManager manager;

	Point GameOverPosition = new Point(360, 400);
	Font f = new Font("Dialog", Font.PLAIN, 32);
	
	private boolean aliensHaveInvaded = false;

	private ArrayList<Invader> invaders = new ArrayList<>();

	enum GameState {
		Active, Over
	}

	private static GameState gameState = GameState.Active;

	enum InvaderType {
		Martian, Plutonian, Mercurian, Venusian
	}

	public Aliens(SpriteManager manager) {
		this.manager = manager;

		buildInvaderArmy();
	}

	public void tick() {

		// is Game Over?
		if (aliensHaveInvaded) return;
		
		invaders.stream().forEach(invader -> {
			
			if (invader.reachedPlayer()) {
				invader.destroy();
				Player.losesOneLife();
				if (!Player.isAlive()) {
					// there may be (non-moving) invaders remaining but we can't
					// clear the collection in this loop. So set flags and return
					aliensHaveInvaded = true;
					gameState = GameState.Over;
					return;
				}
			}
		});

		if (gameState != GameState.Over) {

			if (isArmyDefeated()) {
				Invader.levelUp();
				Player.levelUp();
				buildInvaderArmy();
			}

			// check if any invader has hit the left or right wall
			for (Invader invader : invaders) {
				double pos = invader.getPosition().getX();
				if (pos > RightWall || pos < LeftWall) {
					invader.reverseDirection();
					invaders.stream().forEach(Invader::moveDown);
					break;
				}
			}

			invaders.stream().forEach(invader -> {
				if (invader instanceof Martian) {
					((Martian) invader).fire();
				}
				invader.tick();
			});
		}
	}

	public void render(Graphics g) {

		if (gameState == GameState.Over) {
			displayGameOver(g);
			return;
		}

		// We don't intend to change missiles so make it immutable
		final ArrayList<Missile> missiles = Player.getMissiles();
		ListIterator<Invader> it = invaders.listIterator();

		// Collision detection
		missiles.stream().forEach(missile -> {
			invaders.stream().forEach(invader -> {
				if (invader.getBounds().intersects(missile.getBounds())) {
					Player.addScore(invader.getScore());
					invader.destroy();
					missile.destroy();
				}
			});
		});

		// potentially resizing the collection so use iterators
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
	
	/**
	 * The user can restart the game using a key press
	 * 
	 * @param restart
	 */
	public void restartGame(boolean restart) {
		if (gameState == GameState.Over && restart == true) {
			gameState = GameState.Active;
			aliensHaveInvaded = false;
			invaders.clear();
			Invader.restartGame();
			Player.restartGame();
			buildInvaderArmy();
		}
	}

	/**
	 * 
	 * @return false when all invaders are destroyed
	 */
	public boolean isArmyDefeated() {
		return invaders.size() == 0;
	}

	private void displayGameOver(Graphics g) {
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("Game Over. Press 'S' to restart.", GameOverPosition.x,
				GameOverPosition.y);
	}

	/**
	 * Build a new army
	 */
	private void buildInvaderArmy() {
		addRow(InvaderType.Martian, 0);
		addRow(InvaderType.Plutonian, 1);
		addRow(InvaderType.Mercurian, 2);
		addRow(InvaderType.Venusian, 3);
	}

	/**
	 * Adds a new row to the Invader army
	 * 
	 * @param invader
	 *            the invader type
	 * @param row
	 *            0..n the row number
	 */
	private void addRow(InvaderType invader, int row) {

		// set vertical gap between rows
		anchor.setLocation(new Point(30, 50 + getSpacingDimension().height
				* row));

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
			// set horizontal gap between invaders
			anchor.translate(getSpacingDimension().width, 0);
		}
	}
}
