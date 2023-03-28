package items.weapons;

public class OldSword extends Weapon {

	public OldSword() {
		name = "Old Sword";
		description = "An old and battered one-handed sword. Nothing special, but gets the job done.";
		count = 1;
		cost = 4;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 2;
		maxDamage = 6;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 14;
	}
	
}
