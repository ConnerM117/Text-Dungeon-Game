package adventurers.perks.mage;

import adventurers.choices.mage.HexAbyss;
import adventurers.perks.general.Perk;
import mobs.Player;

public class HexAbyssPerk extends Perk {

	public HexAbyssPerk() {
		name = "Hex of the Abyss";
		description = "Choose a target. Debuff one stat of your choice.";
	}
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new HexAbyss());
	}

}
