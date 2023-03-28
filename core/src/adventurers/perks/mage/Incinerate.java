package adventurers.perks.mage;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Incinerate extends Perk {

	public Incinerate() {
		name = "Incinerate";
		description = "Fireball can cause targets to Burn";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasIncinerate(true);
	}

}
