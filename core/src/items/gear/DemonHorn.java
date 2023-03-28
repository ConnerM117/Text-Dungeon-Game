package items.gear;

import mobs.Player;

public class DemonHorn extends Gear {
	
	public DemonHorn() {
		name = "Demon Horn";
		description = "A curved black horn from the skull of a demon. Immunity to Burn";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
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
