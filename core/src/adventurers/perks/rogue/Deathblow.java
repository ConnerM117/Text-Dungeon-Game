package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Deathblow extends Perk {

	public Deathblow() {
		name = "Deathblow";
		description = "Precise Strike has +40 Crit Rate and +2 Crit Damage";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasDeathblow(true);
	}

}
