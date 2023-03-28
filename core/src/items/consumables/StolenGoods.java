package items.consumables;

import mobs.Player;

public class StolenGoods extends Consumable {

	public StolenGoods() {
		name = "Stolen Goods";
		description = "A sack full of stolen goods.";
		count = 1;
		cost = 20;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You can't use this here.";
	}

}
