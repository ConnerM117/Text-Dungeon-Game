package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.DruidStaff;
import mobs.*;

public class ElderDruid extends Boss {
	
	public ElderDruid() {
		super();
		name = "Elder Druid";
		XP = 5;
		maxHitPoints = 12;
		currentHitPoints = maxHitPoints;
		minArmor = 1;
		maxArmor = 3;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 30;
		baseMind = 70;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 0;
		baseStamina = 3;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " summons a bolt of lightning!\n";
		if (!target.isAgile()) { //target fails to dodge
			str += target.getName() + " isn't fast enough to dodge it!\n" + target.takeDamage(getCurrentDamage()/2);
		} else {
			str += "But " + target.getName() + " leaps out of the way!";
		}
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		mobs.set(0, new WolfDire(this)); //set at 0 because Druid should be the only thing in the list
		return name + " shapeshifts into a Dire Wolf!";
	}
	
	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		mobs.set(0, new BearDire(this)); //set at 0 because Druid should be the only thing in the list
		return name + " shapeshifts into a Dire Bear!";	
	}
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//boss has 4 choices: attack, special move, or two stamina moves
		int choice = 0;
			
		if (getCurrentStamina() == 0) {
			choice = GameScreen.generateRandom(1, 3);
		} else { //it has Stamina, then allow for stamina move
			choice = GameScreen.generateRandom(1, 7);
		}
		
		if (choice == 1) { //choice is 1
			return attackTarget(mob, false, false, true);
		} else if (choice <= 3) { //2 or 3
			return specialAction(mob, mobs);
		} else if (choice <= 5) { //4 or 5
			spendStamina(1);
			return staminaAction(mob, mobs);
		} else { //choice is 6 or 7
			spendStamina(1);
			return staminaActionTwo(mob, mobs);
		}
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return Item.getForestItem();
		else
			return new DruidStaff();
	}

}
