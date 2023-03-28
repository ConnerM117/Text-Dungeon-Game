package adventurers.perks.warrior;

import adventurers.choices.warrior.ToughAsNails;
import adventurers.perks.general.Perk;
import mobs.Player;

public class ToughAsNailsPerk extends Perk {

	public ToughAsNailsPerk() {
		name = "Tough As Nails";
		description = "Spend 1 Stamina to regain Hit Points, and gain the excess as Shield";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new ToughAsNails());
	}

}
