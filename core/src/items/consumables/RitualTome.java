package items.consumables;

import mobs.Player;

public class RitualTome extends Consumable {

	public RitualTome() {
		name = "Ritual Tome";
		description = "An ancient tome detailing rituals of demon summoning and binding.";
		count = 1;
		cost = 4;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You can't use this here.";
	}
}
