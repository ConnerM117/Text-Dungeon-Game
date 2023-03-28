package items.armor;

public class LeatherArmor extends Armor {
	
	public LeatherArmor() {
		name = "Leather Armor";
		description = "Light armor made of hides and leathers.";
		count = 1;
		cost = 8;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 4;
		agilityMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}

}
