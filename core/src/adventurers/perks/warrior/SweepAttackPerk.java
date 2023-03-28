package adventurers.perks.warrior;

import adventurers.choices.warrior.SweepAttack;
import adventurers.perks.general.Perk;
import mobs.Player;

public class SweepAttackPerk extends Perk {

	public SweepAttackPerk() {
		name = "Sweep Attack";
		description = "Attack two targets with decreased Accuracy";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new SweepAttack());
	}

}
