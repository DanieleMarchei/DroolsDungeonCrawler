package dungeonCrawler.gameEngine;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import dungeonCrawler.characters.*;
import dungeonCrawler.perks.*;

public class GameWindow extends Canvas{
	
	private static final long serialVersionUID = 1L;
	private GameState state = GameState.MENU;
	private Map map = null;
	private HashMap<MapElement, Image> textureMap = new HashMap<>();
	private HashMap<BattleElement, Image> textureBattle = new HashMap<>();
	private HashMap<MenuElement, Image> textureMenu = new HashMap<>();
	private HashMap<Perk, Image> texturePerk = new HashMap<>();
	private ArrayList<int[]> cursorPositions = new ArrayList<int[]>();
	private int cursorIndex = 0;
	private int rectSize = 64;
	private PG pg = null;
	private int points = 10;
	private Enemy enemy = null;
	private int time = 0;
	private Perk[] perksForDescription = new Perk[] {new Petrification(), new CriticalHit(), new DieHard(), new Rage(),
													 new Healer(), new CollateralDamage(), new Swap(), new Elusion()};
	private ArrayList<String> briefRecap = new ArrayList<>() ;
	
	public GameWindow(Map map) {
		this.map = map;
		
		pg = new PG();
		pg.setAttack(1);
		pg.setDefence(1);
		pg.setSpeed(1);
		pg.setHpMax(1);
		pg.setHpCurrent(1);
		pg.setCritical(0.03);
		pg.setAccuracy(1);
		
		this.cursorPositions.add(new int[] {23,168});
		this.cursorPositions.add(new int[] {23,195});
		this.cursorPositions.add(new int[] {23,225});
		this.cursorPositions.add(new int[] {144,168});
		this.cursorPositions.add(new int[] {23,270});
		this.cursorPositions.add(new int[] {23,390});
		this.cursorPositions.add(new int[] {140,270});
		this.cursorPositions.add(new int[] {140,390});
		this.cursorPositions.add(new int[] {280,270});
		this.cursorPositions.add(new int[] {280,390});
		this.cursorPositions.add(new int[] {400,270});
		this.cursorPositions.add(new int[] {400,390});
	}
	
	@Override
	public void paint(Graphics g) {
		switch(state) {
		case MENU:
			//DRAW BACKGROUND
			g.drawImage(textureMenu.get(MenuElement.BACKGROUND), 0, 0, this);
			
			//DRAW CURSOR
			int[] cursorPosition = cursorPositions.get(cursorIndex);
			g.drawImage(textureMenu.get(MenuElement.CURSOR), cursorPosition[0], cursorPosition[1], this);
			
			//DRAW POINTS
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 18));
			g.drawString(points + "", 106, 148);
			
			//DRAW STATS
			g.drawString((int)pg.getAttack() + "", 106, 178);
			g.drawString((int)pg.getDefence() + "", 106, 207);
			g.drawString((int)pg.getSpeed() + "", 106, 236);
			g.drawString((int)pg.getHpMax() + "", 200, 178);
			
