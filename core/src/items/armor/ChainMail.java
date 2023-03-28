package items.armor;

public class ChainMail extends Armor {
	
	public ChainMail() {
		name = "Chain Mail";
		description = "Armor composed of interlocked metal rings.";
		count = 1;
		cost = 10;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 6;
		agilityMod = -5;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}

}
