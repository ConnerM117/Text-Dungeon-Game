package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import mobs.Mob;

public class CaveTroll extends Boss {
	
	private int healthRegen;
	
	public CaveTroll() {
		super();
		name = "Cave Troll";
		XP = 5;
		maxHitPoints = 16;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 90;
		baseMind = 30;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 3;
		currentStamina = baseStamina;
		healthRegen = 2;
		immuneToWounded = true;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " spits a stream of acid!\n";
		
		setPoisonAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " regenerates quickly!\n" + regenerate(1, 3);
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		buffDamage(3, 0);
		return name + " picks up and throws a huge rock!\n" + attackTarget(target, false, false, true);
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
			} else if (choice == 4 && getCurrentHitPoints() != getMaxHitPoints() && !isBurning) { //only heal if it's damaged and isn't Burning
				isValid = true;
			} else if (choice == 5) { //choice is 5
				isValid = true;
			}
		}
		
		String str = "";
		if (choice <= 2) { //choice is 1 or 2
			str += attackTarget(mob, false, false, true);
		} else if (choice == 3) { 
			str += specialAction(mob, mobs);
		} else if (choice == 4) { 
			spendStamina(1);
			str += staminaAction(mob, mobs);
		} else { //choice is 5
			spendStamina(1);
			str += staminaActionTwo(mob, mobs);
		}
		
		if (!isBurning) {
			str += healDamage(healthRegen);
		} else {
			str += "The flames keep " + name + " from regenerating!";
		}
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return Item.getTrollItem();
	}

}
