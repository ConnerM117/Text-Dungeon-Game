package items.weapons;

public class ArmingSword extends Weapon {
	
	public ArmingSword() {
		name = "Arming Sword";
		description = "A basic one-handed sword.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 7;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 5;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		atlasIndex = 0;
	}

}
