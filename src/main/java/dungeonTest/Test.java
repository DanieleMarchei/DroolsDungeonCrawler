package dungeonTest;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import dungeonCrawler.gameEngine.GameEngine;
import dungeonCrawler.gameEngine.ImageMap;
import dungeonCrawler.gameEngine.Map;
import dungeonCrawler.gameEngine.MapElement;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) throws IOException{
		
        KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	KieSession kSession = kContainer.newKieSession("ksession-rules");      

    	
		ImageMap imageMap = new ImageMap(Test.class.getResource("/MapOriginal.png"));
		HashMap<String, MapElement> table = new HashMap<>();
		table.put("FFFFFF", MapElement.WALL);
		table.put("000000", MapElement.FLOOR);
		table.put("808080", MapElement.DOOR);
		table.put("B6FF00", MapElement.KEY);
		table.put("F0EF0E", MapElement.FOE);
		table.put("00B055", MapElement.BOSS);
		table.put("FF0000", MapElement.PG);
		
		Map map = imageMap.getMap(table);
		
		GameEngine engine = new GameEngine("Drools Dungeon Crawler", map, kSession);

		engine.start();
		return;
		
	}
	

	

	

}