			//DRAW PERK DESCRIPTION
			if(cursorIndex < 4) break;
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 30));
			g.drawString(perksForDescription[cursorIndex-4].getName(), 523, 290);
			
			String description = perksForDescription[cursorIndex-4].getDescription();
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 18));
			if(description.length() > 30) {
				int limit = (int)Math.ceil(description.length() / 30.0);
				for(int i = 0; i < limit; i++) {
					String s = description.substring(i*30, Math.min(i*30+30,description.length())).trim();
					g.drawString(s, 523, 320 + i*25);
				}
				
			}else {				
				g.drawString(description, 523, 320);
			}
			break;
		case WALKING:
			for(int r = 0; r < map.getHeight(); r++) {
				for(int c = 0; c < map.getWidth(); c++) {
					g.drawImage(textureMap.get(MapElement.FLOOR), c*rectSize, r*rectSize, this);
					g.drawImage(textureMap.get(map.getTiledMap()[r][c]), c*rectSize, r*rectSize, this);
				}
			}
			break;
		case BATTLE_BOSS:
		case BATTLE_FOE:
			//DRAW TEXTURES
			g.drawImage(textureBattle.get(BattleElement.BACKGROUND), 0, 0, this);
			g.drawImage(textureBattle.get(BattleElement.PG), 100, 200, this);
			if(state == GameState.BATTLE_FOE)
				g.drawImage(textureBattle.get(BattleElement.FOE), 600, 200, this);
			else
				g.drawImage(textureBattle.get(BattleElement.BOSS), 600, 200, this);
			
			//DRAW HP BARS
			//	pg
			g.setColor(Color.WHITE);
			g.fillRoundRect(97, 200-20, 128, 15,7,7);
			g.setColor(Color.RED);
			g.fillRoundRect(100, 200-17, (int)((double)pg.getHpCurrent() / pg.getHpMax() * 122), 9,7,7);
			g.setColor(Color.WHITE);
			DecimalFormat df = new DecimalFormat("#.0");
			g.drawString(df.format(pg.getHpCurrent()), 228, 200-8);
			
			g.drawImage(texturePerk.get(pg.getPerk()), 70, 200-23, this);
			
			// enemy
			g.setColor(Color.WHITE);
			g.fillRoundRect(600-3, 200-20, 128, 15,7,7);
			g.setColor(Color.RED);
			g.fillRoundRect(600, 200-17, (int)((double)enemy.getHpCurrent() / enemy.getHpMax() * 122), 9,7,7);
			g.setColor(Color.WHITE);
			g.drawString(df.format(enemy.getHpCurrent()), 730, 200-8);
			
			g.drawImage(texturePerk.get(enemy.getPerk()), 570, 200-23, this);
			if(state == GameState.BATTLE_BOSS)
				g.drawImage(texturePerk.get(((Boss)enemy).getPerk2()), 543, 200-23, this);
			
			//DRAW CURRENT STATS
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 20));
			g.drawString((int)pg.getAttack() + "", 90, 427);
			g.drawString((int)pg.getDefence() + "", 220, 427);
			g.drawString((int)pg.getSpeed() + "", 350, 427);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 18));
			g.drawString("ATK : " + (int)enemy.getAttack(), 740, 240);
			g.drawString("DEF : " + (int)enemy.getDefence(), 740, 260);
			g.drawString("SPE : " + (int)enemy.getSpeed(), 740, 280);
			
			//DRAW cursor POSITION
			if(this.cursorIndex == 0)
				g.drawImage(textureMenu.get(MenuElement.CURSOR), 25, 445, this);
			else
				g.drawImage(textureMenu.get(MenuElement.CURSOR), 25, 470, this);
			
			//DRAW A BRIEF RECAP
			g.setColor(Color.WHITE);
			g.setFont(new Font(g.getFont().getFontName(), 0, 16));
			for(int i = 0; i < briefRecap.size(); i++)
				g.drawString(briefRecap.get(i), 435, 410 + i*13);
			break;
		case WIN:
			g.setColor(Color.ORANGE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.BLACK);
			g.setFont(new Font("Comic Sans MS", 0, 40));
			g.drawString("YOU WIN!!", this.getWidth() / 2 - 100, this.getHeight() / 2);
			break;			
		case LOST:
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.RED);
			g.setFont(new Font("Book Antiqua", 0, 40));
			g.drawString("GAME OVER", 560, 490);
			break;

		}

	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	public int getCursorIndex() {
		return cursorIndex;
	}
	
	public void setCursorIndex(int value) {
		this.cursorIndex = value;
		if(this.cursorIndex < 0)
			this.cursorIndex = 0;
	}

	public void increaseCursorIndex() {
		this.cursorIndex += 1;
		if(cursorIndex >= cursorPositions.size())
			cursorIndex = cursorPositions.size() - 1;
		repaint();
	}
	
	public void decreaseCursorIndex() {
		this.cursorIndex -= 1;
		if(cursorIndex <= 0)
			cursorIndex = 0;
		repaint();
	}

	public int getPoints() {
		return points;
	}

	public void increasePoints() {
		this.points += 1;
	}

	public void decreasePoints() {
		this.points -= 1;
	}
	
	public ArrayList<String> getBriefRecap() {
		return briefRecap;
	}
	
	public void clearRecap() {
		briefRecap.clear();
	}
	
	public void addRecap(String recap) {
		if(briefRecap.size() >= 7) {
			briefRecap.remove(0);
		}
		this.briefRecap.add(recap);
		repaint();
	}
	
	public void addTexture(MapElement element, Image image) {
		textureMap.put(element, image);
	}
	
	public void addTexture(BattleElement element, Image image) {
		textureBattle.put(element, image);
	}
	
	public void addTexture(MenuElement element, Image image) {
		textureMenu.put(element, image);
	}
	
	public void addTexture(Perk perk, Image image) {
		texturePerk.put(perk, image);
	}
	
	public void increaseTime() {
		time++;
	}
	
	public int getTime() {
		return time;
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
		repaint();
	}
	

	public Map getMap() {
		return map;
	}

	
	public void setMap(Map map) {
		this.map = map;
	}

	public PG getPg() {
		return pg;
	}

	public void setPg(PG pg) {
		this.pg = pg;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	
	
}