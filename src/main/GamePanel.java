package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // Default Tile Size
	final int scale = 3; // Scaling Sprites
	
	final int tileSize = originalTileSize * scale; // Final Tile Size
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 Pixels
	
	// FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// Set Player's Default Position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));;
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		int offset = -1;
		
		while (gameThread != null) {
					
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;
		
			if (delta >= 1) {
				update();				
				repaint();		
				delta--;
				playerX += offset * playerSpeed;
				if (playerX >= screenWidth || playerX <= 0) {
					offset *= -1;
				}
			}
		}
	}
	
	public void update() {
		
		if (keyH.upPressed) {
			playerY -= playerSpeed;
		} 
		if (keyH.downPressed) {
			playerY += playerSpeed;
		} 
		if (keyH.leftPressed) {
			playerX -= playerSpeed;
		} 
		if (keyH.rightPressed) {
			playerX += playerSpeed;
		} 
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
}
