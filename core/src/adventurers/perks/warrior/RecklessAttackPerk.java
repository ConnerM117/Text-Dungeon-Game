package adventurers.perks.warrior;

import adventurers.choices.warrior.RecklessAttack;
import adventurers.perks.general.Perk;
import mobs.Player;

public class RecklessAttackPerk extends Perk {

	public RecklessAttackPerk() {
		name = "Reckless Attack";
		description = "This attack can't be dodged, but reduces your Dodge";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new RecklessAttack());
	}

}
