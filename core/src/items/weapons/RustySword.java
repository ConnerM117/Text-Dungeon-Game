package items.weapons;

public class RustySword extends Weapon {
	
	public RustySword() {
		name = "Rusty Sword";
		description = "A sword in terrible condition.";
		count = 1;
		cost = 3;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 1;
		
		atlasIndex = 17;
	}

}
