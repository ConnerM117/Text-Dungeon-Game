package items.consumables;

import mobs.Player;

public class RatBag extends Consumable {

	public RatBag() {
		name = "Rat in a Bag";
		description = "Exactly what it sounds like";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 18;
	}

	public String useItem(Player player) {
		player.removeFromInventory(this);
		return "You set loose the rat, which quickly escapes into the dungeon.\nWhat was that about?";
	}
}
