package mobs;

import java.util.List;

import items.Item;
import items.consumables.WyvernVenom;

public class Wyvern extends Mob {
	
	public Wyvern(int counter) {
		super();
		name = "Wyvern " + counter;
		XP = 3;
		maxHitPoints = 12;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 6;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 70;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 2;
		critRate = 5;
		critDamage = 25;
		woundRate = 15;
		woundDamage = 1;
		baseStamina = 2;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes to the sky and performs a flyby attack!\n" + buffAgility(20, 1) + "\n" + attackTarget(target, false, false, true);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " strikes with its tail stinger!";
		
		setPoisonAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new WyvernVenom();
	}

}
