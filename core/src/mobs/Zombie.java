package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class Zombie extends Mob {
	
	private boolean isProtecting;
	private Mob protectedMob;
	
	public Zombie(int counter) {
		super();
		name = "Zombie " + counter;
		XP = 1;
		maxHitPoints = 8;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 0;
		baseToughness = 200;
		baseMind = 5;
		baseAccuracy = 50;
		minDamage = 2;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
		
		isProtecting = false;
		protectedMob = null;
		immuneToPoison = true;
		immuneToWounded = true;
	}
	
	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (getCurrentHitPoints() <= 0) {
			if (GameScreen.generateRandom(1, 3) == 1) { //1 in 3 chance that the zombie gets back up
				str += "\n" + name + " is defeated, but rises again!";
				currentHitPoints = 1;
			}
		}
		if (isProtecting && getCurrentHitPoints() <= 0) { //if it is protecting something when it dies
			protectedMob.resetProtected(); //ensure the protection ends
		}
		return str;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " prepares to take a hit!\n" + setTempHP(2);
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
				choice = GameScreen.generateRandom(1, 3);
			} else { //it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 4);
			}
			
			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3 && getTempHP() == 0) { 
				isValid = true;
			} else if (choice <= 4 && mobs.size() > 1 && !mobsAreProtected(mobs)) { //only protect an ally if there is an ally to protect and something isn't already protected (prevents protection loops/chains)
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
		return null;
	}

}
