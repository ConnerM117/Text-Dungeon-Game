package items.weapons;

public class Halberd extends Weapon {
	
	public Halberd() {
		name = "Halberd";
		description = "A versatile polearm with an axe, spearpoint, and hammer.";
		count = 1;
		cost = 14;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 6;
		maxDamage = 10;
		piercingDamage = 2;
		magicDamageMod = 0;
		critRateMod = 7;
		critDamageMod = 20;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 7;
		woundDamageMod = 1;
		
		atlasIndex = 8;
	}

}
