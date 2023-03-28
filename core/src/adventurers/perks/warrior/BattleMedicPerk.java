package adventurers.perks.warrior;

import adventurers.choices.warrior.BattleMedic;
import adventurers.perks.general.Perk;
import mobs.Player;

public class BattleMedicPerk extends Perk {
	
	public BattleMedicPerk() {
		name = "Battle Medic";
		description = "Heal self and end Wounded";
	}

	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new BattleMedic());
	}

}
