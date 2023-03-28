package items.weapons;

public class Greatsword extends Weapon {
	
	public Greatsword() {
		name = "Greatsword";
		description = "A fearsome two-handed sword.";
		count = 1;
		cost = 12;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 7;
		maxDamage = 11;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 10;
		dodgeMod = 5;
		accuracyMod = 5;
		woundRateMod = 7;
		woundDamageMod = 1;
		
		atlasIndex = 7;
	}
}
