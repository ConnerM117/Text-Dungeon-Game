package mobs;

import java.util.List;

import items.Item;
import items.consumables.DemonAshes;

public class FireDemon extends Mob {
	
	public FireDemon(int counter) {
		super();
		name = "Fire Demon " + counter;
		XP = 2;
		maxHitPoints = 11;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 0;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 70;
		baseMind = 50;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 7;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
		immuneToBurning = true;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " throws a fireball!\n";
		
		setBurningAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + "'s flames burn brightly!\n" + buffDamage(1, 3) + "\n" + buffCritRate(10, 3);
	}

	@Override
	public Item getItemDrop() {
		return new DemonAshes();
	}

}
