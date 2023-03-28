package items.weapons;

public class Morningstar extends Weapon {

	public Morningstar() {
		name = "Morningstar";
		description = "A sophisticated way to say 'spiked club.'";
		count = 1;
		cost = 8;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 1;
		magicDamageMod = 0;
		critRateMod = 5;
		critDamageMod = 20;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 5;
		woundDamageMod = 1;
		
		atlasIndex = 13;
	}
}
