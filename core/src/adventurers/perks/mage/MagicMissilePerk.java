package adventurers.perks.mage;

import adventurers.choices.mage.MagicMissile;
import adventurers.perks.general.Perk;
import mobs.Player;

public class MagicMissilePerk extends Perk {

	public MagicMissilePerk() {
		name = "Magic Missile";
		description = "Attack a target. The attack never misses and can't be dodged, but deals reduced damage.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new MagicMissile());
	}

}
