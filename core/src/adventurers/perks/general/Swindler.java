package adventurers.perks.general;

import mobs.Player;

public class Swindler extends Perk {

	public Swindler() {
		name = "Swindler";
		description = "Get a discount at Shops";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setSwindler(true);
	}

}
