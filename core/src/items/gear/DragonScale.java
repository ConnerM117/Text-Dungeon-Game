package items.gear;

import mobs.Player;

public class DragonScale extends Gear {
	
	public DragonScale() {
		name = "Dragon Scale";
		description = "A huge scale usable as a shield. Immune to Burning";
		count = 1;
		cost = 10;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 2;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 10;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
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
