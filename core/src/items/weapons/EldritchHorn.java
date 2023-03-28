package items.weapons;

public class EldritchHorn extends Weapon {
	
	public EldritchHorn() {
		name = "Eldritch Horn";
		description = "A long twisting horn from an eldritch beast; 3 damage, +5 Crit Rate, +1 Crit Damage, +15 Accuracy";
		count = 1;
		cost = 16;
		isEquippable = true;
		isEquipped = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 1;
		minDamage = 6;
		maxDamage = 9;
		piercingDamage = 2;
		magicDamageMod = 2;
		critRateMod = 5;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 5;
		woundRateMod = 0;
		woundDamageMod = 0;
		
		atlasIndex = 24;
	}

}
