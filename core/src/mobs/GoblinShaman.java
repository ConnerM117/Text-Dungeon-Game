package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.GoblinTalisman;

public class GoblinShaman extends Mob {

	public GoblinShaman(int counter) {
		super();
		name = "Goblin Shaman " + counter;
		XP = 2;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 40;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 3;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	// public methods

	public String specialAction(Mob target, List<Mob> mobs) {
		int rand = GameScreen.generateRandom(0, mobs.size() - 1); // target a random ally, including itself
		return name + " chants and casts a spell!\n" + mobs.get(rand).getName() + " is filled with bloodlust!\n"
				+ mobs.get(rand).buffAccuracy(20, 2) + "\n" + mobs.get(rand).buffDamage(1, 2);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " chants and casts a spell! Flames surround you!\n";
		
		setBurningAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new GoblinTalisman();
	}

}
