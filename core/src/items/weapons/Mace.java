package items.weapons;

public class Mace extends Weapon {
	
	public Mace() {
		name = "Mace";
		description = "A basic bludgeon on the end of a stick. Light.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = true;
		handsRequired = 1;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 20;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 11;
	}
	
}
