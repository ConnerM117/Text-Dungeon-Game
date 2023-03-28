package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class ToughRage extends Perk {
	
	public ToughRage() {
		name = "Tough Rage";
		description = "Raging also increases your Toughness";
	}

	@Override
	public void applyBenefits(Player player) {
		player.setHasToughRage(true);
	}

}
