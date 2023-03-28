package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class CleavePerk extends Perk {

	public CleavePerk() {
		name = "Cleave";
		description = "When you Attack, if the target is defeated, attack again.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasCleave(true);
	}

}
