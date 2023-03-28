package items.gear;

public class LightHelm extends Gear {

	public LightHelm() {
		name = "Light Helm";
		description = "Protection for your vulnerable noggin.";
		count = 1;
		cost = 4;
		isEquippable = true;
		worn = WornOn.HEAD;
		handsRequired = 0;
		armorMod = 1;
		magicDamageMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}
}
