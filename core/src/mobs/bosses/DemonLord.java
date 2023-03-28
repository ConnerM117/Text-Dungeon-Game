package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import mobs.*;

public class DemonLord extends Boss {
	
	private int summonCounter;
	
	public DemonLord() {
		super();
		name = "Demon Lord";
		XP = 5;
		maxHitPoints = 14;
		currentHitPoints = maxHitPoints;
		minArmor = 3;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 90;
		baseMind = 90;
		baseAccuracy = 70;
		minDamage = 4;
		maxDamage = 8;
		piercingDamage = 0;
		critRate = 15;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		summonCounter = 1;
		immuneToBurning = true;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " attacks with a flaming sword!\n";
		
		setBurningAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		if (summonCounter == 1) { //the first summon will always be a horned demon, otherwise it will be an imp
			GameScreen.mobsToAdd.add(new HornedDemon(summonCounter));
			summonCounter++;
			return name + " opens a portal, and a horned demon emerges to join the battle!";
		} else {
			GameScreen.mobsToAdd.add(new Imp(summonCounter));
			summonCounter++;
			return name + " opens a portal, and an imp emerges to join the battle!";
		}
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		String str = name + " tries to possess you!\n";
		if (!target.isTough()) { //the target has a chance to resist with Toughness
			str += target.getName() + " can't resist " + name + "'s compulsion and attacks itself!\n" + target.attackTarget(target, true, false, false);
		} else { //they dodge
			str += target.getName() + " manages to shake off the compulsion!";
		}
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
			} else if (choice == 3) {
				isValid = true;
			} else if (choice == 4 && mobs.size() < 4) { //will only call new ally if there are less than 3 allies
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
		return Item.getDemonItem();
	}

}
