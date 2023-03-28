package items.weapons;

public class Shortsword extends Weapon {
	
	public Shortsword() {
		name = "Shortsword";
		description = "A... short sword. Light.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = true;
		handsRequired = 1;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 18;
	}
}
