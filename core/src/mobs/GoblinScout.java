package mobs;

import java.util.List;

import items.Item;
import items.weapons.GoblinKnife;

public class GoblinScout extends Mob {
	
	//constructor
	public GoblinScout(int counter) {
		super();
		name = "Goblin Scout " + counter;
		XP = 2;
		maxHitPoints = 6;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 20;
		baseMind = 30;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 4;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
		
	//public methods
	
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " focuses on avoiding attacks and waits for an opening!\n" + buffAgility(20, 2) + "\n" + buffCritRate(5, 2);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " hides!\n" + setHidden(true, 2);
	}

	@Override
	public Item getItemDrop() {
		return new GoblinKnife();
	}
}
