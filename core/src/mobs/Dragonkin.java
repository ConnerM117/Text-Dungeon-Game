package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.consumables.MakeshiftTrap;

public class Dragonkin extends Mob {
	
	public Dragonkin(int counter) {
		super();
		name = "Dragonkin " + counter;
		XP = 1;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 40;
		baseMind = 60;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 5;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 25;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = (name + " flanks to give its allies an opening!");
		for (Mob m : mobs) { //buff all mobs other than itself
			if (m != this) {
				str += "\n" + m.buffAccuracy(20, 0);
				str += "\n" + m.buffCritRate(10, 0);
			}
		}
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		setHasTrap(true, 2);
		return (name + " sets a trap!");
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
		return new MakeshiftTrap();
	}

}
