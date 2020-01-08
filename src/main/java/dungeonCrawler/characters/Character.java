package dungeonCrawler.characters;

import dungeonCrawler.perks.Perk;

public abstract class Character {
	protected double hpMax;
	protected double hpCurrent;
	protected double attack;
	protected double defence;
	protected double speed;
	protected double critical;
	protected double accuracy;
	protected Perk perk;

	
	public double getHpMax() {
		return hpMax;
	}
	
	public void setHpMax(double hpMax) {
		this.hpMax = hpMax;
		if(this.hpMax >= 11) this.hpMax = 11;
	}
	
	public double getHpCurrent() {
		return hpCurrent;
	}
	
	public void setHpCurrent(double hpCurrent) {
		this.hpCurrent = Math.min(hpCurrent, hpMax);
		if(this.hpCurrent <= 0) this.hpCurrent = 0;
		if(this.hpCurrent >= 11) this.hpCurrent = 11;
	}
	
	public double getAttack() {
		return attack;
	}
	
	public void setAttack(double attack) {
		this.attack = attack;
		if(this.attack <= 1) this.attack = 1;
		if(this.attack >= 11) this.attack = 11;
	}
	
	public double getDefence() {
		return defence;
	}
	
	public void setDefence(double defence) {
		this.defence = defence;
		if(defence <= 1) this.defence = 1;
		if(this.defence >= 11) this.defence = 11;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
		if(speed <= 1) this.speed = 1;
		if(this.speed >= 11) this.speed = 11;
	}
	
	public double getCritical() {
		return critical;
	}
	
	public void setCritical(double critical) {
		this.critical = critical;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	public Perk getPerk() {
		return perk;
	}

	public void setPerk(Perk perk) {
		this.perk = perk;
	}
	
	@Override
	public String toString() {
		String result = "\tHP : %f / %f\n\tATK : %f\n\tDEF : %f\n\tSPE : %f\n\tPerk : %s\n";
		return String.format(result, hpCurrent, hpMax, attack, defence, speed,perk.getName());
	}

}
