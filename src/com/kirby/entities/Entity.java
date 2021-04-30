package com.kirby.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;

public class Entity {
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	public static BufferedImage ENEMY_SPRITESHEET = Kirby.spritesheet.getSprite(0, 224-32, 32, 32);
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidht() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}

}
