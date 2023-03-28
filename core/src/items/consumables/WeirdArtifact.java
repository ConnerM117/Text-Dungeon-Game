package items.consumables;

import mobs.Player;

public class WeirdArtifact extends Consumable {

	public WeirdArtifact() {
		name = "Weird Artifact";
		description = "This chunk of polished black stone is cut into a peculiar shape, and has golden cogs engraved "
				+ "on its surface.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
	}
	
	public String useItem(Player player) {
		return "You don't know how to use this.";
	}
}
