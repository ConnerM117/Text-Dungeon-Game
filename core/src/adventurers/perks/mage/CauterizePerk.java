package adventurers.perks.mage;

import adventurers.choices.mage.Cauterize;
import adventurers.perks.general.Perk;
import mobs.Player;

public class CauterizePerk extends Perk {

	public CauterizePerk() {
		name = "Cauterize";
		description = "Heal your wounds and purge Poison.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Cauterize());
	}

}
