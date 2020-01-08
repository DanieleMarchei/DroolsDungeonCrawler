package dungeonCrawler.gameEngine;


public class Map {
	
	private int width;
	private int height;
	private MapElement[][] tiledMap;
	
	public Map(int width, int height, MapElement[][] tiledMap) {
		this.width = width;
		this.height = height;
		this.tiledMap = tiledMap;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public MapElement[][] getTiledMap() {
		return tiledMap;
	}
	
	public void setTiledMap(int row, int col, MapElement element) {
		if(row < 0 || row >= height) return;
		if(col < 0 || col>= width) return;
		
		tiledMap[row][col] = element;
	}

	

}
