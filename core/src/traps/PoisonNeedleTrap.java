package traps;

import mobs.Player;

public class PoisonNeedleTrap extends Trap {
	
	private int poisonDamage;
	
	public PoisonNeedleTrap() {
		isArmed = true;
		damage = 1;
		attackAccuracy = 95;
		poisonDamage = 2;
		description = "fire a poisoned needle";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = "A needle springs out of the lock!\n";
		if (trapDoesHit()) { //if attack hits
			str += player.takeDamage(damage);
			if (player.isTough()) { //if the attack hits, the target has a chance to resist the poison
				str += ("\nBut the poison doesn't take effect!");
			} else {
				str += "\n" + player.setPoisoned(true, poisonDamage); //if they don't resist, poison for random rounds from min to max
			}
		} else { //the attack misses
			str += ("But you're already out of the way!");
		}
		isArmed = false;
		return str;
	}

}
