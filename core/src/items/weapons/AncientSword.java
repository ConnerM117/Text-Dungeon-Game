package items.weapons;

public class AncientSword extends Weapon {
	
	public AncientSword() {
		name = "Ancient Sword";
		description = "A very old sword in excellent condition.";
		count = 1;
		cost = 10;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 6;
		maxDamage = 9;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 5;
		accuracyMod = 5;
		woundRateMod = 5;
		woundDamageMod = 1;
		atlasIndex = 21;
	}

}
