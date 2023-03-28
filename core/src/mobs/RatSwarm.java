package mobs;

import java.util.List;

import items.Item;

public class RatSwarm extends Mob {

	public RatSwarm(int counter) {
		super();
		name = "Rat Swarm " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 0;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 50;
		baseMind = 20;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 3;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return "The swarm washes over you, biting twice!\n" + attackTarget(target, false, false, true) + "\n"
				+ attackTarget(target, false, false, true);
	}

	// attack with a diseased bite that also has a chance to poison the target
	public String staminaAction(Mob target, List<Mob> mobs) {
		return "The swarm scatters temporarily!\n" + buffAgility(50, 1) 
			+ "\n" + buffToughness(50, 1) + "\n" + buffCritRate(10, 1) + "\n" + buffCritDamage(1, 1);
	}

	@Override
	public Item getItemDrop() {
		return null;
	}
}
