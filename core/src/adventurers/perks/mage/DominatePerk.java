package adventurers.perks.mage;

import adventurers.choices.mage.Dominate;
import adventurers.perks.general.Perk;
import mobs.Player;

public class DominatePerk extends Perk {

	public DominatePerk() {
		name = "Dominate";
		description = "Spend 1 Stamina to become Protected by a target you choose.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Dominate());
	}

}
