package items.consumables;

import mobs.Player;

public class GoblinMoonshine extends Consumable {

private int healStaminaValue;
	
	public GoblinMoonshine() {
		name = "Goblin Moonshine";
		healStaminaValue = 2;
		description = "Restores " + healStaminaValue + " Stamina.";
		count = 1;
		cost = 3;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 9;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks some " + name + "!\n";
		str += player.healStamina(healStaminaValue);
		player.removeFromInventory(this);
		return str;
	}
}
