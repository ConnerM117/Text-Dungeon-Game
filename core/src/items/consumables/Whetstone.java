package items.consumables;

import mobs.Player;

public class Whetstone extends Consumable {

	public Whetstone() {
		name = "Dwarven Whetstone";
		description = "Increases damage by 1";
		count = 1;
		cost = 3;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 23;
	}
	
	public String useItem(Player player) {
		String str =  player.getName() + " sharpens their weapon with a " + name + "!\n";
		str += player.buffDamage(1, 3);
		player.removeFromInventory(this);
		return str;
	}
}
