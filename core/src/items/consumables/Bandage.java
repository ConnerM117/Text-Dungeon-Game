package items.consumables;

import mobs.Player;

public class Bandage extends Consumable {
	
	private int healValue;
	
	public Bandage() {
		name = "Bandage";
		description = "Ends any Wounds/Bleeding affecting you and restores some hit points.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		healValue = 2;
		
		atlasIndex = 2;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " uses a " + name + ".\n";
		if (player.getWounded()) {
			str += player.getName() + " is no longer Wounded!";
			player.setWounded(false);
		} else {
			str += "There is no wound to bandage!";
		}
		player.healDamage(healValue);
		player.removeFromInventory(this);
		return str;
	}

}
