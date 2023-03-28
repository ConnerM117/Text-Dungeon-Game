package items.consumables;

import mobs.Player;

public class TrollBrew extends Consumable {
	
	private int healValue;
	
	public TrollBrew() {
		name = "Troll Brew";
		healValue = 2;
		description = "Heals " + healValue + " hit points each turn for 4 turns";
		count = 1;
		cost = 6;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 22;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks a " + name + "!";
		str += player.regenerate(healValue, 4);
		player.removeFromInventory(this);
		return str;
	}

}
