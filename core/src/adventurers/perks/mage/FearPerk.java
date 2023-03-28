package adventurers.perks.mage;

import adventurers.choices.mage.Fear;
import adventurers.perks.general.Perk;
import mobs.Player;

public class FearPerk extends Perk {

	public FearPerk() {
		name = "Fear";
		description = "Your target takes debuffs to Accuracy, Crit Rate, and Mind";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Fear());
	}

}
