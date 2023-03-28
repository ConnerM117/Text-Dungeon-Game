package items.weapons;

public class Greataxe extends Weapon {
	
	public Greataxe() {
		name = "Greataxe";
		description = "A heavy two-handed axe.";
		count = 1;
		cost = 12;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 2;
		minDamage = 6;
		maxDamage = 12;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 7;
		critDamageMod = 25;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 10;
		woundDamageMod = 2;
		
		atlasIndex = 6;
	}
}
