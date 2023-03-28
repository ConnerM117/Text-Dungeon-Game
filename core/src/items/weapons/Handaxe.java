package items.weapons;

public class Handaxe extends Weapon {

	public Handaxe() {
		name = "Handaxe";
		description = "A small axe with big dreams. Light.";
		count = 1;
		cost = 5;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = true;
		handsRequired = 1;
		minDamage = 2;
		maxDamage = 8;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 1;
		
		atlasIndex = 9;
	}
	
}
