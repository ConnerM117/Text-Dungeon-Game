package mobs;

import java.util.List;

import items.Item;

public class GiantRat extends Mob {

	public GiantRat(int counter) {
		super();
		name = "Giant Rat " + counter;
		XP = 1;
		maxHitPoints = 5;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 0;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 20;
		baseMind = 20;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 4;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " waits for an opening to attack!" + "\n" + buffAgility(20, 2) + "\n" + buffToughness(10, 2)
				+ "\n" + buffCritRate(5, 2);
	}

	// attack with a diseased bite that also has a chance to poison the target
	public String staminaAction(Mob target, List<Mob> mobs) {
		setPoisonAttack(true, 1);
		String str = name + " lunges with a diseased bite!\n" + attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return null;
	}
}
