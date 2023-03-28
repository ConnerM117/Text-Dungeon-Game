package adventurers.perks.general;

import mobs.Player;

public class Resilient extends Perk {

	public Resilient() {
		name = "Resilient";
		description = "You are immune to Poison";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setImmuneToPoison(true);
	}

}
