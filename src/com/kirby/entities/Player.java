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
	public int framesAspire = 0, maxframesAspire = 8, indexAspireOn = 0, maxIndexAspireOn = 2, indexAspireOff = 0, maxIndexAspireOff = 2;
	public boolean aspire = false, aspireOffBool = false, aspireTemp = true;
	public int temp = 0, maxtemp = 120, tempCd = 0, maxTempCd = 60;
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
		if(moved == false && aspire && aspireTemp) {
			if(tempCd < 120 && aspireTemp) {
			tempCd++;	
			temp++;
			framesAspire++;
			if(framesAspire == maxframesAspire) {
				framesAspire = 0;
				indexAspireOn++;
				if(indexAspireOn > maxIndexAspireOn) {
					indexAspireOn = 2;
					System.out.println("Entrou on");
				}if(aspireOffBool == true) {
					indexAspireOff++;
					System.out.println("Entrou off");
				}if(indexAspireOff > maxIndexAspireOff) {
					indexAspireOff = 2;
				}
			}
		}
	  }
		if(aspire == false && tempCd > 0) {
			tempCd--;
			aspireTemp = false;
			if(tempCd == 0) {
				aspireTemp = true;
			}
		}
		System.out.println(tempCd);
	}
	public void render(Graphics g){
		if(aspire && moved == false){
			if(temp < 90 ){
			g.drawImage(aspireOn[indexAspireOn], this.getX(), this.getY(), null);
			}else if(temp >= 90){
				indexAspireOn = 0;
				aspireOffBool = true;
				g.drawImage(aspireOff[indexAspireOff], this.getX(), this.getY(), null);
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
