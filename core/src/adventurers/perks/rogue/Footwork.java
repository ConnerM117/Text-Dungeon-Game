package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Footwork extends Perk {

	public Footwork() {
		name = "Footwork";
		description = "Buff Dodge for 2 rounds whenever you use a normal Attack";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasFootwork(true);
	}

}
