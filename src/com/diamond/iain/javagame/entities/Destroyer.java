package com.diamond.iain.javagame.entities;

import static com.diamond.iain.javagame.utils.GameConstants.scaledHeight;
import static com.diamond.iain.javagame.utils.GameConstants.scaledWidth;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.tiles.Cloakable;
import com.diamond.iain.javagame.tiles.Tile;

public class Destroyer extends Ship implements Tile, Cloakable {
	
	private boolean timerRunning = false;
	private final int cloakCycle = 10000;
	private final int cloakDuration = 3000;	
	private boolean cloakEngaged = false;
	
	private Random r = new Random();
	Timer cloak;
	
	public Destroyer(SpriteManager manager, Point p) {
		x = p.x;
		y = p.y;
		this.ship = manager.destroyer;
		
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
			g.drawImage(ship, x, y, scaledWidth * 2, scaledHeight, null);
		}
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
}
