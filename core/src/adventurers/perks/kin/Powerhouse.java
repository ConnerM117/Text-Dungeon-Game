package adventurers.perks.kin;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Powerhouse extends Perk {

	private int inventoryBonus;
	
	public Powerhouse() {
		name = "Powerhouse";
		inventoryBonus = 2;
		description = "Gain a bonus to succeed on Strength actions and increase max inventory.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.increaseMaxInventory(inventoryBonus);
		player.setPowerhouse(true);
	}
}
