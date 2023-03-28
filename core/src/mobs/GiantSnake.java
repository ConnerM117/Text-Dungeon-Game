package mobs;

import java.util.List;

import items.Item;
import items.weapons.GiantSnakeFang;

public class GiantSnake extends Mob {
	
	public GiantSnake(int counter) {
		super();
		name = "Giant Snake " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 50;
		baseMind = 50;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 5;
		piercingDamage = 1;
		critRate = 10;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " coils up and readies to strike!\n" + buffAccuracy(20, 1) + "\n" + buffCritRate(20, 1) + "\n" + buffCritDamage(25, 1);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " lunges with venomous fangs!\n";
		
		setPoisonAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new GiantSnakeFang();
	}

}
