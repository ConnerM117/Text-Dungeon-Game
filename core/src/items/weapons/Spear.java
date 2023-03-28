package items.weapons;

public class Spear extends Weapon {
	
	public Spear() {
		name = "Spear";
		description = "A long and easy-to-use weapon.";
		count = 1;
		cost = 6;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 7;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 10;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		
		atlasIndex = 19;
	}
}
