package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class DeepPockets extends Perk {

	private int inventoryBuff;
	
	public DeepPockets() {
		name = "Deep Pockets";
		inventoryBuff = 2;
		description = "Increase your inventory space by " + inventoryBuff;
	}
	@Override
	public void applyBenefits(Player player) {
		player.increaseMaxInventory(inventoryBuff);
	}

}
