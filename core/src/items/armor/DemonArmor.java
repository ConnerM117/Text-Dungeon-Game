package items.armor;

import mobs.Player;

public class DemonArmor extends Armor {

	public DemonArmor() {
		name = "Demon Armor";
		description = "Black armor decorated with glowing red runes. Immune to Burning, Bleeding, and Poison";
		count = 1;
		cost = 20;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 8;
		agilityMod = -5;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.setImmuneToBurning(true);
		player.setImmuneToPoison(true);
		player.setImmuneToWounded(true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.setImmuneToBurning(false);
		player.setImmuneToPoison(false);
		player.setImmuneToWounded(false);
	}
}
