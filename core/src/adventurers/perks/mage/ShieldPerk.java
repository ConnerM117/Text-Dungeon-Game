package adventurers.perks.mage;

import adventurers.choices.mage.ShieldSpell;
import adventurers.perks.general.Perk;
import mobs.Player;

public class ShieldPerk extends Perk {

	public ShieldPerk() {
		name = "Shield";
		description = "Gain a bonus to Dodge";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new ShieldSpell());
	}

}
