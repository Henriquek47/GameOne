package com.kirby.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kirby.main.Kirby;
import com.kirby.world.World;


public class Player extends Entity{
	
	public boolean right, left, stopped_right, stopped_left;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.5;
	private boolean stopped = true;
	private boolean moved = false;
	private int frames = 0, maxframes = 10, index = 0, maxIndex = 5;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;


	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[6];
		leftPlayer = new BufferedImage[6];
		for(int i = 0; i<6; i++) {
		rightPlayer[i] = Kirby.spritesheet.getSprite(32 + (i * 32), 0, 32, 32);
		}
		for(int i = 0; i<6; i++) {
			leftPlayer[i] = Kirby.spritesheet.getSprite(32 + (i * 32), 128-32, 32, 32);
			}
		
	}
	
	public void tick(){
		stopped = true;
		moved = false;
		if(right && World.isFree(this.getX() + (int)speed, this.getY())) {
			stopped = false;
			moved = true;
			dir = right_dir;
			x += speed;
		} 	
		else if(left && World.isFree(this.getX() - (int)speed, this.getY())) {
			stopped = false;
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if(World.isFree(this.getX(), this.getY() + (int)speed)){
			y+=speed;
		}
		if(moved) {
			frames++;
			if(frames == maxframes) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(dir == right_dir && stopped == false){
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		}else if(dir == left_dir && stopped == false){
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		}else{
			//implementar stopped right e left
			g.drawImage(Kirby.spritesheet.getSprite(32*6, 128-32, 32, 32), this.getX(), this.getY(), null);
		}	
	}

}
