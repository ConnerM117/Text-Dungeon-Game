package adventurers.perks.rogue;

import adventurers.choices.rogue.Pinpoint;
import adventurers.perks.general.Perk;
import mobs.Player;

public class PinpointPerk extends Perk {

	public PinpointPerk() {
		name = "Pinpoint";
		description = "Gain a bonus to Accuracy and Crit Rate, debuff all foes' Dodge";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Pinpoint());
	}

}
