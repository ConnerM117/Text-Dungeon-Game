package items.weapons;

public class ExecutionerBlade extends Weapon {

	public ExecutionerBlade() {
		name = "Executioner Blade";
		description = "A heavy sword with a flat end, best suited to chopping.";
		count = 1;
		cost = 9;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 4;
		maxDamage = 8;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 10;
		critDamageMod = 25;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		
		atlasIndex = 4;
	}
}
