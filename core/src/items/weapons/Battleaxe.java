package items.weapons;

public class Battleaxe extends Weapon {
	
	public Battleaxe() {
		name = "Battleaxe";
		description = "A hefty large-bladed axe.";
		count = 1;
		cost = 8;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 4;
		maxDamage = 10;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 7;
		woundDamageMod = 2;
		atlasIndex = 1;
	}
	
}
