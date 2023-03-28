package adventurers.perks.general;

import mobs.Player;

public class StatBoost extends Perk {
	
	public StatBoost() {
		name = "Stat Boost";
		description = "Get Another Stat Boost. This counts against your stat boost limits.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		//player.getAdventurer().chooseStatBonus(player);
		//TODO implement. eliminate Stat Boost as a choice entirely, and replace with two Kin perks?
	}

}
