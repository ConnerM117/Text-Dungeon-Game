package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameEvent;
import com.textdungeon.game.GameScreen;

import items.Item;
import mobs.Mob;

public class HeartOfTheDungeon extends Boss {

	public HeartOfTheDungeon() {
		super();
		name = "The Heart of the Dungeon";
		XP = 0;
		maxHitPoints = 30;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 10;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 80;
		baseMind = 80;
		baseAccuracy = 80;
		minDamage = 3;
		maxDamage = 9;
		piercingDamage = 2;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 2;
		baseStamina = 3;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1)
			return mageChoices(target, mobs);
		else if (rand == 2)
			return rogueChoices(target, mobs);
		else
			return warriorChoices(target, mobs);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " blinks in and out of existence!\n" + buffAgility(30, 2);
	}

	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		String str = name + " warps time and acts twice!";

		GameScreen.eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				GameScreen.setLogger(getCombatChoice(target, mobs));
			}
		});

		GameScreen.eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				GameScreen.setLogger(getCombatChoice(target, mobs));
			}
		});

		return str;
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
			} else if (choice == 3) { // will only rally if there are allies
				isValid = true;
			} else if (choice == 4) { // will only call new ally if there are less than 3 allies
				isValid = true;
			} else if (choice == 5) { // choice is 5
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

	public String mageChoices(Mob target, List<Mob> mobs) {
		int choice = GameScreen.generateRandom(1, 3);
		if (choice == 1) {
			return "An arm within brandishes a staff, and a magical ward appears around it...\n" + buffAgility(20, 3);
		} else if (choice == 2) {
			return "A staff appears and gathers magical power...\n" + buffAccuracy(10, 3) + "\n" + buffCritRate(10, 3);
		} else { // choice is 3
			String str = "A bolt of flame shoots toward you from a brandished staff...\n";

			setBurningAttack(true, 2);
			str += attackTarget(target, false, false, true);
			resetBurningAttack();

			return str;
		}
	}

	public String rogueChoices(Mob target, List<Mob> mobs) {
		int choice = GameScreen.generateRandom(1, 3);
		if (choice == 1) {
			return "A dagger within takes precise aim...\n" + buffCritRate(10, 1) + "\n" + buffDamage(1, 1) + "\n"
					+ target.debuffAgility(10, 3);
		} else if (choice == 2) {
			return "A thick cloak of darkness obscures its movement and confuses you...\n" + buffAgility(10, 4) + "\n"
					+ target.removeAllBuffs();
		} else { // choice is 3
			return "A thick cloak of darkness hides its position...\n" + setHidden(true, 1);
		}
	}

	public String warriorChoices(Mob target, List<Mob> mobs) {
		int choice = GameScreen.generateRandom(1, 3);
		if (choice == 1) {
			String str = "A greatsword emerges and takes a powerful but reckless swing...\n";

			buffDamage(2, 0);
			debuffAccuracy(20, 0);
			str += attackTarget(target, false, false, true);

			return str;
		} else if (choice == 2) {
			debuffAccuracy(20, 0);
			return "Two swords emerge, and it attacks with both...\n" + attackTarget(target, false, false, true)
					+ "\n" + attackTarget(target, false, false, true);
		} else { // choice is 3
			return "A rumble emanates from the thing, as a horrible battle cry...\n" + buffDamage(1, 3)
					+ "\n" + buffAccuracy(10, 3);
		}
	}

	@Override
	public Item getItemDrop() {
		return null;
	}

}
