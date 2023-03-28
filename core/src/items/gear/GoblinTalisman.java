package items.gear;

public class GoblinTalisman extends Gear {
	
	public GoblinTalisman() {
		name = "Goblin Talisman";
		description = "A piece of bone carved with crude symbols.";
		count = 1;
		cost = 6;
		isEquippable = true;
		worn = WornOn.MISC;
		handsRequired = 0;
		armorMod = 0;
		magicDamageMod = 1;
		damageMod = 0;
		critRateMod = 5;
		critDamageMod = 0;
		woundRateMod = 5;
		woundDamageMod = 0;
		agilityMod = 0;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}

}
