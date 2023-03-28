package adventurers.perks.mage;

import adventurers.choices.mage.Ward;
import adventurers.perks.general.Perk;
import mobs.Player;

public class WardPerk extends Perk {

	public WardPerk() {
		name = "Ward";
		description = "Grant yourself a Hit Point shield";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Ward());
	}

}
