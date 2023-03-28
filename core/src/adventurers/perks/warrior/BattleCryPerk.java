package adventurers.perks.warrior;

import adventurers.choices.warrior.BattleCry;
import adventurers.perks.general.Perk;
import mobs.Player;

public class BattleCryPerk extends Perk {
	
	public BattleCryPerk() {
		name = "Battle Cry";
		description = "Temporarily increase your Accuracy and Crit Rate in combat";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new BattleCry());
	}

}
