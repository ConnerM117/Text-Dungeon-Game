package items.consumables;

import mobs.Player;

public class AlchemySupplies extends Consumable {

	public AlchemySupplies() {
		name = "Alchemy Supplies";
		description = "Tools used to create alchemical substances.";
		count = 1;
		cost = 5;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You can't use this here.";
	}
}
