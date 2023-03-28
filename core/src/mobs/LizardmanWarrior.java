package mobs;

import java.util.List;

import items.Item;
import items.consumables.LizardmanAntidote;

public class LizardmanWarrior extends Mob {
	
	public LizardmanWarrior(int counter) {
		super();
		name = "Lizardman Warrior " + counter;
		XP = 1;
		maxHitPoints = 7;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 50;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " poisons its blade!\n";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = (name + " throws mud from the swamp!\n");
		if (!target.isAgile()) { //target can dodge to avoid effects
			str += target.debuffAccuracy(20, 2) + "\n" + target.debuffCritRate(10, 2);
		} else {
			str += "But " + target.getName() + " dodges out of the way!";
		}
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new LizardmanAntidote();
	}

}
