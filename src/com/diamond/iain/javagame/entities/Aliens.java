package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.LeftWall;
import static com.diamond.iain.javagame.utils.GameConstants.RightWall;
import static com.diamond.iain.javagame.utils.GameConstants.getScreenDimension;
import static com.diamond.iain.javagame.utils.GameConstants.getSpacingDimension;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.Timer;

import com.diamond.iain.javagame.gfx.SpriteManager;

public class Aliens {

	private Point anchor = new Point(30, 50);
	private final int numOfInvaders = 11;
	private final SpriteManager manager;

	Point openingMessagePosition = new Point(260, 260);
	Point startGamePosition = new Point(400, 400);
	Font basic = new Font("Dialog", Font.PLAIN, 32);
	Font grande = new Font("Dialog", Font.PLAIN, 100);

	private final int ShipFreq = 20000;
	private final int ShipInitialDelay = 30000;
	private boolean destroyerTimerRunning = false;
	private boolean bossDefeated = false;
	Random r = new Random();
	Mothership mothership;
	Destroyer destroyer;

	private final int AsteroidFreq = 8000;
	private final int AsteroidInitialDelay = 8000;
	private boolean asteroidTimerRunning = false;

	private ArrayList<Invader> invaders = new ArrayList<>();
	private ArrayList<Asteroid> asteroids = new ArrayList<>();

	enum GameState {
		Active, Over
	}

	private static GameState gameState = GameState.Over;

	enum InvaderType {
		Martian, Plutonian, Mercurian, Venusian
	}

	public Aliens(SpriteManager manager) {
		this.manager = manager;
		destroyer = new Destroyer(manager);
		mothership = new Mothership(manager);
	}

	public void tick() {

		double pos;

		if (isGameOver())
			return;

		// Check if player has lost all their lives
		// e.g. shot by invader missiles or hit
		// by asteroids
		if (!Player.isAlive()) {
			gameState = GameState.Over;
			return;
		}

		// Check if the game is about to be over
		invaders.stream().forEach(invader -> {

			if (invader.reachedPlayer()) {
				invader.destroy();
				Player.losesOneLife();
				if (!Player.isAlive()) {
					gameState = GameState.Over;
					return;
				}
			}
		});
		
		if (mothership.isActive() && mothership.reachedPlayer()){
			mothership.destroy();
			Player.losesOneLife();
			if (!Player.isAlive()){
				gameState = GameState.Over;
				return;
			}
		}

		if (isArmyDefeated() && !destroyer.isActive()) {
			if (!bossDefeated) {
				mothership.setActive();
			} else {
				bossDefeated = false;
				Invader.levelUp();
				Player.levelUp();
				buildInvaderArmy();
			}
		}

		addAsteroid();
		addDestroyer();

		// check if any invader has hit the left or right wall
		for (Invader invader : invaders) {
			pos = invader.getPosition().getX();
			if (pos + invader.getWidth() > RightWall || pos < LeftWall) {
				invader.reverseDirection();
				invaders.stream().forEach(Invader::moveDown);
				break;
			}
		}

		// check if mother-ship is alive and still on the screen
		if (mothership.isActive()) {
			pos = mothership.getPosition().getX();
			if (pos + mothership.getWidth() > RightWall || pos < LeftWall) {
				mothership.reverseDirection();
				mothership.moveDown();
			}
			mothership.fire();
			mothership.tick();
		}

		// check if destroyer is alive and still on the screen
		if (destroyer.isActive() && destroyer.getPosition().getX() < RightWall) {
			destroyer.cloak();
			destroyer.fire();
			destroyer.tick();
		} else {
			// TO REMOVE Is this code needed?
			destroyer.destroy();
		}

		asteroids.stream().forEach(Asteroid::tick);

		// Everybody moves. Special invaders use special abilities
		invaders.stream().forEach(invader -> {
			if (invader instanceof Martian) {
				((Martian) invader).fire();
			} else if (invader instanceof Mercurian) {
				((Mercurian) invader).cloak();
			}
			invader.tick();
		});
	}

