package items.weapons;

import mobs.Player;

public class HydraFang extends Weapon {
	
	private int poisonDamage;
	private int weaponPoisonedRounds;
	
	public HydraFang() {
		name = "Hydra Fang";
		description = "A long fang useable as a piercing weapon. Permanently poisoned and immune to poison.";
		count = 1;
		cost = 20;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 2;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 27;
		
		poisonDamage = 1;
		weaponPoisonedRounds = 0;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.poisonWeapon(poisonDamage, weaponPoisonedRounds, true);
		player.setImmuneToPoison(true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.resetWeaponPoisoned();
		player.setImmuneToPoison(false);
	}

}
