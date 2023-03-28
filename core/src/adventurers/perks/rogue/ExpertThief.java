package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class ExpertThief extends Perk {

	public ExpertThief() {
		name = "Expert Thief";
		description = "Pickpocket gives you a choice between three items";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setExpertThief(true);
	}

}
