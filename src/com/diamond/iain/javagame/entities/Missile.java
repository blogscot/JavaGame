package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.missileYPos;
import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Missile implements Tile {
	
	private static final int SPEED = 5;
	public int x = 0, y = 0;
	private BufferedImage missile;

	private boolean active = true;
	
	public Missile(SpriteManager manager, int startingX){
		x = startingX;
		y = missileYPos;
		missile = manager.missile;
	}

	@Override
	public void tick() {
		if (y > 0) y -= SPEED;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(missile, x, y, scaledHeight, scaledWidth, null);
	}

	@Override
	public Point getPosition() {
		return new Point(x,y);
	}

	@Override
	public boolean isActive(){
		return active;
	}
	
	@Override
	public void destroy() {
		active = false;
	}
}
