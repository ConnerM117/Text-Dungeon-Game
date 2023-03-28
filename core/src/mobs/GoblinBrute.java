package mobs;

import java.util.List;

import items.Item;

public class GoblinBrute extends Mob {

	private int tempHP;
	
	public GoblinBrute(int counter) {
		super();
		name = "Goblin Brute " + counter;
		XP = 4;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 50;
		baseMind = 30;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		
		tempHP = 3;
	}

	@Override
	public Item getItemDrop() {
		return Item.getGoblinItem();
	}

	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes a moment to regain their strength!\n" + setTempHP(tempHP);
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " unleashes a powerful wounding attack!\n" + buffWoundRate(100, 0) + "\n" + buffWoundDamage(1, 0) + "\n" + attackTarget(target, false, false, true);
	}

}
