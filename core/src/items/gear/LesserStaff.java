package items.gear;

public class LesserStaff extends Gear {
	
	public LesserStaff() {
		name = "Lesser Staff";
		description = "A magical implement for casting spells.";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 0;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 1;
		isEquipped = false;
		type = Type.GEAR;
	}

}
