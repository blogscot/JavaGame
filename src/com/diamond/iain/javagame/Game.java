package com.diamond.iain.javagame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.diamond.iain.javagame.entities.Player;
import com.diamond.iain.javagame.gfx.ImageLoader;
import com.diamond.iain.javagame.gfx.KeyManager;
import com.diamond.iain.javagame.gfx.SpriteManager;
import com.diamond.iain.javagame.gfx.SpriteSheet;

// Game uses the Model View Controller Pattern
// Game = Controller, Player = View, Model = SpriteManager
// Thus, Player should not depend on SpriteManager

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 440, HEIGHT = 300, SCALE = 2;
	public static boolean running = false;
	public Thread gameThread;
	
	private BufferedImage spriteSheet;
	
	private static Player player;
	
	public void init() {
		ImageLoader loader = new ImageLoader();
		
		try
		{
			spriteSheet = loader.load("/spritesheet.png");
		} catch (IllegalArgumentException e) {
			System.out.println("SpriteSheet not found.");
			System.exit(-1);
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		player = new Player(new SpriteManager(ss));
		
		this.addKeyListener(new KeyManager());
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			System.out.println("Bad Shutdown error.");
		}
	}
	
	@Override
	public void run() {
		
		init();
		long lastTime = System.nanoTime();
		final double amountofTicks = 60;
		double ns = 1000000000 / amountofTicks;
		double delta = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				delta--;
			}
			render();
		}
		stop();
	}

	private void tick() {
		player.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		// draw game entities
		player.render(g);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		JFrame frame = new JFrame("Space Invaders");
		frame.setSize(WIDTH*SCALE, HEIGHT*SCALE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		
		game.start();
	}

	public static Player getPlayer(){
		return player;
	}
}


