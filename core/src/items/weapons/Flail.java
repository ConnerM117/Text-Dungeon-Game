package items.weapons;

public class Flail extends Weapon {
	
	public Flail() {
		name = "Flail";
		description = "A spiky ball on the end of a chain.";
		count = 1;
		cost = 9;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 6;
		maxDamage = 8;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 10;
		critDamageMod = 50;
		dodgeMod = 0;
		accuracyMod = -5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 5;
	}
}
