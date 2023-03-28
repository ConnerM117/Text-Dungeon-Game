package items.armor;

public class SplintArmor extends Armor {
	
	public SplintArmor() {
		name = "Splint Armor";
		description = "Armor made of layers of plate bolted to chain mail.";
		count = 1;
		cost = 12;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 8;
		agilityMod = -5;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.ARMOR;
	}
	
}
