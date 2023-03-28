package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class TrapExpert extends Perk {

	public TrapExpert() {
		name = "Trap Expert";
		description = "Automatically detect traps.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setTrapExpert(true);
	}

}
