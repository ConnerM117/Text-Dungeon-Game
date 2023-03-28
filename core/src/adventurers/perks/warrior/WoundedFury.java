package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class WoundedFury extends Perk {
	
	private int woundedFuryCritBonus;
	
	public WoundedFury() {
		name = "Wounded Fury";
		woundedFuryCritBonus = 3;
		description = "For each point of HP missing, gain +" + woundedFuryCritBonus + " to Crit Rate";
	}

	@Override
	public void applyBenefits(Player player) {
		player.setHasWoundedFury(true);
		player.setWoundedFuryCritBonus(woundedFuryCritBonus);
	}

}
