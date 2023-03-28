package items.consumables;

import mobs.Player;

public class FlaskOfWater extends Consumable {
	
	public FlaskOfWater() {
		name = "Flask of Water";
		description = "Ends any Burning affecting you.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 8;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " uses a " + name + "!\n";
		if (player.isBurning()) {
			str += player.getName() + " is no longer Burning!";
		} else {
			str += "There is no fire to put out!";
		}
		player.setBurning(false);
		player.removeFromInventory(this);
		return str;
	}

}
