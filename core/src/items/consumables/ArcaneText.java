package items.consumables;

import mobs.Player;

public class ArcaneText extends Consumable {

	public ArcaneText() {
		name = "Arcane Text";
		description = "A volume with dense passages of scholastic prose and highly detailed diagrams.";
		count = 1;
		cost = 10;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 0;
	}
	
	public String useItem(Player player) {
		return "You can't use this here.";
	}
}
