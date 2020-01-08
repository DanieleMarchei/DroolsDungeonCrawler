package dungeonCrawler.perks;

public abstract class Perk {

	public String getName() {
		String[] split = this.getClass().toString().split("\\.");
		return split[split.length-1];
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Perk)) return false;
		Perk perk = (Perk)obj;
		return perk.getName().equals(this.getName());
			
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	public abstract String getDescription();
}
