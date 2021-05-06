package com.kirby.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.kirby.entities.Entity;
import com.kirby.entities.Player;
import com.kirby.graficos.Spritesheet;
import com.kirby.world.World;

public class Kirby extends Canvas implements Runnable, KeyListener{
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public  final static int WIDTH = 360;
	public static final int HEIGHT = 220;
	private final int SCALE = 2;
	
	public static List<Entity> entities;
	
	public static Spritesheet spritesheet;
	public static Spritesheet background;
	
	private BufferedImage image;
	
	public static Player player;
	
	public static World world;

	public Kirby() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,32,32, spritesheet.getSprite(0, 0, 32, 32));
		world = new World("/map.png", "/background.png"); 
		entities.add(player);
	}
	
	public void initFrame() {
		frame = new JFrame("Game 1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			e.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		Graphics g = image.getGraphics();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.render(g);
		for(int i = 0; i < entities.size(); i++ ) {
			Entity e = entities.get(i);
			e.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}
	
	
	public static void main(String agrs[]) {
		Kirby game = new Kirby();
		game.start();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks = 60.0;
		double ns = 1000000000 / amoutOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println(frames);
				frames = 0;
				timer += 1000;
			}			
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
				player.fly = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_X) {
			if(player.temp >= 100000000){
				player.aspire = false;
			}else if(player.temp < 1000000){
				player.aspire = true;
			}
			}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
				player.right = true;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
					e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.fly = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.aspire = false;
			}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
				player.right = false;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
					e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
			}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}