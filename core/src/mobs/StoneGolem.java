package mobs;

import java.util.List;

import items.Item;

public class StoneGolem extends Mob {
	
	//constructor
	public StoneGolem(int counter) {
		super();
		name = "Stone Guardian " + counter;
		XP = 1;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 0;
		baseToughness = 100;
		baseMind = 20;
		baseAccuracy = 60;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 0;
		currentStamina = baseStamina;
		
		immuneToWounded = true;
		immuneToPoison = true;
	}
		
	//public methods
	
	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		return "The stone guardian takes aim!\n" + buffAccuracy(10, 2);
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		return "The stone guardian prepares a deadly strike!\n" + buffDamage(2, 1);
	}

	@Override
	public Item getItemDrop() {
		return null;
	}

}
