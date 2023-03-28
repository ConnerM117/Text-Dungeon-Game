package items.consumables;

import mobs.Player;

public class LizardmanAntidote extends Consumable {
	
	private int rounds;
	
	public LizardmanAntidote() {
		name = "Lizardman Antidote";
		rounds = 3;
		description = "Immune to Poison for " + rounds + " rounds.";
		count = 1;
		cost = 3;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 13;
	}
	
	public String useItem(Player player) {
		player.setImmunePoisonTemp(true, 3);
		player.removeFromInventory(this);
		return player.getName() + " drinks a " + name + "!\nThey're immune to Poison for " + rounds + " rounds.";
	}

}
