package adventurers.perks.mage;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Sacrifice extends Perk {

	public Sacrifice() {
		name = "Sacrifice";
		description = "If you defeat a foe with a normal Attack, it may trigger a Dark Bargain with no cost.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasSacrifice(true);
	}

}
