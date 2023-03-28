package items.gear;

public class Cloak extends Gear {
	
	public Cloak() {
		name = "Cloak";
		description = "A thick cloak that obscures your movements.";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 0;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 10;
		accuracyMod = 5;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}
}
