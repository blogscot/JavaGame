package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.TileHeight;
import static com.diamond.iain.javagame.utils.GameConstants.TileWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.diamond.iain.javagame.tiles.Tile;

public abstract class Ship implements Tile {

	//private static final int SPEED = 2;
	protected int x = 0, y = 0;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x,y, TileWidth, TileHeight);
	}

}
