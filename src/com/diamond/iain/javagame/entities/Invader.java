package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.*;

import java.awt.Graphics;
import java.awt.Point;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Invader implements Tile {
	
	private static int speed = 6;
	public int x = 0, y = 0;
	public boolean isActive = true;
	
	// Scoring data
	int baseValue = 10;
	
	@Override
	public void tick() {
		x += speed;
	}

	@Override
	public void render(Graphics g) {
	}
	
	public Point getPosition() {
		return new Point(x,y);
	}
	
	public void reverseDirection(){
		speed *= -1;
	}
	
	public void moveDown(){
		y += TileHeight;
	}
	
	public void destroy(){
		isActive = false;
	}

	public boolean isActive(){
		return isActive;
	}
}
