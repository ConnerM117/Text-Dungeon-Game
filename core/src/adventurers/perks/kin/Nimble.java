package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Nimble extends Perk {

	public Nimble() {
		name = "Nimble";
		description = "Fleeing has a chance to not spend Stamina.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setNimble(true);
	}
}
