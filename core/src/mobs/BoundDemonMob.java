package mobs;

import java.util.List;

import items.Item;
import items.consumables.DemonAshes;

public class BoundDemonMob extends Mob {

	public BoundDemonMob() {
		super();
		name = "Bound Demon";
		XP = 4;
		maxHitPoints = 16;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 60;
		baseToughness = 70;
		baseMind = 50;
		baseAccuracy = 70;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 2;
		baseStamina = 2;
		currentStamina = baseStamina;
	}
	
	@Override
	public Item getItemDrop() {
		return new DemonAshes();
	}

	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = (name + " exhales a jet of flame!\n");
		
		setBurningAttack(true, 3);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();
		
		return str;
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		setHasFlameCloak(true, 2);
		return (name + " bursts into flame! It would be dangerous to get near it...");
	}

}
