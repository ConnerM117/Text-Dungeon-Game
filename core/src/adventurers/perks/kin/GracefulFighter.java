package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class GracefulFighter extends Perk {

	public GracefulFighter() {
		name = "Graceful Fighter";
		description = "Gain a bonus to Dodge while dual-wielding or you have a hand free.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setGracefulFighter(true);
	}
}
