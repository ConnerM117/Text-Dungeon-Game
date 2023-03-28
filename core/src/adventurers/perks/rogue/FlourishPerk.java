package adventurers.perks.rogue;

import adventurers.choices.rogue.Flourish;
import adventurers.perks.general.Perk;
import mobs.Player;

public class FlourishPerk extends Perk {

	public FlourishPerk() {
		name = "Flourish";
		description = "+20 Dodge and remove all self debuffs";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Flourish());
	}

}
