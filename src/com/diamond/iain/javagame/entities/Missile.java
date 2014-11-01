package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Missile implements Tile {
	
	private static final int SPEED = 5;
	public int x = 0, y = 0;
	private boolean active = true;
	private BufferedImage missile;
	
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
	
	public int getYpos() {
		return y;
	}

	public boolean isActive(){
		return active;
	}
	
	public void destroy() {
		active = false;
	}
}
