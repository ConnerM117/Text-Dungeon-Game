package adventurers.perks.mage;

import adventurers.perks.general.Perk;
import mobs.Player;

public class FeedTheFlames extends Perk {

	public FeedTheFlames() {
		name = "Feed The Flames";
		description = "If Fireball defeats a foe, gain a Hit Point shield.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasFeedTheFlames(true);
	}

}
