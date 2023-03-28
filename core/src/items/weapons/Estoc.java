package items.weapons;

public class Estoc extends Weapon {
	
	public Estoc() {
		name = "Estoc";
		description = "A two-handed thrusting sword.";
		count = 1;
		cost = 11;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 5;
		maxDamage = 7;
		piercingDamage = 3;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 5;
		accuracyMod = 10;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 3;
	}
	
}
