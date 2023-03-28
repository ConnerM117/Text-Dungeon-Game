package items.gear;

public class HolyRelic extends Gear {
	
	public HolyRelic() {
		name = "Holy Relic";
		description = "A holy artifact from another time.";
		count = 1;
		cost = 0;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 0;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 5;
		accuracyMod = 0;
		toughnessMod = 10;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
