package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class SkeletonGiant extends Mob {

	public SkeletonGiant(int counter) {
		super();
		name = "Giant Skeleton " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 100;
		baseMind = 10;
		baseAccuracy = 60;
		minDamage = 2;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 50;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;

		immuneToPoison = true;
		immuneToWounded = true;
	}

	// public methods

	public String specialAction(Mob target, List<Mob> mobs) {
		boolean isValid = false;
		int choice = 0;

		while (!isValid) { // choose an ally other than itself
			choice = GameScreen.generateRandom(0, (mobs.size() - 1));
			if (mobs.get(choice) != this) {
				isValid = true;
			}
		}

		Mob newTarget = mobs.get(choice);
		int damage = getCurrentDamage();
		if (damage > newTarget.getCurrentHitPoints()) {
			damage = newTarget.getCurrentHitPoints();
		}
		return name + " smashes a skeleton ally and absorbs their bones!\n" + newTarget.takeDamage(damage) + "\n"
				+ healDamage(damage) + "\n" + healStamina(1);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		debuffAccuracy(20, 0);
		buffCritRate(10, 0);

		return name + " attacks recklessly, swinging twice!\n" + attackTarget(target, false, false, true) + "\n"
				+ attackTarget(target, false, false, true);
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
				// only smash an ally to heal if there is an ally and giant is missing HP
			} else if (choice == 3 && mobs.size() > 1 && getCurrentHitPoints() < getMaxHitPoints()) { 
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
		return null;
	}

}
