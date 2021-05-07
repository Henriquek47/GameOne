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
	public int jumpHeight = 32, jumpFrames = 0;
	public boolean jump = false, isJumping = false;
	private int framesWalk = 0, maxframesWalk = 8, indexWalk = 0, maxIndexWalk = 5;
	private int framesFall = 0, maxframesFall = 8, indexFall = 0, maxIndexFall = 7;
	public int framesAspire = 0, maxframesAspire = 8, indexAspireOn = 0, maxIndexAspireOn = 2, indexAspireOff = 0, maxIndexAspireOff = 2;
	public boolean aspire = false, aspireOffBool = false, aspireTemp = true;
	public int temp = 0, maxtemp = 120, tempCd = 0, maxTempCd = 60;
	private BufferedImage[] aspireOn;
	private BufferedImage[] aspireOff;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] rightPlayerFall;
	private BufferedImage[] leftPlayerFall;
	private BufferedImage rightPlayerJump;
	private BufferedImage leftPlayerJump;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		animationWalk();
		animationAspire();
		animationJump();
		animationFall();
		
	}
	
	public void tick(){
		stopped = true;
		moved = false;
		//walk
		if(right && World.isFree(this.getX() + (int)speed, this.getY()) && aspire == false) {
			stopped_left = false;
			stopped_right = true;
			stopped = false;
			moved = true;
			dir = right_dir;
			x += speed;
		}
		//walk
		else if(left && World.isFree(this.getX() - (int)speed, this.getY()) && aspire == false) {
			stopped_right = false;
			stopped_left = true;
			stopped = false;
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		//caindo
		if(World.isFree(this.getX(), this.getY() + 2) && jump == false){
				y+= 2;
		//jump
		}if(jump && !World.isFree(this.getX(), this.getY() + 1)) {
			isJumping = true;
		}else {
			jump = false;
		}
		if(isJumping) {
			 if(World.isFree(this.getX(), this.getY())) {
				 y -= 5.5;
				 jumpFrames+=2;
				 if(jumpFrames == jumpHeight) {
					 isJumping = false;
					 jump = false;
					 jumpFrames = 0;
				 }
			 }else {
				 isJumping = false;
				 jump = false;
				 jumpFrames = 0;
			 }
		}
		//index animation walk	
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
		//index fall animation
		if(World.isFree(this.getX(), this.getY() + 2)) {
			framesFall++;
			if(framesFall == maxframesFall) {
				framesFall = 0;
				indexFall++;
				if(indexFall > maxIndexFall) {
					indexFall = 0;
				}
			}
		}
		//index animation aspire	
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
		//cd animation
		if(aspire == false && tempCd > 0) {
			tempCd--;
			aspireTemp = false;
			if(tempCd == 0) {
				aspireTemp = true;
			}
		}
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
		}else if(dir == right_dir && stopped == false  && aspire == false && isJumping == false && !World.isFree(this.getX(), this.getY() + 2)){
			g.drawImage(rightPlayer[indexWalk], this.getX(), this.getY(), null);
		}else if(dir == left_dir && stopped == false  && aspire == false && isJumping == false && !World.isFree(this.getX(), this.getY() + 2)){
			g.drawImage(leftPlayer[indexWalk], this.getX(), this.getY(), null);
		}else if(right == false && left == false){
			if(stopped_right == true && moved == false && isJumping == false){
			g.drawImage(Kirby.spritesheet.getSprite(0, 0, 32, 32), this.getX(), this.getY(), null);
		}else if(stopped_left == true && moved == false && isJumping == false){
			g.drawImage(Kirby.spritesheet.getSprite(32*6, 128-32, 32, 32), this.getX(), this.getY(), null);
		}
		}else if(isJumping && right) {
			g.drawImage(rightPlayerJump, this.getX(), this.getY(), null);
		}else if(isJumping && left) {
			g.drawImage(leftPlayerJump, this.getX(), this.getY(), null);
		}else if(isJumping == false && right == true && World.isFree(this.getX(), this.getY() + 2)) {
			g.drawImage(rightPlayerFall[indexFall], this.getX(), this.getY(), null);
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
	
	void animationJump(){
		rightPlayerJump = Kirby.spritesheet.getSprite(320, 0, 32, 32);
		leftPlayerJump = Kirby.spritesheet.getSprite(608, 32, 32, 32);
	}
	
	void animationFall(){
		rightPlayerFall = new BufferedImage[8];
		leftPlayerFall = new BufferedImage[8];
		for(int i = 0; i<8; i++) {
			rightPlayerFall[i] = Kirby.spritesheet.getSprite(320 + 32 + (i * 32), 0, 32, 32);
		}
		for(int i = 0; i<8; i++) {
			leftPlayerFall[i] = Kirby.spritesheet.getSprite(608 - (i * 32), 32, 32, 32);
			}
	}
}
