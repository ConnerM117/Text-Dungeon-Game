package adventurers.perks.warrior;

import adventurers.choices.warrior.Smite;
import adventurers.perks.general.Perk;
import mobs.Player;

public class SmitePerk extends Perk {

	public SmitePerk() {
		name = "Smite";
		description = "Attack and, on hit, deal double damage";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Smite());
	}

}
