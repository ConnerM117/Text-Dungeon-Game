package items.gear;

import mobs.Player;

public class LichSkull extends Gear {
	
	public LichSkull() {
		name = "Lich Skull";
		description = "The skull of a dead lich. Immune to Wounded.";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 0;
		magicDamageMod = 1;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 1;
		isEquipped = false;
		type = Type.GEAR;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.setImmuneToWounded(true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.setImmuneToWounded(false);
	}

}
