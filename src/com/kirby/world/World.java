package com.kirby.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kirby.entities.Enemy;
import com.kirby.entities.Entity;
import com.kirby.main.Kirby;

public class World {
	
	private static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	BufferedImage background;
	public static final int TITLE_SIZE = 32;
	
	public World(String path, String path2){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			background = ImageIO.read(getClass().getResource(path2));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy * WIDTH)];
					tiles[xx + (yy * WIDTH)] = new Tile(xx*32, yy*32, null);
					if(pixelAtual == 0xFF4CFF00){
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx*32, yy*30, Tile.TITLE_FLOOR);
					}else if(pixelAtual == 0xFFFF00DC){
						Kirby.player.setX(xx*32);
						Kirby.player.setY(yy*32);
					}else if(pixelAtual == 0xFFFFD800){
						tiles[xx + (yy * WIDTH)] = new Tile(xx*32, (int) (yy*30.3), Tile.TITLE_FLOOR_MORE);
					}else if(pixelAtual == 0xFFFF0000){
						Kirby.entities.add(new Enemy(xx*32, yy*32, 32, 32, Entity.ENEMY_SPRITESHEET));
					}
				}
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext){
		int x1 = xnext/TITLE_SIZE;
		int Y1 = ynext/TITLE_SIZE;
		
		int x2 = (xnext + TITLE_SIZE - 1)/TITLE_SIZE;
		int Y2 = ynext/TITLE_SIZE;
		
		int x3 = xnext/TITLE_SIZE;
		int Y3 = (ynext + TITLE_SIZE - 1)/TITLE_SIZE;
		
		int x4 = (xnext + TITLE_SIZE - 1)/TITLE_SIZE;
		int Y4 = (ynext + TITLE_SIZE - 1)/TITLE_SIZE;
		
		return !(tiles[x1 + (Y1*World.WIDTH)] instanceof Floor_Tile ||
				 tiles[x2 + (Y2*World.WIDTH)] instanceof Floor_Tile ||
				 tiles[x3 + (Y3*World.WIDTH)] instanceof Floor_Tile ||
				 tiles[x4 + (Y4*World.WIDTH)] instanceof Floor_Tile);
	}
	
	public void render(Graphics g){
		g.drawImage(background, 0, 0, Kirby.WIDTH, Kirby.HEIGHT, null);
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
				}
			}
		}

}
