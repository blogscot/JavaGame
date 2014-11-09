package com.diamond.iain.javagame.entities;

import java.awt.Point;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Mercurian extends Invader implements Tile {
	
	public Mercurian(SpriteManager manager, Point p) {
		x = p.x;
		y = p.y;
		this.alien = manager.mercurian;
		
		score = 20;
	}
}