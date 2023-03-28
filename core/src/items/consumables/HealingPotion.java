package items.consumables;

import mobs.Player;

public class HealingPotion extends Consumable {
	
	private int healValue;
	
	public HealingPotion() {
		name = "Healing Potion";
		healValue = 5;
		description = "Heals " + healValue + " hit points";
		count = 1;
		cost = 3;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 10;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks a " + name + "!\n";
		str += player.healDamage(healValue);
		player.removeFromInventory(this);
		return str;
	}

}
