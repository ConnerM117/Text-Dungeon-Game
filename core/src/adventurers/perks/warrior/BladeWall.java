package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class BladeWall extends Perk {

	public BladeWall() {
		name = "Blade Wall";
		description = "Parry-Riposte also grants Armor";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasBladeWall(true);
	}

}
