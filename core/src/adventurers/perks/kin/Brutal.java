package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Brutal extends Perk {
	
	private int critBonus;
	private int woundBonus;
	
	public Brutal() {
		name = "Brutal";
		critBonus = 10;
		woundBonus = 10;
		description = "Increase your Crit and Wound Rates";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.modBaseCritRate(critBonus);
		player.modBaseWoundRate(woundBonus);
	}
}
