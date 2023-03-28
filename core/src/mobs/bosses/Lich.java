package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.LichSkull;
import mobs.*;

public class Lich extends Boss {

	private int summonCounter;
	
	public Lich() {
		super();
		name = "Lich";
		XP = 5;
		maxHitPoints = 17;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 100;
		baseMind = 80;
		baseAccuracy = 80;
		minDamage = 3;
		maxDamage = 8;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 3;
		currentStamina = baseStamina;
		
		immuneToWounded = true;
		immuneToPoison = true;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " casts a spell!";
		for (Mob m : mobs) {
			if (m != this) {
				str += "\n" + m.healDamage(1);
			}
		}
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		GameScreen.mobsToAdd.add(new Zombie(summonCounter));
		summonCounter++;
		return name + " casts a spell, and a zombie emerges to join the battle!";
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		String str = name + " points at you and casts a spell!\n";
		if (!target.isTough()) { //the target has a chance to resist
			str += target.getName() + " takes the spell's full effects!\n" + target.takeDamage(getCurrentDamage()*2);
		} else { //they resist
			str += target.getName() + " resists most of the spell's effects!\n" + target.takeDamage(getCurrentDamage());
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
			} else if (choice == 3 && mobs.size() > 1) { //will only heal if there are allies
				isValid = true;
			} else if (choice == 4 && mobs.size() < 4) { //will only call new ally if there are less than 3 allies
				isValid = true;
			} else if (choice == 5) {
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
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return Item.getLichItem();
		else
			return new LichSkull();
	}
}
