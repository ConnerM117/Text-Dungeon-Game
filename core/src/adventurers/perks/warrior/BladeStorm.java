package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class BladeStorm extends Perk {

	public BladeStorm() {
		name = "BladeStorm";
		description = "Parry-Riposte targets each foe that attacks you this round";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasBladeStorm(true);
	}

}
