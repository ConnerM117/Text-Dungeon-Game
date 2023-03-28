package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class EldritchSpawn extends Mob {
	
	public EldritchSpawn(int counter) {
		super();
		name = "Eldritch Spawn " + counter;
		XP = 1;
		maxHitPoints = 7;
		currentHitPoints = maxHitPoints;
		minArmor = 3;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 50;
		baseMind = 80;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " blinks in and out of existence!\n" + buffAgility(30, 2);
	}
	
	//attack with a diseased bite that also has a chance to poison the target
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = (name + " warps time around + " + target.getName() + "!");
		int rand = GameScreen.generateRandom(1, 4);
		if (rand == 1)
			str += "\n" + target.debuffAccuracy(20, 3);
		else if (rand == 2)
			str += "\n" + target.debuffCritRate(20, 3);
		else if (rand == 3)
			str += "\n" + target.debuffAgility(20, 3);
		else
			str += "\n" + target.debuffToughness(20, 3);
		return str;
	}

	@Override
	public Item getItemDrop() {
		return null;
	}

}
