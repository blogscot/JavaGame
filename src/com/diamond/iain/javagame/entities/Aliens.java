package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Tile;

public class Aliens implements Tile {
	
	ArrayList<Invader> invaders = new ArrayList<>();

	public Aliens(SpriteManager manager){
		
		// Build me an army
		Invader m1 = new Martian(manager, 30, 50);
		Invader m2 = new Martian(manager, 65, 50);
		invaders.add(m1);
		invaders.add(m2);
	}
	
	@Override
	public void tick() {
		invaders.stream().forEach(Invader::tick);
	}

	@Override
	public void render(Graphics g) {
		invaders.stream().forEach(invader -> invader.render(g));
	}
}
