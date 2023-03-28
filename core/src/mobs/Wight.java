package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.gear.AncientShield;
import items.weapons.AncientAxe;
import items.weapons.AncientSword;

public class Wight extends Mob {

	public Wight(int counter) {
		super();
		name = "Wight " + counter;
		XP = 2;
		maxHitPoints = 6;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 200;
		baseMind = 20;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 7;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;

		immuneToPoison = true;
		immuneToWounded = true;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		buffWoundRate(50, 0);
		return name + " aims a deadly blow!\n" + attackTarget(target, false, false, true);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = "Frost coats the walls as " + name + " reaches toward you!\n";
		if (!target.isTough()) { // the target has a chance to dodge to resist burning
			str += target.getName() + " isn't tough enough to withstand the cold!\n"
					+ target.takeDamage(getCurrentDamage() / 2) + "\n" + target.debuffAgility(20, 3);
		} else { // they dodge
			str += target.getName() + " endures the cold!";
		}
		return str;
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1)
			return new AncientSword();
		else if (rand == 2)
			return new AncientAxe();
		else
			return new AncientShield();
	}
}
