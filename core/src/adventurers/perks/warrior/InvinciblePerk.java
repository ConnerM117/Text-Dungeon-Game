package adventurers.perks.warrior;

import adventurers.choices.warrior.Invincible;
import adventurers.perks.general.Perk;
import mobs.Player;

public class InvinciblePerk extends Perk {

	public InvinciblePerk() {
		name = "Invincible";
		description = "Become immune to damage for one round";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Invincible());
	}

}
