package com.kirby.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;

public class Tile {
	
	public static BufferedImage TITLE_FLOOR = Kirby.spritesheet.getSprite(0, 128, 32, 32);
	public static BufferedImage TITLE_TRANSPARENT = Kirby.spritesheet.getSprite(32, 128, 32, 32);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}

}
