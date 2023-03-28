package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.consumables.TrollBrew;

public class Troll extends Mob {
	
	private int healthRegen;
	
	public Troll(int counter) {
		super();
		name = "Troll " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 80;
		baseMind = 30;
		baseAccuracy = 65;
		minDamage = 2;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		healthRegen = 2;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = (name + " tries to sieze " + target.getName() + "!\n");
		if (!target.doesDodge()) { //the target has a chance to dodge
			str += target.getName() + " is grabbed by " + name + "!\n" + target.takeDamage(getCurrentDamage()) + "\n" + target.debuffAgility(30, 0);
		} else { //they dodge
			str += target.getName() + " leaps out of the way!";
		}
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " regenerates quickly!\n" + regenerate(maxHitPoints/4, 1);
	}
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		int choice = 0;
		boolean isValid = false;
			
		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 3);
			} else { //it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 4);
			}
			
			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3) {
				isValid = true;
			} else if (choice == 4 && getCurrentHitPoints() != getMaxHitPoints() && !isBurning) { //only heal if it's damaged and isn't Burning
				isValid = true;
			}
		}
		
		String str = "";
		
		if (choice <= 2) { //choice is 1 or 2
			str += attackTarget(mob, false, false, true);
		} else if (choice == 3) {
			str += specialAction(mob, mobs);
		} else { //choice is 4
			spendStamina(1);
			str += staminaAction(mob, mobs);
		}
		
		if (!isBurning) {
			str += "\n" + healDamage(healthRegen);
		} else {
			str += "\nThe flames keep " + name + " from regenerating!";
		}
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new TrollBrew();
	}

}
