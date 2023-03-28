package items.gear;

public class AncientShield extends Gear {
	
	public AncientShield() {
		name = "Ancient Shield";
		description = "An ancient shield in excellent condition.";
		count = 1;
		cost = 5;
		isEquippable = true;
		worn = WornOn.HANDS;
		handsRequired = 1;
		armorMod = 2;
		damageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 10;
		accuracyMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
	}
	
	

}
