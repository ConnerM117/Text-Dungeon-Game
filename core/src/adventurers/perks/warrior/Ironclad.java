package adventurers.perks.warrior;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Ironclad extends Perk {

	private int armorBuff;
	
	public Ironclad() {
		name = "Ironclad";
		armorBuff = 10;
		description = "While wearing armor, increase Armor by +" + armorBuff;
	}
	
	@Override
	public void applyBenefits(Player player) {
		// TODO increase Armor while wearing armor
	}

}
