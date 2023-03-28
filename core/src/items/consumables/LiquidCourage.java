package items.consumables;

import mobs.Player;

public class LiquidCourage extends Consumable {
	
	private int armorBuff;
	private int rounds;
	
	public LiquidCourage() {
		name = "Liquid Courage";
		armorBuff = 2;
		rounds = 5;
		description = "Gain +" + armorBuff + " Armor for " + rounds + " rounds.";
		count = 1;
		cost = 6;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 12;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " drinks " + name + "!";
		str += player.buffArmor(armorBuff, rounds);
		player.removeFromInventory(this);
		return str;
	}

}
