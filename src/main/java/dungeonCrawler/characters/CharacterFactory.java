package dungeonCrawler.characters;

import java.util.HashMap;
import java.util.Random;

import dungeonCrawler.perks.*;

public final class CharacterFactory {
	
	private static Perk randomPerk() {
		Random r = new Random();
		switch(r.nextInt(8)) {
		case 0:
			return new DieHard();
		case 1:
			return new Petrification();
		case 2:
			return new Rage();
		case 3:
			return new Swap();
		case 4:
			return new Healer();
		case 5:
			return new CollateralDamage();
		case 6:
			return new Elusion();
		case 7:
			return new CriticalHit();
		default:
			return null;
		}
		
	}
	
	private static HashMap<String, Double> randomCharacteristics(){
		Random r = new Random();
		int points = 10;
		
		double hpMax = r.nextInt(points);
		points -= hpMax;
		hpMax += 1;
		
		double atk = r.nextInt(points);
		points -= atk;
		atk += 1;
		
		double def = r.nextInt(points);
		points -= def;
		def += 1;
		
		double spe = r.nextInt(points);
		points -= spe;
		spe += 1;
		HashMap<String, Double> c = new HashMap<>();
		c.put("HP_MAX", hpMax);
		c.put("ATK", atk);
		c.put("DEF", def);
		c.put("SPE", spe);
		
		
		return c;
	}
	
	private static final double CRIT = 0.3;
	private static final double ACC= 1.0;
	
	public static Foe getRandomFoe() {
		
		HashMap<String, Double> c = randomCharacteristics();
		
		Foe foe = new Foe();
		foe.setHpMax(c.get("HP_MAX").intValue());
		foe.setHpCurrent(foe.getHpMax());
		foe.setAttack(c.get("ATK").intValue());
		foe.setDefence(c.get("DEF").intValue());
		foe.setSpeed(c.get("SPE").intValue());
		foe.setCritical(CRIT);
		foe.setAccuracy(ACC);
		foe.setPerk(randomPerk());
		if(foe.getPerk().equals(new CriticalHit()))
		{
			foe.setHpMax(foe.getHpMax() / 2);
			foe.setHpCurrent(foe.getHpMax());
			foe.setCritical(0.15);
		}
		
		return foe;
	}
	
	public static Boss getRandomBoss() {
		
		HashMap<String, Double> c = randomCharacteristics();
		
		Boss boss = new Boss();
		boss.setHpMax(c.get("HP_MAX").intValue() + 2);
		boss.setHpCurrent(boss.getHpMax());
		boss.setAttack(c.get("ATK").intValue() + 1);
		boss.setDefence(c.get("DEF").intValue() + 1);
		boss.setSpeed(c.get("SPE").intValue() +1 );
		boss.setCritical(CRIT);
		boss.setAccuracy(ACC);
		boss.setPerk(randomPerk());
		if(boss.getPerk().equals(new CriticalHit()))
		{
			boss.setHpMax(boss.getHpMax() / 2);
			boss.setHpCurrent(boss.getHpMax());
			boss.setCritical(0.15);
		}
		
		do {
			boss.setPerk2(randomPerk());
		}while(boss.getPerk().equals(boss.getPerk2()));
		
		if(boss.getPerk2().equals(new CriticalHit()))
		{
			boss.setHpMax(boss.getHpMax() / 2);
			boss.setHpCurrent(boss.getHpMax());
			boss.setCritical(0.15);
		}
		
		return boss;
	}
	
}
