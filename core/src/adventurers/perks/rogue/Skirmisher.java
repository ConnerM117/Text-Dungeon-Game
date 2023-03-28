package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Skirmisher extends Perk {

	public Skirmisher() {
		name = "Skirmisher";
		description = "Get 2/3 your Dodge as chance to dodge attacks (normally 1/2)";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setSkirmisher(true);
	}

}
