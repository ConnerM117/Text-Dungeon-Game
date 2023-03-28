package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class SkeletonKey extends Perk {

	public SkeletonKey() {
		name = "Skeleton Key";
		description = "Always succeed when picking locks and disarming traps.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasSkeletonKey(true);
	}

}
