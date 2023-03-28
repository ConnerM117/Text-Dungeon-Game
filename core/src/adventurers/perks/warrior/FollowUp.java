package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class FollowUp extends Perk {

	public FollowUp() {
		name = "Follow Up";
		description = "If your attack hits and deals damage, gain a bonus to Crit Rate for 1 round";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasFollowUp(true);
	}

}
