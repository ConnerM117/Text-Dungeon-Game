package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.BearCloak;

public class Bear extends Mob {

	public Bear(int counter) {
		super();
		name = "Bear " + counter;
		XP = 2;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 60;
		baseMind = 30;
		baseAccuracy = 65;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 15;
		woundDamage = 2;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return (name + " holds back to regain its vigor!\n") + setTempHP(2);
	}

	// attack with a diseased bite that also has a chance to poison the target
	public String staminaAction(Mob target, List<Mob> mobs) {
		return (name + " tries to maul " + target.getName() + "!\n") + buffWoundRate(10, 0) + "\n"
				+ attackTarget(target, false, false, true) + "\n" + attackTarget(target, false, false, true);
	}

	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		// mob has 3 choices: attack, special move, or stamina move
		int choice = 0;
		boolean isValid = false;

		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 3);
			} else { // it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 4);
			}

			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3 && getTempHP() > 0) { // only buff self if not already buffed
				isValid = true;
			} else if (choice == 4) {
				isValid = true;
			}
		}

		if (choice <= 2) { // choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) {
			return specialAction(mob, mobs);
		} else { // choice is 4
			spendStamina(1);
			return staminaAction(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		return new BearCloak();
	}
}
