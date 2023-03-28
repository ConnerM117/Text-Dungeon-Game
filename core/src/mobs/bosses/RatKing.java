package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import mobs.*;

public class RatKing extends Boss {
	
	int ratCounter;
	
	public RatKing() {
		super();
		name = "Rat King";		
		XP = 5;
		maxHitPoints = 12;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 50;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 3;
		currentStamina = baseStamina;
		ratCounter = 2; //Rat King starts with one Giant Rat in the battle
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		for (Mob m : mobs) {
			if (m != this) {
				m.buffAgility(10, 3);
				m.buffCritRate(5, 3);
			}
		}
		return "The Rat King rallies its allies!";
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		GameScreen.mobsToAdd.add(new GiantRat(ratCounter));
		ratCounter++;
		return "The Rat King whistles, and a Giant Rat emerges to join the battle!";
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		String str = "The Rat King lunges with a diseased bite!";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//boss has 4 choices: attack, special move, or two stamina moves
		int choice = 0;
		boolean isValid = false;
		
		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 3);
			} else { //it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 5);
			}
			
			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3 && mobs.size() > 1) { //will only rally if there are allies
				isValid = true;
			} else if (choice == 4 && mobs.size() < 4) { //will only rally if there are less than 3 allies
				isValid = true;
			} else if (choice == 5) { //choice is 5
				isValid = true;
			}
		}
		
		if (choice <= 2) { //choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) { 
			return specialAction(mob, mobs);
		} else if (choice == 4) { 
			spendStamina(1);
			return staminaAction(mob, mobs);
		} else { //choice is 5
			spendStamina(1);
			return staminaActionTwo(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		return null;
	}
}
