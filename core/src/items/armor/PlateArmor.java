package items.armor;

public class PlateArmor extends Armor {
	
	public PlateArmor() {
		name = "Plate Armor";
		description = "Metal plates, worn over chain mail.";
		count = 1;
		cost = 15;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 10;
		agilityMod = -10;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}

}
