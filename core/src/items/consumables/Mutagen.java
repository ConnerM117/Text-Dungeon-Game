package items.consumables;

import mobs.Player;

public class Mutagen extends Consumable {

	private int statMod;
	
	public Mutagen() {
		name = "Mutagen";
		statMod = 5;
		description = "Increases Agility, Mind, Strength, and Toughness by 5.";
		count = 1;
		cost = 10;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 16;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks a " + name + "!\nStats were increased by " + statMod + ".";
		player.modBaseAgility(statMod);
		player.modBaseMind(statMod);
		player.modBaseStrength(statMod);
		player.modBaseToughness(statMod);
		player.removeFromInventory(this);
		return str;
	}
}
