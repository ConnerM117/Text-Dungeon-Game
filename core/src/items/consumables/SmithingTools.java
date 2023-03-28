package items.consumables;

import mobs.Player;

public class SmithingTools extends Consumable {
	
	public SmithingTools() {
		name = "Smithing Tools";
		description = "Tools used for blacksmithing.";
		count = 1;
		cost = 5;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You don't know how to use this.";
	}
}
