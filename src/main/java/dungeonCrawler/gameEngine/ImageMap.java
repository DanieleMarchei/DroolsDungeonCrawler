package dungeonCrawler.gameEngine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageMap {
	
	private BufferedImage image;
	
	public ImageMap(URL url) throws IOException {
		image = ImageIO.read(url);
	}

	
	public Map getMap(HashMap<String, MapElement> conversionTable) {
		int rows = image.getHeight();
		int cols = image.getWidth();
		MapElement[][] map = new MapElement[rows][cols];
		int[] rgbs = image.getRGB(0, 0, cols, rows, null, 0, cols);
		String hex = "";
		MapElement element = null;
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				hex = Integer.toHexString(rgbs[r*cols + c]).substring(2, 8).toUpperCase();
				element = conversionTable.get(hex);
				if (element != null)
					map[r][c] = element;
				else
					map[r][c] = MapElement.WALL;
			}
		}
		
		
		return new Map(cols, rows, map);
	}

	public BufferedImage getImage() {
		return image;
	}

}
