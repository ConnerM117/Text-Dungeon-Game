package mobs;

import java.util.List;

import items.Item;
import items.consumables.Consumable;

public class WereRat extends Mob {

	private int tempHP;

	public WereRat(int counter) {
		super();
		name = "Wererat " + counter;
		XP = 2;
		maxHitPoints = 7;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 40;
		baseMind = 50;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 4;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;

		tempHP = 2;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return "The wererat takes precise aim!\n" + buffAccuracy(10, 3) + "\n" + buffCritRate(5, 3);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		return "The wererat hangs back, it seems to gain back some fighting spirit...\n" + setTempHP(tempHP);
	}

	@Override
	public Item getItemDrop() {
		return Consumable.getRandConsumable();
	}

}
