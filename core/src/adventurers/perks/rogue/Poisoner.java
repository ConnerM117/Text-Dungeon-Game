package adventurers.perks.rogue;

import adventurers.choices.rogue.ApplyPoison;
import adventurers.perks.general.Perk;
import mobs.Player;

public class Poisoner extends Perk {

	public Poisoner() {
		name = "Poisoner";
		description = "Use an action to poison your weapon.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new ApplyPoison());
	}

}
