package com.kirby.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;
import com.kirby.world.World;

public class Enemy extends Entity{
	
	public double speed = 0.5;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private int frames = 0, maxframes = 8, index = 0, maxIndex = 4;
	public boolean right, left, stopped_right = true, stopped_left = false;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public int chronometer = 0, chronometerMax = 40;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[5];
		leftPlayer = new BufferedImage[5];
		for(int i = 0; i<5; i++) {
		rightPlayer[i] = Kirby.spritesheet.getSprite(0 + (32 * i), 224, 32, 32);
		}
		for(int i = 0; i<5; i++) {
			leftPlayer[i] = Kirby.spritesheet.getSprite(0 + (32 * i), 224 - 32, 32, 32);
			}
		
	}
	
	public void tick(){
		System.out.println(chronometer);
		if(right && World.isFree(this.getX() + (int)speed, this.getY()) && chronometer <= 20) {
			dir = right_dir;
			x += speed;
		}
		else if(left && World.isFree(this.getX() - (int)speed, this.getY()) && chronometer > 21) {
			dir = left_dir;
			x -= speed;
		}
		if(World.isFree(this.getX(), this.getY() + (int)speed)){
			y+=speed;
		}
		frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		chronometer++;
		if(chronometer == chronometerMax){
			chronometer = 0;
		}
	}
	
	public void render(Graphics g){
		if(dir == right_dir){
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		}else if(dir == left_dir){
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		}
	}

}
