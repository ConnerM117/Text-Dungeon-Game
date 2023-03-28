package items.weapons;

public class Longsword extends Weapon {
	
	public Longsword() {
		name = "Longsword";
		description = "A versatile hand-and-a-half sword.";
		count = 1;
		cost = 9;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 5;
		maxDamage = 9;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 5;
		accuracyMod = 5;
		woundRateMod = 5;
		woundDamageMod = 0;
		
		atlasIndex = 10;
	}
}
