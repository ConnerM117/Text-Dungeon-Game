package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Ironhide extends Perk {

	public Ironhide() {
		name = "Ironhide";
		description = "When you Rage, get Temp HP";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasIronhide(true);
	}

}
