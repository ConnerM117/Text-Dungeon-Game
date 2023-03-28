package items.weapons;

import mobs.Player;

public class GiantSnakeFang extends Weapon {
	
	private int poisonDamage;
	private int weaponPoisonedRounds;
	
	public GiantSnakeFang() {
		name = "Giant Snake Fang";
		description = "A long fang useable as a piercing weapon. Permanently poisoned.";
		count = 1;
		cost = 14;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 2;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 25;
		
		poisonDamage = 1;
		weaponPoisonedRounds = 0;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.poisonWeapon(poisonDamage, weaponPoisonedRounds, true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.resetWeaponPoisoned();
	}

}
