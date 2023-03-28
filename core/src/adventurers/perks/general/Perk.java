package adventurers.perks.general;

import mobs.Player;

public abstract class Perk {

	protected String name;
	protected String description;
	
	public abstract void applyBenefits(Player player);
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
}
