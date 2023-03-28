package items.gear;

import mobs.Player;

public class LizardmanShield extends Gear {
	
	public LizardmanShield() {
		name = "Lizardman Shield";
		description = "A shield made of bone and hide, with a slimy protective coating. Immune to Poison";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 1;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		agilityMod = 10;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.setImmuneToPoison(true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.setImmuneToPoison(false);
	}

}
