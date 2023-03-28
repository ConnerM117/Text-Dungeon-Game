package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.AncientShield;
import items.weapons.AncientAxe;
import items.weapons.AncientSword;
import mobs.*;

public class BoneMass extends Boss {

	public BoneMass() {
		super();
		name = "Bone Mass";
		XP = 5;
		maxHitPoints = 18;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 0;
		baseToughness = 200;
		baseMind = 10;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 3;
		currentStamina = baseStamina;

		immuneToPoison = true;
		immuneToWounded = true;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " tries to constrict " + target.getName() + " with a surge of bones!";
		if (!target.isTough()) { // target's toughness avoids debuffs
			str += target.getName() + " isn't tough enough to endure it!\n" + target.takeDamage(1) + "\n"
					+ target.debuffAccuracy(10, 3) + "\n" + target.debuffAgility(20, 3);
		} else {
			str += "But " + target.getName() + " is tough enough to endure it!";
		}
		return str;
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " absorbs shattered bone from its surroundings, replenishing its mass!\n"
				+ healDamage(GameScreen.generateRandom(2, 4));
	}

	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		return name + " surges outward in a wave of bones!\n" + target.debuffAccuracy(10, 3) + "\n"
				+ target.debuffAgility(10, 3) + "\n" + buffCritRate(10, 3) + "\n"
				+ attackTarget(target, false, false, true);
	}

	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		// boss has 4 choices: attack, special move, or two stamina moves
		int choice = 0;
		boolean isValid = false;

		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 3);
			} else { // it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 5);
			}

			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3) {
				isValid = true;
			} else if (choice == 4 && getCurrentHitPoints() < getMaxHitPoints()) { // will only heal if it's not at full
																					// HP
				isValid = true;
			} else if (choice == 5) {
				isValid = true;
			}
		}

		if (choice <= 2) { // choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) {
			return specialAction(mob, mobs);
		} else if (choice == 4) {
			spendStamina(1);
			return staminaAction(mob, mobs);
		} else { // choice is 5
			spendStamina(1);
			return staminaActionTwo(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1)
			return new AncientSword();
		else if (rand == 2)
			return new AncientAxe();
		else
			return new AncientShield();
	}
}
