package adventurers.perks.rogue;

import adventurers.choices.rogue.Ballad;
import adventurers.perks.general.Perk;
import mobs.Player;

public class BalladPerk extends Perk {

	public BalladPerk() {
		name = "Ballad";
		description = "Choose a stat and temporarily increase it";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Ballad());
	}

}
