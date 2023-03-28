package mobs;

import java.util.List;

import items.Item;
import items.consumables.DemonAshes;

public class Imp extends Mob {
	
	public Imp(int counter) {
		super();
		name = "Imp " + counter;
		XP = 1;
		maxHitPoints = 5;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 50;
		baseMind = 60;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " attacks with a venomous stinger!\n";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " turns invisible!\n" + setHidden(true, 2);
	}

	@Override
	public Item getItemDrop() {
		return new DemonAshes();
	}

}
