package items.consumables;

import mobs.Player;

public class Laudanum extends Consumable {

	public Laudanum() {
		name = "Laudanum";
		description = "Removes all debuffs.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 11;
	}

	public String useItem(Player player) {
		String str = player.getName() + " drinks " + name + "!";
		str += player.removeAllDebuffs();
		player.removeFromInventory(this);
		return str;
	}
}
