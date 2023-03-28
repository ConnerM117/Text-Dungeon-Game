package items.consumables;

import mobs.Player;

public class WyvernVenom extends Consumable {

	private int damage;
	private int weaponDuration;
	
	public WyvernVenom() {
		name = "Wyvern Venom";
		damage = 2;
		weaponDuration = 5;
		description = "Poisons your weapons for " + weaponDuration + " rounds. "
				+ "\n\tOn hit, deals " + damage + " damage per round.";
		count = 1;
		cost = 5;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 24;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " applies " + name + "!";
		str += player.poisonWeapon(damage, weaponDuration, false);
		player.removeFromInventory(this);
		return str;
	}
}
