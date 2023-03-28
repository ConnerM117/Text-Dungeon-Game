package adventurers.perks.general;

import mobs.Player;

public class BuiltOx extends Perk {

	private int inventoryBonus;
	
	public BuiltOx() {
		name = "Built Like An Ox";
		inventoryBonus = 3;
		description = "Increase your max inventory capacity by " + inventoryBonus;
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.increaseMaxInventory(inventoryBonus);
	}

}
