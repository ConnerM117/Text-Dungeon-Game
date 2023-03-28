package adventurers.perks.rogue;

import adventurers.choices.rogue.Scout;
import adventurers.perks.general.Perk;
import mobs.Player;

public class ScoutPerk extends Perk {

	public ScoutPerk() {
		name = "Scout";
		description = "When you enter a new room, randomly identify one of its adjacent rooms."
				+ "\n\tIn combat, remove enemy buffs and bypass Hidden.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setScout(true);
		player.addCombatChoice(new Scout());
	}

}
