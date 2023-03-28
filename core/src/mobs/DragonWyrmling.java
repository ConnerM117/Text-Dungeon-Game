package mobs;

import java.util.List;

import items.Item;
import items.armor.DragonhideArmor;

public class DragonWyrmling extends Mob {
	
	public DragonWyrmling(int counter) {
		super();
		name = "Dragon Wyrmling " + counter;
		XP = 2;
		maxHitPoints = 9;
		currentHitPoints = maxHitPoints;
		minArmor = 3;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 80;
		baseMind = 60;
		baseAccuracy = 70;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		
		immuneToBurning = true;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes to the sky and performs a flyby attack!\n" + buffAgility(30, 1) + "\n" + attackTarget(target, false, false, true);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = (name + " breathes a jet of flame!");
		if (!target.isAgile()) { //the target has a chance to dodge to resist burning
			str += target.getName() + " isn't fast enough to get out of the flames!\n" + target.setBurning(true, 1);
		} else { //they dodge
			str += target.getName() + " leaps out of the way of the flames!";
		}
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new DragonhideArmor();
	}

}
