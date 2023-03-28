package adventurers.perks.mage;

import adventurers.choices.mage.FlameCloak;
import adventurers.perks.general.Perk;
import mobs.Player;

public class FlameCloakPerk extends Perk {

	public FlameCloakPerk() {
		name = "Flame Cloak";
		description = "Foes that attack you might catch fire!";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new FlameCloak());
	}

}
