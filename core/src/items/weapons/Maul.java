package items.weapons;

public class Maul extends Weapon {
	
	public Maul() {
		name = "Maul";
		description = "A huge bludgeoning hammer or mace.";
		count = 1;
		cost = 12;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 8;
		maxDamage = 10;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 10;
		critDamageMod = 40;
		dodgeMod = 0;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 12;
	}
}
