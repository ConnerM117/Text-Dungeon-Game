package adventurers.perks.warrior;

import adventurers.choices.warrior.Intimidate;
import adventurers.perks.general.Perk;
import mobs.Player;

public class IntimidatePerk extends Perk {

	public IntimidatePerk() {
		name = "Intimidate";
		description = "Instill fear in your enemies";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Intimidate());
	}

}
