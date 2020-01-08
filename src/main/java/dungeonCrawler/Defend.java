package dungeonCrawler;

import java.util.UUID;

public abstract class Defend {
	protected UUID id = UUID.randomUUID();

	public UUID getId() {
		return id;
	}
}
