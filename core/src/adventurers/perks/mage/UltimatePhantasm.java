package adventurers.perks.mage;

import adventurers.perks.general.Perk;
import mobs.Player;

public class UltimatePhantasm extends Perk {

	public UltimatePhantasm() {
		name = "Ultimate Phantasm";
		description = "Phantasm targets all foes.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasUltimatePhantasm(true);
	}

}
