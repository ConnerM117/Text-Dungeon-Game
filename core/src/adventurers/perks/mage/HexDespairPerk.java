package adventurers.perks.mage;

import adventurers.choices.mage.HexDespair;
import adventurers.perks.general.Perk;
import mobs.Player;

public class HexDespairPerk extends Perk {

	public HexDespairPerk() {
		name = "Hex of Despair";
		description = "Debuff a target's Accuracy and Mind";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new HexDespair());
	}

}
