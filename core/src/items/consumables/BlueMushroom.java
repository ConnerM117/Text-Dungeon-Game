package items.consumables;

import mobs.Player;

public class BlueMushroom extends Consumable {

	private int XPMod;
	
	public BlueMushroom() {
		name = "Blue Mushroom";
		description = "A powerful alchemical ingredient. Who knows what it does on its own?";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		XPMod = 10;
		
		atlasIndex = 3;
	}
	
	public String useItem(Player player) {
		player.removeFromInventory(this);
		return player.getName() + " consumes the " + name + "!\n" + player.receiveXP(XPMod);
	}
}
