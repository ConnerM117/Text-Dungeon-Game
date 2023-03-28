package adventurers.perks.general;

import mobs.Player;

public class Survivor extends Perk {

	public Survivor() {
		name = "Survivor";
		description = "1/battle when you would die, spend 1 Stamina and survive with 1 HP";
	}
	
	@Override
	public void applyBenefits(Player player) {
		player.setSurvivor(true);
	}

}
