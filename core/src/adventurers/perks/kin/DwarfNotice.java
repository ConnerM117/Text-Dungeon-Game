package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class DwarfNotice extends Perk {

	public DwarfNotice() {
		name = "Eye For Detail";
		description = "More easily notice secrets and traps.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setEyeForDetail(true);
	}
}
