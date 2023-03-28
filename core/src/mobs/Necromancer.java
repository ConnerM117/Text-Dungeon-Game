package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.NecromancyTalisman;

public class Necromancer extends Mob {
	
	private int healValue;
	
	public Necromancer(int counter) {
		super();
		name = "Necromancer " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 70;
		baseMind = 60;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
		healValue = 2;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		boolean isValid = false;
		int choice = 0;
		
		while (!isValid) { //choose an ally other than itself
			choice = GameScreen.generateRandom(0, (mobs.size()-1));
			if (mobs.get(choice) != this) {
				isValid = true;
			}
		}
		
		return name + " casts a spell!\n" + mobs.get(choice).healDamage(healValue);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		GameScreen.mobsToAdd.add(new Zombie(mobs.size()));
		return name + " casts a spell, and a zombie emerges to join the battle!";
	}

	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//boss has 4 choices: attack, special move, or two stamina moves
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
			} else if (choice == 3 && mobs.size() > 1) { //will only heal if there are allies
				isValid = true;
			} else if (choice == 4 && mobs.size() < 4) { //will only call new ally if there are less than 3 allies
				isValid = true;
			}
		}
		
		if (choice <= 2) { //choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) { 
			return specialAction(mob, mobs);
		} else { //choice is 4
			spendStamina(1);
			return staminaAction(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		return new NecromancyTalisman();
	}
}
