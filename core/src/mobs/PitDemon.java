package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.armor.DemonArmor;
import items.weapons.DemonSword;

public class PitDemon extends Mob {

	public PitDemon(int counter) {
		super();
		name = "Pit Demon " + counter;
		XP = 4;
		maxHitPoints = 9;
		currentHitPoints = maxHitPoints;
		minArmor = 4;
		maxArmor = 6;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 90;
		baseMind = 80;
		baseAccuracy = 70;
		minDamage = 5;
		maxDamage = 7;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		immuneToBurning = true;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " attacks with a flaming sword!\n";

		setBurningAttack(true, 3);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();

		return str;
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " unleashes a powerful wounding attack!\n" + buffWoundRate(100, 0) + "\n" + buffWoundDamage(1, 0)
				+ "\n" + attackTarget(target, false, false, true);
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new DemonSword();
		else
			return new DemonArmor();
	}

}
