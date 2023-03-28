package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Adaptable extends Perk {

	private int staminaBuff;
	
	public Adaptable() {
		name = "Adaptable";
		staminaBuff = 1;
		description = "Increase your max stamina by " + staminaBuff;
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.modMaxStamina(staminaBuff);
	}
}
