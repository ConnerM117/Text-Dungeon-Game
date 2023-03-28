package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Ambush extends Perk {

	public Ambush() {
		name = "Ambush";
		description = "On the first round, Precise Strike automatically crits.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasAmbush(true);
	}

}
