package items.consumables;

import mobs.Player;

public class Rope extends Consumable {
	
	public Rope() {
		name = "Rope";
		description = "Can be used to potentially overcome some obstacles";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 20;
	}
	
	public String useItem(Player player) {
		return "You can't use " + name + " here!";
	}

}
