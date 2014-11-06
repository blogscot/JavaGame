package com.diamond.iain.javagame.tiles;

import java.awt.Graphics;
import java.awt.Point;


public interface Tile {
	
	public void tick();
	public void render(Graphics g);
	public void destroy();
	public boolean isActive();
	public Point getPosition();
}
