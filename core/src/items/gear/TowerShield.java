package items.gear;

public class TowerShield extends Gear {
	
	public TowerShield() {
		name = "Tower Shield";
		description = "A huge shield that restricts visibility.";
		count = 1;
		cost = 6;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 3;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = -5;
		accuracyMod = -5;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
