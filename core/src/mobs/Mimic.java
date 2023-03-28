package mobs;

import java.util.List;

import items.Item;

public class Mimic extends Mob {

	public Mimic() {
		super();
		name = "Mimic";
		XP = 2;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 60;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " lashes with venomous tongue!\n";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = (name + " tries to immobilize " + target.getName() + " with sticky ooze!\n");
		if (this.isAccurate()) {
			str += target.debuffAccuracy(15, 3) + "\n" + target.debuffAgility(30, 3);
		} else {
			str += "But " + target.getName() + " leaps out of the way!";
		}
		return str;
	}
	
	@Override
	public Item getItemDrop() {
		return null;
	}

}
