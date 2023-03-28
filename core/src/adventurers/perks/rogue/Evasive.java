package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Evasive extends Perk {

	public Evasive() {
		name = "Evasive";
		description = "You are immune to Burning";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setEvasive(true);
	}

}
