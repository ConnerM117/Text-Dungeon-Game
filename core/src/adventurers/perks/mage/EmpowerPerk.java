package adventurers.perks.mage;

import adventurers.choices.mage.Empower;
import adventurers.perks.general.Perk;
import mobs.Player;

public class EmpowerPerk extends Perk {

	public EmpowerPerk() {
		name = "Empower";
		description = "Increase Magic Damage and Crit Rate";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Empower());
	}

}
