package items.gear;

public class Shield extends Gear {
	
	public Shield() {
		name = "Shield";
		description = "A sizeable anti-death device.";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 2;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 5;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
