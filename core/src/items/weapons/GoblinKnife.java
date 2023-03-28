package items.weapons;

public class GoblinKnife extends Weapon {
	
	public GoblinKnife() {
		name = "Goblin Knife";
		description = "A crude short blade with a cruel edge. Light.";
		count = 1;
		cost = 8;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = true;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 7;
		woundDamageMod = 1;
		
		atlasIndex = 26;
	}

}
