package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class NotTheFace extends Perk {

	public NotTheFace() {
		name = "Not The Face!";
		description = "You can't be critically hit.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setIsImmuneToCrit(true);
	}

}
