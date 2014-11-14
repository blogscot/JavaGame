package com.diamond.iain.javagame.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.CanFire;
import com.diamond.iain.javagame.tiles.Cloakable;
import com.diamond.iain.javagame.tiles.Tile;

public class Destroyer extends Ship implements Tile, Cloakable, CanFire {
	
	private boolean timerRunning = false;
	private final int cloakCycle = 10000;
	private final int cloakDuration = 3000;	
	private boolean cloakEngaged = false;
	private final Point p = new Point(0,50);

	private Random r = new Random();
	Timer cloak;
	
	public Destroyer(SpriteManager manager) {
		x = p.x;
		y = p.y;
		this.ship = manager.destroyer;
		isActive = false;
		
		score = 200;
		
		// This is a fixed timer so set it up once
		cloak = new Timer(cloakDuration, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timerRunning = false;
				cloakEngaged = false;
			}
		});
		cloak.setRepeats(false);		
	}
	

	@Override
	public void render(Graphics g) {
		if (!cloakEngaged)
		{
			g.drawImage(ship, x, y, width, height, null);
		}
	}
	
	public void resetPosition() {
		x = p.x;
		y = p.y;
	}
	
	public void restartGame(){
		resetPosition();
		isActive = false;
	}
	
	@Override
	public void cloak() {
		
		if (!timerRunning) {
			timerRunning = true;

			Timer t = new Timer(r.nextInt(cloakCycle), new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// switch the cloak on
					cloakEngaged = true;
					cloak.start();
				}
			});

			t.setRepeats(false);
			t.start();
		}
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}
}
