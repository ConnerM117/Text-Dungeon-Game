package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Bloodlust extends Perk {

	private int woundRateBuff;
	
	public Bloodlust() {
		name = "Bloodlust";
		woundRateBuff = 10;
		description = "+" + woundRateBuff + " Wound Rate. Increased Accuracy and Crit Rate when attacking a Wounded target.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasBloodlust(true);
		player.modBaseWoundRate(woundRateBuff);
	}

}
