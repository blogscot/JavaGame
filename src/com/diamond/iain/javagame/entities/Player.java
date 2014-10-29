package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Player extends Tile {

	private static final int SPEED = 6;
	private boolean right = false;
	private boolean left = false;
	private BufferedImage player;

	private ArrayList<Missile> missiles = new ArrayList<>();

	public Player(SpriteManager manager) {
		super(manager);
		this.x = 30;
		this.y = playerYPos;
		this.player = manager.player;
	}

	@Override
	public void tick() {

		// Move Player
		if (left && x >= 0)
			x -= SPEED;
		if (right && x <= ScreenWidth * SCALE - (TileWidth))
			x += SPEED;

		// Move each missile if it is still on screen
		for (Missile m : missiles) {
			if (m.getYpos() > 0) {
				m.tick();
			} else {
				// otherwise remove from list
				m.destroy();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(player, x, y, scaledHeight, scaledWidth, null);
		
		// render each missile if it is still on screen
		for (Missile m : missiles) {
			if (m.isActive())
			{
				m.render(g);
			}
		}
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void firePressed(boolean fire) {
		if (fire) 
		{
			// create new missile at player's x position
			missiles.add(new Missile(manager, this.x));
		}
	}
}
