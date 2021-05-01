package com.kirby.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;
import com.kirby.world.World;

public class Enemy extends Entity{
	
	public double speed = 0.5;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private int frames = 0, maxframes = 25, index = 0, maxIndex = 4;
	public boolean right = true, left;
	public int chronometer = 0, chronometerMax = 20, index2 = 0, maxIndex2 = 20;
	public boolean troca = false;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[5];
		leftPlayer = new BufferedImage[5];
		for(int i = 0; i<5; i++) {
		rightPlayer[i] = Kirby.spritesheet.getSprite(0 + (32 * i), 224, 32, 32);
		}
		for(int i = 0; i<5; i++) {
			leftPlayer[i] = Kirby.spritesheet.getSprite(0 + (32 * i), 32*6, 32, 32);
			}
		
	}
	
	public void tick(){
		chronometer++;
		if(chronometer == chronometerMax) {
			chronometer = 0;
			index2++;
			System.out.println(index2);
			if(index2 > maxIndex2) {
				index2 = 0;
			}
		}
		if(World.isFree(this.getX(), this.getY() + 1)){
			y+=1;
		}
		if(index2 <= 10){
			left = false;
			right = true;
		}else if(index2 > 11){
			right = false;
			left = true;
		}
		if(right && World.isFree((int)x + (int)speed, this.getY())) {
			System.out.println("Entrou aqui2");
			troca = false;
			right = true;
			x += speed;
		}
		else if(left && World.isFree((int)x - (int)speed, this.getY())) {
			System.out.println("entrou");
			troca = true;
			right = false;
			left = true;
			x -= speed;
		}
		frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g){
		if(right && !troca){
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		}else if(left && troca){
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		}
	}

}
