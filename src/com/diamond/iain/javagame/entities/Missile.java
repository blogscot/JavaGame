package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Missile extends Tile {
	
	private static final int SPEED = 5;
	private boolean active = true;
	private BufferedImage missile = manager.missile;
	
	public Missile(SpriteManager manager, int startingX){
		super(manager);
		this.x = startingX;
		this.y = missileYPos;
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
		//System.out.println(x+","+y);
		return y;
	}

	public boolean isActive(){
		return active;
	}
	
	public void destroy() {
		active = false;
	}
}
