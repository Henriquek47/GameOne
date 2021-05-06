package com.kirby.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;
import com.kirby.world.World;


public class Player extends Entity{
	
	public boolean right, left, stopped_right = true, stopped_left = false;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.5;
	private boolean stopped = true;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	public boolean fly = false;
	private int framesWalk = 0, maxframesWalk = 8, indexWalk = 0, maxIndexWalk = 5;
	private int framesAspire = 0, maxframesAspire = 8, indexAspire = 0, maxIndexAspire = 2;
	public boolean aspire = false;
	public int temp = 0, maxtemp = 60;
	private BufferedImage[] aspireOn;
	private BufferedImage[] aspireOff;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		animationWalk();
		animationAspire();
		
	}
	
	public void tick(){
		stopped = true;
		moved = false;
		if(moved == false && aspire){
			
		}
		if(right && World.isFree(this.getX() + (int)speed, this.getY()) && aspire == false) {
			stopped_left = false;
			stopped_right = true;
			stopped = false;
			moved = true;
			dir = right_dir;
			x += speed;
		} 	
		else if(left && World.isFree(this.getX() - (int)speed, this.getY()) && aspire == false) {
			stopped_right = false;
			stopped_left = true;
			stopped = false;
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if(World.isFree(this.getX(), this.getY() + (int)speed) && fly == false){
			y+=speed;
		}else if(fly == true){
				y-=speed;
			}
			
		if(moved) {
			framesWalk++;
			if(framesWalk == maxframesWalk) {
				framesWalk = 0;
				indexWalk++;
				if(indexWalk > maxIndexWalk) {
					indexWalk = 0;
				}
			}
		}
		if(moved == false && aspire) {
			temp++;
			framesAspire++;
			System.out.println(temp);
			if(temp == maxtemp){
			}
			if(framesAspire == maxframesAspire) {
				framesAspire = 0;
				indexAspire++;
				if(indexAspire > maxIndexAspire) {
					indexAspire = 0;
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(aspire && moved == false){
			if(temp < 100000 ){
			g.drawImage(aspireOn[indexAspire], this.getX(), this.getY(), null);
			}else if(temp >= 30){
				g.drawImage(aspireOff[indexAspire], this.getX(), this.getY(), null);
			}
		}else if(dir == right_dir && stopped == false  && aspire == false){
			g.drawImage(rightPlayer[indexWalk], this.getX(), this.getY(), null);
		}else if(dir == left_dir && stopped == false  && aspire == false){
			g.drawImage(leftPlayer[indexWalk], this.getX(), this.getY(), null);
		}else if(right == false && left == false){
			if(stopped_right == true && moved == false){
			g.drawImage(Kirby.spritesheet.getSprite(0, 0, 32, 32), this.getX(), this.getY(), null);
		}else if(stopped_left == true && moved == false){
			g.drawImage(Kirby.spritesheet.getSprite(32*6, 128-32, 32, 32), this.getX(), this.getY(), null);
		}
		}	
	}
	
	void animationWalk(){
		rightPlayer = new BufferedImage[6];
		leftPlayer = new BufferedImage[6];
		for(int i = 0; i<6; i++) {
		rightPlayer[i] = Kirby.spritesheet.getSprite(32 + (i * 32), 0, 32, 32);
		}
		for(int i = 0; i<6; i++) {
			leftPlayer[i] = Kirby.spritesheet.getSprite(0 + (i * 32), 128-32, 32, 32);
			}
	}
	
	void animationAspire(){
		aspireOn = new BufferedImage[3];
		aspireOff = new BufferedImage[3];
		for(int i = 0; i<3; i++) {
			aspireOn[i] = Kirby.spritesheet.getSprite(0 + (i * 32), 64, 32, 32);
		}
		for(int i = 0; i<3; i++) {
			aspireOff[i] = Kirby.spritesheet.getSprite(64 + (i * 32), 64, 32, 32);
			}
	}

}
