package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import mobs.*;

public class GoblinKing extends Boss {

	private int gobCounter;

	public GoblinKing() {
		super();
		name = "Goblin King";
		XP = 5;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 30;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 3;
		currentStamina = baseStamina;
		gobCounter = 1;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = ("The Goblin King threatens its subjects!");
		for (Mob m : mobs) {
			if (m != this) {
				str += "\n" + m.buffAccuracy(10, 3);
				str += "\n" + m.setTempHP(1);
			}
		}
		return str;
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		GameScreen.mobsToAdd.add(new GoblinScout(gobCounter)); // Goblin King enters combat with a shaman
		gobCounter++;
		return "The Goblin King bellows, and a goblin scout emerges to join the battle!";
	}

	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		return "The Goblin King throws himself at you in a fury!\n" + buffCritRate(10, 3) + "\n" + buffAccuracy(10, 3)
				+ "\n" + attackTarget(target, false, false, true);
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
			} else if (choice == 3 && mobs.size() > 1) { // will only rally if there are allies
				isValid = true;
			} else if (choice == 4 && mobs.size() < 4) { // will only call new ally if there are less than 3 allies
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

	@Override
	public Item getItemDrop() {
		return Item.getGoblinItem();
	}
}
