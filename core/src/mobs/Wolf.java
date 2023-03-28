package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class Wolf extends Mob {
	
	public Wolf(int counter) {
		super();
		name = "Wolf " + counter;
		XP = 1;
		maxHitPoints = 5;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 1;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 20;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 1;
		maxDamage = 4;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 50;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = (name + " flanks to give its allies an opening!");
		for (Mob m : mobs) { //buff all other wolves other than itself
			if (m != this) {
				str += "\n" + m.buffAccuracy(10, 1);
				str += "\n" + m.buffCritRate(5, 1);
			}
		}
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " lunges for the throat!\n" + buffCritRate(30, 0) + "\n" + buffCritDamage(25, 0) + "\n" + attackTarget(target, false, false, true);
	}
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//mob has 3 choices: attack, special move, or stamina move
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
			} else if (choice == 3 && mobs.size() > 1) { //only buff allies if there are other allies
				isValid = true;
			} else if (choice == 4) {
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
		return null;
	}

}
