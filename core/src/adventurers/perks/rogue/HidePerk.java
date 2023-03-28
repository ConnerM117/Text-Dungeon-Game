package adventurers.perks.rogue;

import adventurers.choices.rogue.Hide;
import adventurers.perks.general.Perk;
import mobs.Player;

public class HidePerk extends Perk {

	public HidePerk() {
		name = "Hide";
		description = "Spend 1 Stamina to Hide. While hidden, gain +20 Dodge and +2 Damage, and foes must find you to attack you.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Hide());
	}

}
