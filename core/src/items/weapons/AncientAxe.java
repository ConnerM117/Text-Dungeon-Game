package items.weapons;

public class AncientAxe extends Weapon {
	
	public AncientAxe() {
		name = "Ancient Axe";
		description = "An ancient axe in excellent condition.";
		count = 1;
		cost = 10;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 5;
		maxDamage = 10;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 10;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 7;
		woundDamageMod = 2;
		atlasIndex = 22;
	}

}
