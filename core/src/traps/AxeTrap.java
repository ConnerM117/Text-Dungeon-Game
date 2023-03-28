package traps;

import mobs.Player;

public class AxeTrap extends Trap {

	public AxeTrap() {
		isArmed = true;
		damage = 2;
		attackAccuracy = 80;
		description = "swing an axe";
	}

	@Override
	public String triggerTrap(Player player) {
		String str = ("An axe swings out of a space in the ceiling");
		if (trapDoesHit()) { //if attack hits
			if (player.doesDodge()) { //if player dodges
				str += ("\nBut " + player.getName() + " manages to leap out of the way!");
			} else { //the player doesn't dodge
				str += "\n" + player.takeDamage(damage) + "\n" + player.setWounded(true, 3, 1);
			}
		} else { //the attack misses
			str += ("But it swings over your head!");
		}
		return str;
	}

}
