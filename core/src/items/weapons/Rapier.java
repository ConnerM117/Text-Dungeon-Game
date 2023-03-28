package items.weapons;

public class Rapier extends Weapon {
	
	public Rapier() {
		name = "Rapier";
		description = "A thin thrusting sword.";
		count = 1;
		cost = 8;
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
		dodgeMod = 5;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 16;
	}
}
