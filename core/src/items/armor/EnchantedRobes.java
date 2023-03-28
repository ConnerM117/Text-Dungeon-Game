package items.armor;

public class EnchantedRobes extends Armor {
	
	public EnchantedRobes() {
		name = "Enchanted Robes";
		description = "Magical robes. +1 Stamina";
		count = 1;
		cost = 12;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 2;
		agilityMod = 5;
		toughnessMod = 0;
		staminaMod = 1;
		isEquipped = false;
		type = Type.ARMOR;
	}

}
