package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.AncientShield;
import items.weapons.AncientAxe;
import items.weapons.AncientSword;
import items.weapons.RustySword;

public class SkeletonArcher extends Mob {
	
	public SkeletonArcher(int counter) {
		super();
		name = "Skeleton Archer " + counter;
		XP = 2;
		maxHitPoints = 6;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 10;
		baseToughness = 100;
		baseMind = 10;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 4;
		piercingDamage = 1;
		critRate = 5;
		critDamage = 50;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 1;
		currentStamina = baseStamina;
		
		immuneToPoison = true;
		immuneToWounded = true;
	}
		
	//public methods
	
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " aims a precision shot!\n" + buffCritRate(10, 1) + "\n" + buffAccuracy(20, 1);
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " rebuilds itself!\n" + healDamage(1);
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
			} else if (choice == 3) {
				isValid = true;
			} else if (choice == 4 && getCurrentHitPoints() < getMaxHitPoints()) { //will only heal if it's missing HP
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
		int rand = GameScreen.generateRandom(1, 5);
		if (rand <= 2)
			return new RustySword();
		else if (rand == 3)
			return new AncientSword();
		else if (rand == 4)
			return new AncientAxe();
		else
			return new AncientShield();
	}
}
