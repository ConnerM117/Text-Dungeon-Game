package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.weapons.OgreClub;

public class Ogre extends Mob {
	
	public Ogre(int counter) {
		super();
		name = "Ogre " + counter;
		XP = 2;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 60;
		baseMind = 20;
		baseAccuracy = 60;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
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
		
		for (Mob m : mobs) { //buff all other allies other than itself
			if (m != this) {
				m.buffAccuracy(10, 3);
				m.buffAgility(10, 3);
				m.setTempHP(1);
			}
		}
		
		return name + " bellows and smashes " + mobs.get(choice).getName() + " in frustration! The others scramble forward!\n" + attackTarget(mobs.get(choice), true, false, true)
		 + "\nAll allies get +10 + Accuracy, +10 Agility, and 1 Temp HP.";
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " prepares a deadly blow!\n" + buffCritRate(40, 1) + "\n" + buffCritDamage(25, 1) + "\n" + buffAccuracy(20, 1);
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
			} else if (choice == 3 && mobs.size() > 2) { //only buff allies if there are other allies
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
		return new OgreClub();
	}

}
