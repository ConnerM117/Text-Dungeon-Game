package traps;

import mobs.Player;

public class ArrowTrap extends Trap {
	
	public ArrowTrap() {
		isArmed = true;
		damage = 2;
		attackAccuracy = 80;
		description = "fire an arrow";
	}

	@Override
	public String triggerTrap(Player player) {
		if (trapDoesHit()) { //if attack hits
			if (player.doesDodge()) { //if player dodges
				return ("You manage to leap out of the way of an arrow!");
			} else { //the player doesn't dodge
				String str = ("An arrow hits!\n") + player.takeDamage(damage);
				return str;
			}
		} else { //the attack misses
			return ("An arrow flies over your head!");
		}
	}

}
