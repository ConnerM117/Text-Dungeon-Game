package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Phantom extends Perk {

	public Phantom() {
		name = "Phantom";
		description = "Hiding doesn't take your action.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasPhantom(true);
	}

}
