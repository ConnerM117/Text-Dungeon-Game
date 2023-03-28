package items.armor;

import mobs.Player;

public class DragonhideArmor extends Armor {
	
	public DragonhideArmor() {
		name = "Dragonhide Armor";
		description = "Armor made of tough dragonhide. Immune to Burning";
		count = 1;
		cost = 20;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 10;
		agilityMod = -10;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.setImmuneToBurning(true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.setImmuneToBurning(false);
	}

}
