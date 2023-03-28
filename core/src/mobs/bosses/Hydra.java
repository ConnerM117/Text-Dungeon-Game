package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.weapons.HydraFang;
import mobs.*;


public class Hydra extends Boss {
	
	private int healthRegen;
	
	public Hydra() {
		super();
		name = "Hydra";
		XP = 5;
		maxHitPoints = 13;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 80;
		baseMind = 40;
		baseAccuracy = 80;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		healthRegen = 1;
	}
	
	private void modMaxDamage(int buff) {
		maxDamage += buff;
	}
	
	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (damage > 4 && !isBurning) { //if the damage exceeds 4 and it isn't Burning, cut off a head and re-grow two more
			str += "\n" + name + " loses one of its heads! Two more grow in its place!";
			healDamage(2);
			modMaxDamage(2); //prevents buff from being removed
			buffCritRate(5, 10);
		} else if (isBurning) {
			str += "The flames cauterize the wound and prevent " + name + " from regrowing a head!";
		}
		return str;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " attacks with venomous jaws!\n";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " exhales a jet of poison gas!\n";
		
		if (!target.isTough()) { //target fails to resist
			str += target.getName() + " isn't enough to endure it!\n" + target.takeDamage(getCurrentDamage()/2) + "\n" + target.setPoisoned(true, 2);
		} else {
			str += "But " + target.getName() + " resists the poison!";
		}
		
		return str;
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		return name + " is enraged and thrashes furiously!\n" + attackTarget(target, false, false, true) + "\n" + attackTarget(target, false, false, true);
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
			} else if (choice == 4) {
				isValid = true;
			} else if (choice == 5 && getCurrentHitPoints() <= (getMaxHitPoints()/2)){ //choice is 5 and current hit points are less than/equal to half max hit points
				isValid = true;
			}
		}
		
		String str = "";
		if (choice <= 2) { //choice is 1 or 2
			str += attackTarget(mob, false, false, true);
		} else if (choice == 3) { 
			str += specialAction(mob, mobs);
		} else if (choice == 4) { 
			str += staminaAction(mob, mobs);
			spendStamina(1);
		} else { //choice is 5
			str += staminaActionTwo(mob, mobs);
			spendStamina(1);
		}
		return str + "\n" + healDamage(healthRegen);
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return Item.getSwampItem();
		else
			return new HydraFang();
	}

}
