package items.gear;

public class DruidStaff extends Gear {
	
	public DruidStaff() {
		name = "Druid Staff";
		description = "A magical implement for casting spells.";
		count = 1;
		cost = 0;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 0;
		magicDamageMod = 2;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 10;
		staminaMod = 1;
		isEquipped = false;
		type = Type.GEAR;
	}

}
