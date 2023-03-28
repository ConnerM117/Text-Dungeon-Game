package items.weapons;

public class Pike extends Weapon {
	
	public Pike() {
		name = "Pike";
		description = "A very long spear that requires greater skill.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 5;
		maxDamage = 9;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 20;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 1;
		
		atlasIndex = 15;
	}
}
