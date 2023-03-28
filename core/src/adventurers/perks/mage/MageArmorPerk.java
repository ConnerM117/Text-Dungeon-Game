package adventurers.perks.mage;

import adventurers.choices.mage.MageArmor;
import adventurers.perks.general.Perk;
import mobs.Player;

public class MageArmorPerk extends Perk {

	public MageArmorPerk() {
		name = "Mage Armor";
		description = "Temporarily increase your Armor";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new MageArmor());
	}

}
