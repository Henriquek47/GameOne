package com.kirby.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx*30, yy*30, Tile.TITLE_FLOOR);
					}else if(pixelAtual == 0xFFFF00DC){
						Kirby.player.setX(xx*32);
						Kirby.player.setY(yy*32);
					}
				}
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext){
		int x1 = xnext/ 32;
		int y1 = ynext/ 32;
		
		int x2 = (xnext + 32-1)/32;
		int y2 = ynext/32;
		
		int x3 = xnext/32;
		int y3 = (ynext+32-1)/32; 
		
		int x4 = (xnext + 32 - 1) / 32;
		int y4 = (ynext + 32 - 1) / 32;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof Floor_Tile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof Floor_Tile ||
				tiles[x3 + (y3*World.WIDTH)] instanceof Floor_Tile ||
				tiles[x4 + (y4*World.WIDTH)] instanceof Floor_Tile
				);
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
