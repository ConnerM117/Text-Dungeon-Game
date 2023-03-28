package adventurers.perks.warrior;

import adventurers.choices.warrior.PowerAttack;
import adventurers.perks.general.Perk;
import mobs.Player;

public class PowerAttackPerk extends Perk {
	
	public PowerAttackPerk() {
		name = "Power Attack";
		description = "Attack with reduced Accuracy but increased Damage";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new PowerAttack());
	}

}
