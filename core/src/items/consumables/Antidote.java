package items.consumables;

import mobs.Player;

public class Antidote extends Consumable {
	
	public Antidote() {
		name = "Antidote";
		description = "Ends any Poison affecting you.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 1;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks an " + name + ".\n";
		if (player.isPoisoned()) {
			str += player.getName() + " is no longer Poisoned!";
		} else {
			str += "There is no poison to neutralize!";
		}
		player.setPoisoned(false);
		player.removeFromInventory(this);
		return str;
	}
}
