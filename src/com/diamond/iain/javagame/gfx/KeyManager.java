package com.diamond.iain.javagame.gfx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.diamond.iain.javagame.Game;
import com.diamond.iain.javagame.entities.Player;

public class KeyManager implements KeyListener {

	Player player = Game.getPlayer();

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) { player.setLeft(true); }

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) { player.setRight(true);}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) { player.firePressed(true); }
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) { player.setLeft(false); }

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) { player.setRight(false); }

		if (e.getKeyCode() == KeyEvent.VK_SPACE) { player.firePressed(false); }
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
