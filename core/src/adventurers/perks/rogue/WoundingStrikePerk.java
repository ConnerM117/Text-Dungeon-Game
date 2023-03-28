package adventurers.perks.rogue;

import adventurers.choices.rogue.WoundingStrike;
import adventurers.perks.general.Perk;
import mobs.Player;

public class WoundingStrikePerk extends Perk {

	public WoundingStrikePerk() {
		name = "Wounding Strike";
		description = "Spend stamina to attack. On hit, the target is automatically Wounded.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new WoundingStrike());
	}

}
