package items.consumables;

import mobs.Player;

public class BurningPitch extends Consumable {
	
	private int burnDamage;
	private int weaponBurningRounds;
	
	public BurningPitch() {
		name = "Burning Pitch";
		description = "Causes your weapon to start Burning.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		burnDamage = 1;
		weaponBurningRounds = 4;
		
		atlasIndex = 4;
	}
	
	public String useItem(Player player) {
		String str = player.getName() + " covers their weapon in " + name + ".\n";
		str += player.burnWeapon(burnDamage, weaponBurningRounds, false);
		player.removeFromInventory(this);
		return str;
	}

}
