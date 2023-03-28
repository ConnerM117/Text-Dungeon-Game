package items.weapons;

import mobs.Player;

public class DemonSword extends Weapon {
	
	private int burnDamage;
	private int weaponBurnRounds;
	
	public DemonSword() {
		name = "Demon Sword";
		description = "A black blade with red demonic runes. Permanently burning.";
		count = 1;
		cost = 15;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 6;
		maxDamage = 10;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		
		atlasIndex = 23;
		
		burnDamage = 1;
		weaponBurnRounds = 0;
	}
	
	@ Override
	public void equipEffects(Player player) {
		player.burnWeapon(burnDamage, weaponBurnRounds, true);
	}
	
	@ Override
	public void unequipEffects(Player player) {
		player.resetWeaponBurning();
	}

}
