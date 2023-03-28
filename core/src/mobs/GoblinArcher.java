package mobs;

import java.util.List;

import items.Item;
import items.weapons.GoblinKnife;

public class GoblinArcher extends Mob {
	
	public GoblinArcher(int counter) {
		super();
		name = "Goblin Archer " + counter;
		XP = 1;
		maxHitPoints = 4;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 20;
		baseMind = 30;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
		
	//public methods
	
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes aim!\n" + buffAccuracy(20, 1) + "\n" + buffCritRate(10, 1);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " fires two arrows!\n" + attackTarget(target, false, false, true) + "\n" + attackTarget(target, false, false, true);
	}

	@Override
	public Item getItemDrop() {
		return new GoblinKnife();
	}

}
