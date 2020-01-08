package dungeonCrawler.gameEngine;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.kie.api.runtime.KieSession;

import dungeonCrawler.*;
import dungeonCrawler.perks.*;


public class GameEngine extends JFrame{

	private static final long serialVersionUID = 1L;
	private GameWindow gameWindow = null;
	private int rectSize = 64;
	private KieSession kSession = null;
	
	public GameEngine(String title, Map map, KieSession kSession) {
		setLayout(new BorderLayout());
		setSize(map.getWidth()*rectSize + 15, map.getHeight()*rectSize + 39);
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.kSession = kSession;
		kSession.insert(map);
		
		gameWindow = new GameWindow(map);
		gameWindow.setState(GameState.MENU);
		gameWindow.addTexture(MapElement.BOSS, new ImageIcon(GameEngine.class.getResource("/Boss.png")).getImage());
		gameWindow.addTexture(MapElement.DOOR, new ImageIcon(GameEngine.class.getResource("/Door.png")).getImage());
		gameWindow.addTexture(MapElement.FLOOR, new ImageIcon(GameEngine.class.getResource("/Floor.png")).getImage());
		gameWindow.addTexture(MapElement.FOE, new ImageIcon(GameEngine.class.getResource("/Foe.png")).getImage());
		gameWindow.addTexture(MapElement.KEY, new ImageIcon(GameEngine.class.getResource("/Key.png")).getImage());
		gameWindow.addTexture(MapElement.PG, new ImageIcon(GameEngine.class.getResource("/PG.png")).getImage());
		gameWindow.addTexture(MapElement.WALL, new ImageIcon(GameEngine.class.getResource("/Wall.png")).getImage());
		gameWindow.addTexture(BattleElement.BACKGROUND, new ImageIcon(GameEngine.class.getResource("/Battaglia.png")).getImage());
		gameWindow.addTexture(BattleElement.FOE, new ImageIcon(GameEngine.class.getResource("/FoeBig.png")).getImage());
		gameWindow.addTexture(BattleElement.BOSS, new ImageIcon(GameEngine.class.getResource("/BossBig.png")).getImage());
		gameWindow.addTexture(BattleElement.PG, new ImageIcon(GameEngine.class.getResource("/PGBig.png")).getImage());
		gameWindow.addTexture(MenuElement.BACKGROUND, new ImageIcon(GameEngine.class.getResource("/Menu.png")).getImage());
		gameWindow.addTexture(MenuElement.CURSOR, new ImageIcon(GameEngine.class.getResource("/Dot.png")).getImage());
		gameWindow.addTexture(new CollateralDamage(), new ImageIcon(GameEngine.class.getResource("/CollateralDamage.png")).getImage());
		gameWindow.addTexture(new CriticalHit(), new ImageIcon(GameEngine.class.getResource("/CriticalHit.png")).getImage());
		gameWindow.addTexture(new DieHard(), new ImageIcon(GameEngine.class.getResource("/Lucario.png")).getImage());
		gameWindow.addTexture(new Elusion(), new ImageIcon(GameEngine.class.getResource("/Avoid.png")).getImage());
		gameWindow.addTexture(new Healer(), new ImageIcon(GameEngine.class.getResource("/Regen.png")).getImage());
		gameWindow.addTexture(new Petrification(), new ImageIcon(GameEngine.class.getResource("/Havel.png")).getImage());
		gameWindow.addTexture(new Rage(), new ImageIcon(GameEngine.class.getResource("/Rage.png")).getImage());
		gameWindow.addTexture(new Swap(), new ImageIcon(GameEngine.class.getResource("/Swap.png")).getImage());
		kSession.insert(gameWindow);	
		
		kSession.fireAllRules();
		
	}
	
	public void start() {
		add("Center", gameWindow);
		gameWindow.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				KeyPress kp = new KeyPress();
				kp.setCode(e.getKeyCode());
				kSession.insert(kp);
				kSession.fireAllRules();
				update();

			}
		});
		
		gameWindow.setFocusable(true);
		setFocusable(true);
		setVisible(true);
		gameWindow.requestFocus();
		requestFocus();
	}
	
	public void update() {
		gameWindow.repaint();
	}
	
	public int getRectSize() {
		return rectSize;
	}
}