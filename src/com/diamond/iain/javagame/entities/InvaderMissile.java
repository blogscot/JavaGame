package com.diamond.iain.javagame.entities;

import java.awt.Point;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class InvaderMissile extends Missile implements Tile {

	public InvaderMissile(SpriteManager manager, Point p){
		x = p.x;
		y = p.y;
		this.missile = manager.playerMissile;
	}
}