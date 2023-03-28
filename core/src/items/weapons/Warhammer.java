package items.weapons;

public class Warhammer extends Weapon {
	
	public Warhammer() {
		name = "Warhammer";
		description = "A hefty weapon with a hammer on one side and a spike on the other.";
		count = 1;
		cost = 8;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 6;
		maxDamage = 8;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 7;
		critDamageMod = 20;
		dodgeMod = 0;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 20;
	}
}
