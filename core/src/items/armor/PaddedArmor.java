package items.armor;

public class PaddedArmor extends Armor {
	
	public PaddedArmor() {
		name = "Padded Armor";
		count = 1;
		description = "Armor made of padded layers of cloth.";
		cost = 5;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 2;
		agilityMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}
}
