package adventurers.perks.mage;

import adventurers.choices.mage.Doppelgangers;
import adventurers.perks.general.Perk;
import mobs.Player;

public class DoppelgangerPerk extends Perk {

	public DoppelgangerPerk() {
		name = "Doppelgangers";
		description = "Summon illusory doppelgangers that draw attention from you.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new Doppelgangers());
	}

}
