package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.LizardmanShield;

public class LizardmanChampion extends Mob {

	private boolean isProtecting;
	private Mob protectedMob;
	
	public LizardmanChampion(int counter) {
		super();
		name = "Lizardman Champion " + counter;
		XP = 3;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 70;
		baseMind = 40;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 5;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
	
	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (isProtecting && getCurrentHitPoints() <= 0) { //if it is protecting something when it dies
			protectedMob.resetProtected(); //ensure the protection ends
		}
		return str;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes cover behind its shield!\n" + buffAgility(40, 0);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		boolean isValid = false;
		int choice = 0;
		
		while (!isValid) { //choose an ally other than itself
			choice = GameScreen.generateRandom(0, (mobs.size()-1));
			if (mobs.get(choice) != this) {
				isValid = true;
			}
		}
		
		mobs.get(choice).setProtected(true, this, 2);
		isProtecting = true;
		protectedMob = mobs.get(choice);

		return name + " protects " + mobs.get(choice).getName() + "!";
	}
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//mob has 3 choices: attack, special move, or stamina move; choices are weighted toward Protecting (stamina move)
		int choice = 0;
		boolean isValid = false;
			
		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 2);
			} else { //it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 4);
			}
			
			if (choice == 1) {
				isValid = true;
			} else if (choice == 2) { 
				isValid = true;
			} else if (choice <= 4 && mobs.size() > 1 && !mobsAreProtected(mobs)) { //only protect an ally if there is an ally to protect
				isValid = true;
			}
		}
		
		if (choice == 1) {
			return attackTarget(mob, false, false, true);
		} else if (choice == 2) {
			return specialAction(mob, mobs);
		} else { //choice is 3 or 4
			spendStamina(1);
			return staminaAction(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		return new LizardmanShield();
	}
}
