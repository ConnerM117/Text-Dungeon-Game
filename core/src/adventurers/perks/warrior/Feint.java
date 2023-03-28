package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Feint extends Perk {
	
	public Feint() {
		name = "Feint";
		description = "If your attack misses or is dodged, gain a 1-round bonus to Accuracy and Crit Rate";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasFeint(true);
	}

}
