package adventurers.perks.kin;

import mobs.Player;

public class DwarfResilient extends adventurers.perks.general.Perk {
	
	public DwarfResilient() {
		name = "Resilient";
		description = "Gain a bonus to Armor.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setResilient(true);
	}
}
