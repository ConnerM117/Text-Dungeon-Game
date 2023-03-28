package items.gear;

public class GreatHelm extends Gear {
	
	public GreatHelm() {
		name = "Great Helm";
		description = "Thick head protection, but reduces visibility.";
		count = 1;
		cost = 8;
		isEquippable = true;
		worn = WornOn.HEAD;
		handsRequired = 0;
		armorMod = 2;
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
