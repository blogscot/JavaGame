package com.diamond.iain.javagame.gfx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.entities.Player;

public class KeyManager implements KeyListener {

	Player player = Game.getPlayer();

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.setUp(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.setUp(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
