package items.gear;

public class Buckler extends Gear {
	
	public Buckler() {
		name = "Buckler";
		description = "A small parrying shield.";
		count = 1;
		cost = 4;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 1;
		damageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		agilityMod = 10;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
