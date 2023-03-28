package adventurers.perks.mage;

import adventurers.choices.mage.Invisibility;
import adventurers.perks.general.Perk;
import mobs.Player;

public class InvisibilityPerk extends Perk {

	public InvisibilityPerk() {
		name = "Invisibility";
		description = "Turn invisible to hide from your foes.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Invisibility());
	}

}
