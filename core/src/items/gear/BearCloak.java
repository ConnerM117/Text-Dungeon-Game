package items.gear;

public class BearCloak extends Gear {
	
	public BearCloak() {
		name = "Bear Cloak";
		description = "A thick fur cloak made from bear hide.";
		count = 1;
		cost = 8;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 1;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 10;
		agilityMod = 5;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
