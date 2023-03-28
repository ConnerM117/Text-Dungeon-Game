package traps;

import mobs.Player;

public class PoisonDartTrap extends Trap {

	public PoisonDartTrap() {
		isArmed = true;
		damage = 1;
		attackAccuracy = 80;
		description = "fire a dart";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = "A dart shoots toward you!\n";
		if (trapDoesHit()) { //if attack hits
			str += player.takeDamage(damage);
			if (player.isTough()) { //if the attack hits, the target has a chance to resist the poison
				str += ("\nBut the poison doesn't take effect!");
			} else {
				str += "\n" + player.setPoisoned(true, 1); //if they don't resist, poison for random rounds from min to max
			}
		} else { //the attack misses
			str += ("But it narrowly misses!");
		}
		return str;
	}

}
