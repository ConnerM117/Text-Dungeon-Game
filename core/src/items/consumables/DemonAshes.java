package items.consumables;

import mobs.Player;

public class DemonAshes extends Consumable {
	
	private int immunityRounds;
	
	public DemonAshes() {
		name = "Demon Ashes";
		immunityRounds = 3;
		description = "Ends Burning and provides immunity to burning for " + immunityRounds + " rounds.";
		count = 1;
		cost = 4;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 6;
	}
	
	public String useItem(Player player) {
		player.setImmuneBurningTemp(true, 3);
		player.removeFromInventory(this);
		return player.getName() + " spreads a cloud of " + name + "!";
	}

}
