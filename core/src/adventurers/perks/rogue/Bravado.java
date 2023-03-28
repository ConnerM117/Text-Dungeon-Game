package adventurers.perks.rogue;

import adventurers.perks.general.Perk;
import mobs.Player;

public class Bravado extends Perk {

	public Bravado() {
		name = "Bravado";
		description = "Bon Mot also buffs Crit Rate and Crit Damage";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setHasBravado(true);
	}

}
