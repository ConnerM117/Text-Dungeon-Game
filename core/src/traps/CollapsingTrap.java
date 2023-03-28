package traps;

import mobs.Player;

public class CollapsingTrap extends Trap {

	public CollapsingTrap() {
		isArmed = true;
		damage = 4;
		attackAccuracy = 100;
		description = "collapse the ceiling";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = ("A hidden contraption collapses the ceiling!");
		if (player.doesDodge()) { //if player dodges
			str += ("\nYou manage to leap out of the way and avoid most of the debris!\n" + player.takeDamage(damage/2));
		} else { //the player doesn't dodge
			str += "\n" + player.takeDamage(damage);
		}
		isArmed = false;
		return str;
	}

}
