package items.weapons;


public class Dagger extends Weapon {
	
	public Dagger() {
		name = "Dagger";
		description = "A very short blade used to target vital areas. Light.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = true;
		handsRequired = 1;
		minDamage = 2;
		maxDamage = 4;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		atlasIndex = 2;
	}

}
