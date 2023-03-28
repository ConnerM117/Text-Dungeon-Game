package items.consumables;

import mobs.Player;

public class Lockpicks extends Consumable {
	
	public Lockpicks() {
		name = "Lockpicks";
		description = "Can be used to potentially open a locked chest or door without the key";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 14;
	}
	
	public String useItem(Player player) {
		return "You can't use " + name + " here!";
	}

}