	public void render(Graphics g) {

		if (isGameOver()) {
			displayStartGame(g);
			return;
		}

		// We don't intend to change missiles so make it immutable
		final ArrayList<Missile> missiles = Player.getMissiles();
		ListIterator<Invader> it = invaders.listIterator();

		// Player missiles collision detection
		missiles.stream().forEach(
				missile -> {
					invaders.stream().forEach(invader -> {
						// a missile should kill one invader only
							if (missile.isActive()
									&& invader.getBounds().intersects(
											missile.getBounds())) {
								Player.addScore(invader.getScore());
								invader.destroy();
								missile.destroy();
							}
						});

					asteroids.stream().forEach(
							asteroid -> {
								if (asteroid.isActive()
										&& asteroid.getBounds().intersects(
												missile.getBounds())) {
									asteroid.destroy();
									missile.destroy();
								}
							});

					if (mothership.isActive()
							&& mothership.getBounds().intersects(
									missile.getBounds())) {
						Player.addScore(mothership.getScore());
						mothership.destroy();
						mothership.resetPosition();
						bossDefeated = true;
						missile.destroy();
					}

					// hitting a destroyed ship should yield no further points
					if (destroyer.isActive()
							&& destroyer.getBounds().intersects(
									missile.getBounds())) {
						Player.addScore(destroyer.getScore());
						destroyer.destroy();
						missile.destroy();
					}
				});

		ListIterator<Asteroid> as = asteroids.listIterator();

		// draw asteroid first so they appear in the 'background'
		while (as.hasNext()) {
			Asteroid ast = as.next();
			if (ast.isActive()) {
				// render each asteroid if it is still on screen
				ast.render(g);
			} else {
				// remove 'destroyed' asteroids
				as.remove();
			}
		}

		if (mothership.isActive()) {
			mothership.render(g);
		}

		if (destroyer.isActive()) {
			destroyer.render(g);
		}

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
	 *            true to restart
	 */
	public void restartGame(boolean restart) {
		if (gameState == GameState.Over && restart == true) {
			bossDefeated = false;
			invaders.clear();
			asteroids.clear();
			Invader.restartGame();
			Player.restartGame();
			destroyer.restartGame();
			buildInvaderArmy();
			// Key presses are asynchronous so make sure the invader
			// army is finished construction before starting the game for real,
			// comparing list + modifying list simultaneously = BAD! see tick()
			gameState = GameState.Active;
		}
	}

	/**
	 * 
	 * @return false when all invaders are destroyed
	 */
	public boolean isArmyDefeated() {
		return invaders.size() == 0;
	}

	public static boolean isGameOver() {
		return gameState == GameState.Over;
	}

	private void displayStartGame(Graphics g) {
		g.setFont(grande);
		g.setColor(Color.cyan);
		g.drawString("Space Invaders", openingMessagePosition.x,
				openingMessagePosition.y);
		g.setFont(basic);
		g.setColor(Color.white);
		g.drawString("Press 's' to start a new game.", startGamePosition.x,
				startGamePosition.y);
	}

	public void addDestroyer() {

		if (!destroyerTimerRunning) {
			destroyerTimerRunning = true;

			Timer t = new Timer(r.nextInt(ShipFreq), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					destroyerTimerRunning = false;
					destroyer.resetPosition();
					destroyer.setActive();
				}
			});

			t.setInitialDelay(ShipInitialDelay);
			t.setRepeats(false);
			t.start();
		}
	}

	public void addAsteroid() {

		if (!asteroidTimerRunning) {
			asteroidTimerRunning = true;

			Timer t = new Timer(r.nextInt(AsteroidFreq), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					asteroidTimerRunning = false;
					int width = (int) (getScreenDimension().getWidth())
							- scaledWidth;
					asteroids.add(new Asteroid(manager, new Point((r
							.nextInt(width)), 0)));
				}
			});

			t.setInitialDelay(AsteroidInitialDelay);
			t.setRepeats(false);
			t.start();
		}
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
