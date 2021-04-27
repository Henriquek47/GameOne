package com.kirby.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kirby.main.Kirby;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH,HEIGHT;
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy * WIDTH)];
					tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx*32, yy*32, Tile.TITLE_FLOOR);
					if(pixelAtual == 0xFF4CFF00){
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx*32, yy*32, Tile.TITLE_FLOOR);
					}else if(pixelAtual == 0xFFFF00DC){
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx*32, yy*32, Tile.TITLE_FLOOR);
					}
				}
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g){
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
				}
			}
		}

}
