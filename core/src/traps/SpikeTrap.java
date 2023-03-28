package traps;

import mobs.Player;

public class SpikeTrap extends Trap {

	public SpikeTrap() {
		isArmed = true;
		damage = 3;
		attackAccuracy = 100;
		description = "raise spikes from the ground";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = ("Spikes shoot up from holes in the ground!\n");
		if (player.doesDodge()) { //if player dodges
			str += ("But you manage to leap out of the way!");
		} else { //the player doesn't dodge
			str += player.takeDamage(damage);
		}
		return str;
	}
}
