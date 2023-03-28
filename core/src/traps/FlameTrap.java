package traps;

import mobs.Player;

public class FlameTrap extends Trap {

	private int burnDamage;
	
	public FlameTrap() {
		isArmed = true;
		damage = 2;
		burnDamage = 1;
		attackAccuracy = 80;
		description = "shoot flames";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = "A gout of flame blasts toward " + player.getName() + "!";
		if (trapDoesHit()) { //if attack hits;
			str += "\n" + player.takeDamage(damage);
			if (player.doesDodge()) { //if the attack hits, the target has a chance to resist the Burn
				str += ("\nBut they get away fast enough to avoid catching fire!");
			} else {
				str += "\n" + player.setBurning(true, burnDamage); //if they don't resist, burn for random rounds from min to max
			}
		} else { //the attack misses
			str += ("But it narrowly misses!");
		}
		return str;
	}

}
