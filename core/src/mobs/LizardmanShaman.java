package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.consumables.LizardmanAntidote;

public class LizardmanShaman extends Mob {

	public LizardmanShaman(int counter) {
		super();
		name = "Lizardman Shaman " + counter;
		XP = 2;
		maxHitPoints = 6;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 60;
		baseMind = 50;
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
		String str = name + " chants and casts a spell!\n";
		if (!target.isTough()) {
			str += target.getName() + " isn't tough enough to resist!\n" + target.takeDamage(getCurrentDamage() / 2)
					+ "\n" + target.setPoisoned(true, 1); // if they don't resist, poison for random rounds from 2-4
		} else { // they resist
			str += target.getName() + " is tough enough to resist!";
		}

		return str;
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " chants and draws a knife to perform a sacrifice!\n";
		boolean isValid = false;
		int choice = 0;

		while (!isValid) { // choose an ally other than itself
			choice = GameScreen.generateRandom(0, (mobs.size() - 1));
			if (mobs.get(choice) != this) {
				isValid = true;
			}
		}

		str += mobs.get(choice).takeDamage(1);
		if (mobs.get(choice).getCurrentHitPoints() <= 0) {
			str += "\n" + setTempHP(3);
		} else {
			str += "\n" + setTempHP(2);
		}
		
		return str;
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
			} else if (choice == 3) {
				isValid = true;
			} else if (choice == 4 && mobs.size() > 1) { // only sacrifice an ally if there are other allies
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
		return new LizardmanAntidote();
	}
}
