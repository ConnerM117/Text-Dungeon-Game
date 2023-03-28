package adventurers.perks.mage;

import adventurers.choices.mage.HexWeakness;
import adventurers.perks.general.Perk;
import mobs.Player;

public class HexWeaknessPerk extends Perk {

	public HexWeaknessPerk() {
		name = "Hex of Weakness";
		description = "Choose a target. Debuff Toughness and Damage.";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.addCombatChoice(new HexWeakness());
	}

}
