package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class GuidedSmite extends Perk {

	public GuidedSmite() {
		name = "Guided Smite";
		description = "Your Smite can't be dodged";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasGuidedSmite(true);
	}

}
