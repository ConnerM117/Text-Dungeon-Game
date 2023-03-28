package adventurers.perks.mage;

import adventurers.perks.general.Perk;
import mobs.Player;

public class FlameWard extends Perk {

	public FlameWard() {
		name = "Flame Ward";
		description = "Become immune to Burning";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasFlameWard(true);
	}

}
