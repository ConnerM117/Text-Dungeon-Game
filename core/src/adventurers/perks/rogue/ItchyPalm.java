package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class ItchyPalm extends Perk {

	public ItchyPalm() {
		name = "Itchy Palm";
		description = "Whenever you get coins, you find a little extra";
	}
	@Override
	public void applyBenefits(Player player) {
		player.setHasItchyPalm(true);
	}

}
