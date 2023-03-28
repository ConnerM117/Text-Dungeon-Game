package items.consumables;

import mobs.Player;

public class PoisonVial extends Consumable {
	
	private int damage;
	private int weaponDuration;
	
	public PoisonVial() {
		name = "Poison Vial";
		damage = 1;
		weaponDuration = 3;
		description = "Poisons your weapons for " + weaponDuration + " rounds. "
				+ "\n\tOn hit, deals " + damage + " damage per round.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 17;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " applies a " + name + "!\n";
		str += player.poisonWeapon(damage, weaponDuration, false);
		player.removeFromInventory(this);
		return str;
	}
}
